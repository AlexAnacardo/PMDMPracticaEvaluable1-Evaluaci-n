package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executors;

public class ActivityLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        Button botonRegistrarse, botonLogin;
        EditText etNombre, etContraseña;

        etNombre = findViewById(R.id.etUser);
        etContraseña = findViewById(R.id.etLogin);

        botonLogin = findViewById(R.id.botonLogin);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre, contraseña;
                nombre = etNombre.getText().toString();
                contraseña= etContraseña.getText().toString();

                Executors.newSingleThreadExecutor().execute(() -> {
                    Usuario usuario = DatabaseClient
                            .getInstance(ActivityLogin.this)
                            .getDb()
                            .usuarioDao()
                            .obtenerPorNombreContraseña(nombre, contraseña);

                    runOnUiThread(() -> {
                        if (usuario != null) {
                            startActivity(new Intent(ActivityLogin.this, MainActivity.class));
                        } else {
                            Toast.makeText(ActivityLogin.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
        });

        botonRegistrarse = findViewById(R.id.botonRegistrarse);

        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCrear = new Intent(ActivityLogin.this, CrearActivity.class);

                startActivity(intentCrear);
            }
        });
    }
}
