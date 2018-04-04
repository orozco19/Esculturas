package corp.poopapps.afinal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegistroActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button bIngreso;
    EditText eCorreo, eContrasena, eContrasena2;
    Intent data = new Intent();

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        bIngreso = (Button)findViewById(R.id.bIngreso);
        eCorreo = (EditText)findViewById(R.id.eCorreo);
        eContrasena = (EditText)findViewById(R.id.eContrasena);
        eContrasena2 = (EditText)findViewById(R.id.eContrasena2);

        inicializar();

    }



    private void inicializar() {

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    goMainScreen();
                    Log.d("FirebaseUser", "Usuario Logeado: "+firebaseUser.getDisplayName());
                    Log.d("FirebaseUser", "Usuario Logeado: "+firebaseUser.getEmail());
                } else{
                    Log.d("FirebaseUser", "El usuario ha cerrado sesión");
                }
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);
       // FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void onClick(View view) {

        String correo,contrasena,contrasena2;

        boolean error = false;

        correo = eCorreo.getText().toString();
        contrasena = eContrasena.getText().toString();
        contrasena2 = eContrasena2.getText().toString();


        if(correo.isEmpty()){
            eCorreo.setError("¡Campo vacío!");
            error = true;
        }
        if(contrasena.isEmpty()){
            eContrasena.setError("¡Campo vacío!");
            error = true;
        }
        if(contrasena2.isEmpty()){
            eContrasena2.setError("¡Campo vacío!");
            error = true;
        }
        if(!contrasena.equals(contrasena2)){
            error = true;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Las contraseñas no coinciden.", Toast.LENGTH_SHORT);

            toast1.show();

            eContrasena.setText("");
            eContrasena2.setText("");
        }

        if(!error) {

            firebaseAuth.createUserWithEmailAndPassword(correo, contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegistroActivity.this, "Cuenta creada", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegistroActivity.this, LogginActivity.class);
                        startActivity(i);
                        finish();
                    } else{
                        Toast.makeText(RegistroActivity.this, "Error al crear la cuenta", Toast.LENGTH_SHORT).show();

                        eCorreo.setText("");
                        eContrasena.setText("");
                        eContrasena2.setText("");
                    }
                }
            });
        }

    }

    private void goMainScreen() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
