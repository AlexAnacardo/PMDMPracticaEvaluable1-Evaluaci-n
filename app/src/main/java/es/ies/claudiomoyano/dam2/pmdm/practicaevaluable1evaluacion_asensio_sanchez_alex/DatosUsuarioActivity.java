package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.concurrent.Executors;

public class DatosUsuarioActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario);

        Executors.newSingleThreadExecutor().execute(() -> {

            SharedPreferences prefs = getSharedPreferences("usuarioLogueado", MODE_PRIVATE);

            Usuario usuario = DatabaseClient
                    .getInstance(DatosUsuarioActivity.this)
                    .getDb()
                    .usuarioDao()
                    .obtenerPorId(prefs.getLong("idUsuario", -1));

            runOnUiThread(() -> {

                    String nombre = usuario.getNombre();
                    String telefono = usuario.getTelefono();
                    String sexo = usuario.getSexo();
                    String fecha = usuario.getFechaNacimiento();
                    String hora = usuario.getHoraNacimiento();
                    byte[] foto = usuario.getImagen();

                    TextView tvNombre = findViewById(R.id.tvNombreUsuario);
                    TextView tvTelefono = findViewById(R.id.tvTelefonoUsuario);
                    TextView tvFecha = findViewById(R.id.tvFechaNacimiento);
                    TextView tvHora = findViewById(R.id.tvHoraNacimiento);
                    TextView tvSexo = findViewById(R.id.tvSexo);
                    ImageView fotoPerfil = findViewById(R.id.fotoPerfil);

                    tvNombre.setText(nombre);
                    tvTelefono.setText(telefono);
                    tvFecha.setText(fecha);
                    tvHora.setText(hora);
                    tvSexo.setText(sexo);

                    if (foto != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);

                        fotoPerfil.setImageBitmap(bitmap);
                    }
            });
        });

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
