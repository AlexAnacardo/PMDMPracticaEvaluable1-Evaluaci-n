package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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

        //Recojo el switch
        Switch switchModooscuro = findViewById(R.id.modoOscuro);

        //Recojo el modo actual de la aplicacion, uso "& Configuration.UI_MODE_NIGHT_MASK" para obtener solamente si estoy en
        //modo claro u oscuro
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        //Si el modo activo es el oscuro, pongo el switch activado por defecto
        switchModooscuro.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        //Le asigno un listener para cada vez que se active/desactive, al hacerlo se activara y desactivara el modo noche
        //lo que cambia el colors.xml que la app usa por defecto
        switchModooscuro.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }
}
