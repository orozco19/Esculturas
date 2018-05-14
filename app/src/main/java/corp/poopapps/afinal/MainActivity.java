package corp.poopapps.afinal;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private BottomNavigationView bottomNavigationView;

    String usuario, contrasena;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView)findViewById(R.id.imageView);
        image.setVisibility(View.INVISIBLE);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                if(item.getItemId() == R.id.EsculturasItem) {
                    setTitle(R.string.esculturas);
                    Intent i = new Intent(MainActivity.this, listaEscultura.class);
                    startActivity(i);
                   /* EsculturasFragment esculturasFragment = new EsculturasFragment();
                    ft.replace(R.id.contenedor, esculturasFragment).commit();*/
                }
                else if(item.getItemId() == R.id.EscultoresItem) {
                    setTitle(R.string.escultores);
                    Intent i = new Intent(MainActivity.this, listaEscultoresActivity.class);
                    startActivity(i);
                }
                else if(item.getItemId() == R.id.CamaraItem) {
                    setTitle(R.string.camara);
                }
                else if(item.getItemId() == R.id.MapaItem) {
                    setTitle(R.string.mapa);
                    image.setVisibility(View.VISIBLE);

                } if(item.getItemId() != R.id.MapaItem){

                    image.setVisibility(View.INVISIBLE);
                }
                return true ;
            }

        });

        inicializar();
    }


    private void inicializar() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.d("FirebaseUser", "Correo Usuario: "+firebaseUser.getEmail());
                } else{
                    Log.d("FirebaseUser", "El usuario ha cerrado sesiòn");
                    goLoginScreen();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.miPerfil){
            Intent i = new Intent(MainActivity.this, miPerfil.class);
            startActivity(i);
        }
        if(item.getItemId() == R.id.Buscaritem){
            Toast.makeText(MainActivity.this, "BUSCAR", Toast.LENGTH_SHORT).show();
            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void goLoginScreen() {
        Intent intent = new Intent(this,LogginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
