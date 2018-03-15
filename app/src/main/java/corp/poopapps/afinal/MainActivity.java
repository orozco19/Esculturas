package corp.poopapps.afinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String usuario,contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (String)getIntent().getExtras().getString("usuario");
        contrasena = (String)getIntent().getExtras().getString("contrasena");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.miPerfil){
            //Toast.makeText(this,"miperfil",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, miPerfil.class);
            i.putExtra("usuario", usuario);
            i.putExtra("contrasena",contrasena);
            startActivity(i);
        }
        if(item.getItemId() == R.id.logout){
            //Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
