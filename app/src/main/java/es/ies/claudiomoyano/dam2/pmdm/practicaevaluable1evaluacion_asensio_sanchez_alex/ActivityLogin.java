package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executors;

public class ActivityLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        //Accedo a las preferencias del usuario para cargar automaticamente el modo oscuro/default que tenga elegido el usuario como predeterminado
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        boolean modoOscuro = prefs.getBoolean("modo_oscuro", false);

        if (modoOscuro) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        100
                );
            }
        }

        if (ContextCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivityLogin.this, new String[]{Manifest.permission.SEND_SMS}, 100);
        } else {
            Intent serviceIntent = new Intent(ActivityLogin.this, ServicioBateria.class);
            startService(serviceIntent);
            Toast.makeText(ActivityLogin.this, "Servicio de batería iniciado", Toast.LENGTH_SHORT).show();
        }

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

                    //Si el usuario Admin no existe en el sistema, lo añado
                    Usuario usuarioAdmin = DatabaseClient
                            .getInstance(ActivityLogin.this)
                            .getDb()
                            .usuarioDao()
                            .obtenerPorNombreContraseña("Admin", "Admin");

                    if(usuarioAdmin==null){
                        DatabaseClient.getInstance(ActivityLogin.this).getDb().usuarioDao().insertarUsuario(new Usuario("Admin", "Admin", "348676106", null, null, null, null));
                    }

                    Usuario usuario = DatabaseClient
                            .getInstance(ActivityLogin.this)
                            .getDb()
                            .usuarioDao()
                            .obtenerPorNombreContraseña(nombre, contraseña);

                    runOnUiThread(() -> {
                        if (usuario != null) {
                            //Guardo el id del usuario logueado en shared preferences para obtener el usaurio en la activity de editar
                            //y en la de listar canciones favoritas
                            SharedPreferences prefs = getSharedPreferences("usuarioLogueado", MODE_PRIVATE);
                            prefs.edit().putLong("idUsuario", usuario.getId()).apply();

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
