package corp.poopapps.afinal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    Button bIngreso;
    EditText eCorreo, eContrasena, eContrasena2;
    Intent data = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        bIngreso = (Button)findViewById(R.id.bIngreso);
        eCorreo = (EditText)findViewById(R.id.eCorreo);
        eContrasena = (EditText)findViewById(R.id.eContrasena);
        eContrasena2 = (EditText)findViewById(R.id.eContrasena2);

    }

    public void onClick(View view) {
        String correo,contrasena,contrasena2;

        int error = 0;

        correo = eCorreo.getText().toString();
        contrasena = eContrasena.getText().toString();
        contrasena2 = eContrasena2.getText().toString();


        if(correo.matches("")){
            eCorreo.setError("¡Campo vacío!");
            error = 1;
        }
        if(contrasena.matches("")){
            eContrasena.setError("¡Campo vacío!");
            error = 1;
        }
        if(contrasena2.matches("")){
            eContrasena2.setError("¡Campo vacío!");
            error = 1;
        }
        if(!contrasena.equals(contrasena2)){
            error = 1;
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Las contraseñas no coinciden.", Toast.LENGTH_SHORT);

            toast1.show();

            eContrasena.setText("");
            eContrasena2.setText("");
        }

        if(error == 0) {

            data.putExtra("Correo", correo);
            data.putExtra("Contrasena", contrasena);

            setResult(RESULT_OK, data);

            finish();
        }

    }
}
