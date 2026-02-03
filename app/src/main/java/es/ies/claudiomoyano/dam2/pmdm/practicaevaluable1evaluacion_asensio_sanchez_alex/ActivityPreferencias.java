package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityPreferencias extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        //Cargo un fragmento de las preferencias creadas en preferences.xml
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment, new PreferenciasFragment()).commit();
    }
}
