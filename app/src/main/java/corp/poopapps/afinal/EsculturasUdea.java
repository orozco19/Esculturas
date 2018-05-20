package corp.poopapps.afinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EsculturasUdea extends AppCompatActivity {

    private TextView tNombre, tAno, tUbicacion, tFoto, tEscultor, tId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esculturas_udea);
    }

}
