package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EditarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_usuario);

        Button botonAniadirFecha = findViewById(R.id.botonSeleccionarFecha);
        Button botonSeleccionaFoto = findViewById(R.id.botonSeleccionarFoto);
        Button botonAceptarEditar = findViewById(R.id.botonAceptarEditar);

        EditText nombreIntroducido = findViewById(R.id.etNombre);
        RadioGroup selectorSexo = findViewById(R.id.rbGroupSexo);


        botonAceptarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("nombre", nombreIntroducido.getText().toString());

                int radioSeleccionado = selectorSexo.getCheckedRadioButtonId();

                if(radioSeleccionado == R.id.rbHombre){
                    editor.putString("sexo", "Hombre");
                }else if(radioSeleccionado == R.id.rbMujer){
                    editor.putString("sexo", "Mujer");
                }else{
                    editor.putString("sexo", "Otro");
                }


                editor.apply();

            }
        });

    }
}
