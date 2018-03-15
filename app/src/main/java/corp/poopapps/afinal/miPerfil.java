package corp.poopapps.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class miPerfil extends AppCompatActivity {

    TextView usuario, contrasena;
    String user, password;
   // boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        usuario = (TextView)findViewById(R.id.perfilusuario);
        contrasena = (TextView)findViewById(R.id.perfilcontrasena);

        user = (String)getIntent().getExtras().getString("usuario");
        password = (String)getIntent().getExtras().getString("contrasena");

        usuario.setText(user);
        contrasena.setText(password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.perfilmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Principal){
            //Toast.makeText(this,"miperfil",Toast.LENGTH_SHORT).show();
            finish();
        }
        if(item.getItemId() == R.id.logout){
            //Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(miPerfil.this, LogginActivity.class);
            i.putExtra("usuario", user);
            i.putExtra("contrasena", password);
           // i.putExtra("flag", flag);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
