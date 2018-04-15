package corp.poopapps.afinal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PruebaActivity extends AppCompatActivity {
    private EditText eNombre, eCorreo, eEdad;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        eNombre = findViewById(R.id.eNombre);
        eCorreo = findViewById(R.id.eCorreo);
        eEdad = findViewById(R.id.eEdad);
    }

    public void ClickPrueba(View view) {
/*
                Usuarios usuarios = new usuarios(databaseReference.push().getKey(),
                eNombre.getText().toString(),
                eCorreo.getText().toString(),
                Integer.parseInt(eEdad.getText().toString()));

        databaseReference.child("usuarios").setValue(usuarios);
    }*/
    }
}
