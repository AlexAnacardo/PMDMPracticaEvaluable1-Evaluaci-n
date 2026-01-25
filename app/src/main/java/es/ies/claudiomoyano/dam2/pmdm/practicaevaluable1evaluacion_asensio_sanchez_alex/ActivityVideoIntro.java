package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityVideoIntro extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        VideoView videoView = findViewById(R.id.videoViewIntro);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro);

        videoView.setVideoURI(uri);
        videoView.start();

        // Esto se ejecuta al terminar el video para redireccionar al login
        videoView.setOnCompletionListener(mp -> {
            Intent intent = new Intent(ActivityVideoIntro.this, ActivityLogin.class);
            startActivity(intent);
            // Uso esto para que el usuario no pueda volver atras
            finish();
        });
    }
}
