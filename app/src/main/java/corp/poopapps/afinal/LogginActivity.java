package corp.poopapps.afinal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogginActivity extends AppCompatActivity {
    EditText eUsuario, eContrasena;
    Button bRegistro;
    String usuario, contrasena;
    public static final int REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin2);

        bRegistro = (Button)findViewById(R.id.bRegistrarse);
        eUsuario = (EditText)findViewById(R.id.eUsuario);
        eContrasena = (EditText)findViewById(R.id.eContrasena);
    }

    public void OnClickButton(View view) {

        Intent i = new Intent(LogginActivity.this, RegistroActivity.class);
        startActivityForResult(i,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Registro completo.", Toast.LENGTH_SHORT);

                toast1.show();
                usuario = data.getStringExtra("Correo");
                contrasena = data.getStringExtra("Contrasena");
            }
        }

       // super.onActivityResult(requestCode, resultCode, data);
    }

    public void ClickIngresar(View view) {

        if(eUsuario.getText().toString().equals(usuario) && eContrasena.getText().toString().equals(contrasena)){
            //USUARIO CONTRASEÑA CORRECTAS
            Intent i = new Intent(LogginActivity.this, MainActivity.class);
            i.putExtra("usuario", usuario);
            i.putExtra("contrasena",contrasena);
            startActivity(i);
        }else{
            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "Usuario o contraseña incorrecta.", Toast.LENGTH_LONG);

            toast1.show();
            eUsuario.setText("");
            eContrasena.setText("");
        }
    }
}
