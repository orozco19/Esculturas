package corp.poopapps.afinal;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    EditText eUsuario, eContrasena;
    Button bRegistro;
    String usuario, contrasena;

    private GoogleApiClient googleApiClient;
    private SignInButton bRegistroGoogle;
    private LoginButton btnSignInFacebook;
    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public static final int SIGN_IN=123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin2);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        btnSignInFacebook = findViewById(R.id.btnSignInFacebook);

        bRegistro = (Button)findViewById(R.id.bRegistrarse);
        eUsuario = (EditText)findViewById(R.id.eUsuario);
        eContrasena = (EditText)findViewById(R.id.eContrasena);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        bRegistroGoogle = (SignInButton)findViewById(R.id.bRegistroGoogle);

        bRegistroGoogle.setSize(SignInButton.SIZE_WIDE);
        bRegistroGoogle.setColorScheme(SignInButton.COLOR_DARK);
        bRegistroGoogle.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN);
            }
        });

        btnSignInFacebook.setReadPermissions("email", "public_profile");

        btnSignInFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Login con Facebook", "Login exitoso");
                signInFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Login con Facebook", "Login Cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Login con Facebook", "Login Error");
                error.printStackTrace();
            }
        });

        inicializar();
        getHashes();
    }

    private void getHashes(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "corp.poopapps.afinal",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public void signInFacebook(AccessToken accessToken){
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    goMainScreen();
                }else {
                        Toast.makeText(LogginActivity.this, "Autenticacion con Facebook no exitosa", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            firebaseAuthWithGoogle(result.getSignInAccount());
        }else {
            Toast.makeText(LogginActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
        }}

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(LogginActivity.this, "No se pudo autentificar con Firebase", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    goMainScreen();
                    Log.d("FirebaseUser", "Correo Usuario: "+firebaseUser.getEmail());
                } else{
                    Log.d("FirebaseUser", "El usuario ha cerrado sesiòn");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        firebaseAuth.addAuthStateListener(authStateListener);
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        super.onStop();
    }


    public void OnClickButton(View view) {

        Intent i = new Intent(LogginActivity.this, RegistroActivity.class);
        startActivity(i);
    }

    public void ClickIngresar(View view) {

        boolean error = false;

        usuario = eUsuario.getText().toString();
        contrasena = eContrasena.getText().toString();

        if(usuario.isEmpty()){
            eUsuario.setError("¡Campo vacío!");
            error = true;
        }
        if(contrasena.isEmpty()){
            eContrasena.setError("¡Campo vacío!");
            error = true;
        }

        if(!error){
            iniciarSesion(usuario, contrasena);
        }

    }

    private void iniciarSesion(String correo, String contrasena) {
        firebaseAuth.signInWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent i = new Intent(LogginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(LogginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LogginActivity.this, "Error en inicio de sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
