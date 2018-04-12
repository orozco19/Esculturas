package corp.poopapps.afinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class miPerfil extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private GoogleApiClient googleApiClient;

    String usuario, contrasena;

    TextView tCorreo;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        tCorreo = (TextView)findViewById(R.id.perfilusuario);
        imageView = (ImageView)findViewById(R.id.imageView);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        inicializar();
    }

    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){

                    if(firebaseUser.getPhotoUrl() != null){Glide.with(miPerfil.this).load(firebaseUser.getPhotoUrl()).into(imageView);}
                    tCorreo.setText(firebaseUser.getEmail());
                    Log.d("FirebaseUser", "getPhotoUrl: "+firebaseUser.getPhotoUrl());
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
        firebaseAuth.removeAuthStateListener(authStateListener);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    private void googleLogOut() {
        if(Auth.GoogleSignInApi != null){
            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()){
                        Toast.makeText(miPerfil.this, "Sesión finalizada", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if(LoginManager.getInstance() != null){
            LoginManager.getInstance().logOut();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void ClickEditarPerfil(View view) {
        Toast.makeText(miPerfil.this, "EDITAR PERFIL", Toast.LENGTH_SHORT).show();
    }

    public void ClickCerrarSesion(View view) {
            Intent i = new Intent(miPerfil.this, LogginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            firebaseAuth.signOut();
            googleLogOut();
    }
}
