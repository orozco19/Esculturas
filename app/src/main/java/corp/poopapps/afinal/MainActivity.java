package corp.poopapps.afinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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
    private FrameLayout frameLayout;
    FragmentManager fm;
    FragmentTransaction ft;

    String usuario, contrasena;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        EsculturasFragment esculturasFragment = new EsculturasFragment();
        ft.add(R.id.frame, esculturasFragment).commit();       //Primer listar a cargar
        frameLayout = findViewById(R.id.frame);

        inicializar();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ft = fm.beginTransaction();
            switch (item.getItemId()) {
                case R.id.EsculturasItem:
                    setTitle(R.string.esculturas);
                    EsculturasFragment esculturasFragment = new EsculturasFragment();
                    ft.replace(R.id.frame, esculturasFragment).commit();
                    return true;
                case R.id.EscultoresItem:
                    setTitle(R.string.escultores);
                    EscultoresFragment escultoresFragment = new EscultoresFragment();
                    ft.replace(R.id.frame, escultoresFragment).commit();
                    return true;
                case R.id.CamaraItem:
                    setTitle(R.string.Ar);
                    ARFragment arFragment = new ARFragment();
                    ft.replace(R.id.frame, arFragment).commit();
                    return true;
                case R.id.MapaItem:
                    setTitle(R.string.mapa);
                    MapaEsculturasFragment mapaEsculturasFragment = new MapaEsculturasFragment();
                    ft.replace(R.id.frame, mapaEsculturasFragment ).commit();
                    return true;
            }
            return false;
        }
    };


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
