package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetalleCancionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detalle_cancion);

        Intent intentPadre = getIntent();

        ImageView fotoCancion = findViewById(R.id.fotoCancion);
        TextView titulo = findViewById(R.id.tvNombreCancion);
        TextView artista = findViewById(R.id.tvNombreArtista);
        TextView duracion = findViewById(R.id.tvDuracion);
        TextView fechaLanzamiento = findViewById(R.id.tvFechalanzamiento);
        TextView nombreAlbum = findViewById(R.id.tvNombreAlbum);
        ImageView fotoArtista = findViewById(R.id.fotoArtista);

        Glide.with(this)
                .load(intentPadre.getStringExtra("foto"))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(fotoCancion);

        titulo.setText(intentPadre.getStringExtra("titulo"));
        artista.setText(intentPadre.getStringExtra("artista"));
        fechaLanzamiento.setText(intentPadre.getStringExtra("fechaLanzamiento"));


        nombreAlbum.setText(intentPadre.getStringExtra("nombreAlbum"));

        int segundosCancion = intentPadre.getIntExtra("duracion", 0);

        String duracionCancion = segundosCancion/60+" minutos";

        duracion.setText(duracionCancion);

        Glide.with(this)
                .load(intentPadre.getStringExtra("fotoArtista"))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(fotoArtista);




    }
}
