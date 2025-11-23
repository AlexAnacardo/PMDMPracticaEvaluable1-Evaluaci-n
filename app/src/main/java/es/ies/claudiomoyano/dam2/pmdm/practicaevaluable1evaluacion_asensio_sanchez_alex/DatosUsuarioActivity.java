package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DatosUsuarioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario);


        SharedPreferences prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE);

        String nombre = prefs.getString("nombre", "Sin nombre");
        String sexo = prefs.getString("sexo", "Sin sexo");
        String fecha = prefs.getString("fechaNacimiento", "Sin fecha");
        String hora = prefs.getString("horaNacimiento", "Sin hora");
        String uriFoto = prefs.getString("uriFotoSeleccionada", null);

        TextView tvNombre = findViewById(R.id.tvNombreUsuario);
        TextView tvFecha = findViewById(R.id.tvFechaNacimiento);
        TextView tvHora = findViewById(R.id.tvHoraNacimiento);
        TextView tvSexo = findViewById(R.id.tvSexo);
        ImageView fotoPerfil = findViewById(R.id.fotoPerfil);

        tvNombre.setText(nombre);
        tvFecha.setText(fecha);
        tvHora.setText(hora);
        tvSexo.setText(sexo);

        if (uriFoto != null) {
            Uri uri = Uri.parse(uriFoto);
            fotoPerfil.setImageURI(uri);

            // IMPORTANTE para que funcione al volver a abrir la app
            fotoPerfil.setClipToOutline(true);
        }


        Button botonEditar = findViewById(R.id.botonEditar);
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditar = new Intent(DatosUsuarioActivity.this, EditarActivity.class);
                startActivity(intentEditar);
            }
        });
    }
}
