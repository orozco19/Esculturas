package corp.poopapps.afinal;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarPerfilActivity extends AppCompatActivity {

    private String nombre, sexo;
    private int edad;
    private EditText eNombre, eEdad;
    private RadioButton femenino, masculino;
    private Button bGuardar;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        setTitle("Editar perfil");

        bGuardar = (Button)findViewById(R.id.bGuardar);
        eNombre = (EditText)findViewById(R.id.eNombre);
        eEdad = (EditText)findViewById(R.id.eEdad);
        femenino = (RadioButton)findViewById(R.id.femenino);
        masculino = (RadioButton)findViewById(R.id.masculino);

        inicializar();

        //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    private void inicializar() {

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.d("FirebaseUser", "Usuario Logeado: "+firebaseUser.getDisplayName());
                    Log.d("FirebaseUser", "Usuario Logeado: "+firebaseUser.getEmail());
                } else{
                    Log.d("FirebaseUser", "El usuario ha cerrado sesión");
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.editarperfilmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Atrasitem){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ClickGuardar(View view) {

        nombre = eNombre.getText().toString();
     //   edad = Integer.parseInt(eEdad.getText().toString());

        boolean error=false;

        if(!femenino.isChecked() && !masculino.isChecked()){
            error=true;
            Toast.makeText(EditarPerfilActivity.this, "Sexo no seleccionado", Toast.LENGTH_SHORT).show();
        }

        if(nombre.isEmpty()){
            error=true;
            eNombre.setError("¡Campo vacío!");
        }

        /*if(eEdad.getText().toString().isEmpty()){
            error=true;
            eEdad.setError("¡Campo vacío!");
        }*/

        if(femenino.isChecked()){
            sexo = femenino.getText().toString();
        }
        else if(masculino.isChecked()){
            sexo = masculino.getText().toString();
        }

        if(!error){
            ActualizarDatos();
        }else{
            Toast.makeText(EditarPerfilActivity.this, "No se realizaron cambios", Toast.LENGTH_SHORT).show();
        }

    }

    private void ActualizarDatos() {

        ActualizarUsuarios usuarios = new ActualizarUsuarios(firebaseAuth.getUid(),
                nombre,
                sexo,
                edad);

        databaseReference.child("Usuarios").child(firebaseAuth.getUid()).setValue(usuarios);

        Toast.makeText(EditarPerfilActivity.this, "¡PERFIL ACTUALIZADO!", Toast.LENGTH_SHORT).show();
    }


}
