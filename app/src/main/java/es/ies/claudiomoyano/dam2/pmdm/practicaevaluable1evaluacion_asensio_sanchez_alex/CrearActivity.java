package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.Executors;

public class CrearActivity extends AppCompatActivity {
    private EditText etNombre, etPassword;
    private Button btnFecha, btnHora, btnFoto, btnAceptar;
    private RadioGroup rgSexo;

    int idNotifications = 100;

    int anioNacimineto, mesNacimiento, diaNacimiento, horaNacimiento, minutoNacimiento;
    private byte[] imagenBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        etNombre = findViewById(R.id.etNombre);
        etPassword = findViewById(R.id.etCrearContraseña);
        btnFecha = findViewById(R.id.botonSeleccionarFecha);
        btnHora = findViewById(R.id.botonSeleccionarHora);
        btnFoto = findViewById(R.id.botonSeleccionarFoto);
        btnAceptar = findViewById(R.id.botonAceptarCrear);
        rgSexo = findViewById(R.id.rbGroupSexo);

        DatePickerDialog.OnDateSetListener fecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                anioNacimineto = year;
                mesNacimiento = month;
                diaNacimiento = day;
            }
        };

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(CrearActivity.this, fecha, 2000, 1, 1);
                dialogoFecha.show();
            }
        });

        TimePickerDialog.OnTimeSetListener hora = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaNacimiento = hourOfDay;
                minutoNacimiento = minute;
            }
        };

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialogoHora = new TimePickerDialog(CrearActivity.this, hora, 0, 0, true);
                dialogoHora.show();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si no se han concedido permisos para acceder al almacenamiento interno, se piden
                if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
                } else {
                    //Abro el selector de archivos y le indico que filtre solo las imagenes
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("image/*");

                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);

                    //Lanzo la actividad
                    startActivityForResult(intent, 2);
                }
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                if (nombre.isEmpty()) {
                    etNombre.setError("Introduce un nombre");
                    return;
                }

                String password = etPassword.getText().toString().trim();
                if (password.isEmpty()) {
                    etNombre.setError("Introduce una contraseña");
                    return;
                }


                int radioSeleccionado = rgSexo.getCheckedRadioButtonId();
                String sexo = "";

                if (radioSeleccionado == R.id.rbHombre) {
                    sexo = "H";
                } else if (radioSeleccionado == R.id.rbMujer) {
                    sexo = "M";
                } else if (radioSeleccionado == R.id.rbOtro) {
                    sexo = "O";
                } else {
                    Toast.makeText(CrearActivity.this, "Selecciona un sexo", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (diaNacimiento == -1 || mesNacimiento == -1 || anioNacimineto == -1) {
                    Toast.makeText(CrearActivity.this, "Selecciona fecha de nacimiento", Toast.LENGTH_SHORT).show();
                    return;
                }

                String fechaNacimiento =
                        diaNacimiento + "/" + mesNacimiento + "/" + anioNacimineto;


                if (horaNacimiento == -1 || minutoNacimiento == -1) {
                    Toast.makeText(CrearActivity.this, "Selecciona hora de nacimiento", Toast.LENGTH_SHORT).show();
                    return;
                }

                String horaNacimientoStr =
                        horaNacimiento + ":" + String.format("%02d", minutoNacimiento);


                if (imagenBytes == null) {
                    Toast.makeText(CrearActivity.this, "Selecciona una foto", Toast.LENGTH_SHORT).show();
                    return;
                }


                Usuario usuario = new Usuario(
                        nombre,
                        password,
                        fechaNacimiento,
                        horaNacimientoStr,
                        sexo,
                        imagenBytes
                );

                //Corro la insercion en un hilo secundario, no se puede hacer en el principal
                Executors.newSingleThreadExecutor().execute(() -> {

                    if(DatabaseClient.getInstance(CrearActivity.this).getDb().usuarioDao().obtenerPorNombreContraseña(nombre, password) == null){
                        DatabaseClient
                                .getInstance(CrearActivity.this)
                                .getDb()
                                .usuarioDao()
                                .insertarUsuario(usuario);

                        runOnUiThread(() -> {
                            Toast.makeText(CrearActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();

                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            String channelId = "idCanalUser";
                            CharSequence channelName = "Canal user";
                            int importance = NotificationManager.IMPORTANCE_LOW;
                            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
                            notificationManager.createNotificationChannel(notificationChannel);

                            NotificationCompat.Builder constructorNotificacion = new NotificationCompat.Builder(CrearActivity.this, channelId);
                            constructorNotificacion.setSmallIcon(R.drawable.ic_notification);
                            constructorNotificacion.setContentTitle("Usuario creado");
                            constructorNotificacion.setContentText("Se ha creado el usuario "+nombre);

                            constructorNotificacion.setAutoCancel(true);
                            NotificationManager notificador = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificador.notify(idNotifications, constructorNotificacion.build());
                            idNotifications++;
                            //Cierro el intent y vuelvo a login
                            finish();
                        });
                    }else{
                        Toast.makeText(CrearActivity.this, "Usuario ya existente", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Si la actividad lanzada fue la de seleccionar la foto y el usuario subio un archivo, se ejecuta el codigo
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            if (uri != null) {

                imagenBytes = uriToByteArray(uri);

                //Filtro los flags recogidos para solo obtener los de lectura y escritura, de dejar pasar otros la aplicacion crashea
                int takeFlags = data.getFlags();
                takeFlags &= (Intent.FLAG_GRANT_READ_URI_PERMISSION |
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

                try {
                    //Hago que el permiso sea persistente
                    getContentResolver().takePersistableUriPermission(uri, takeFlags);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] uriToByteArray(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            byte[] data = new byte[1024];
            int nRead;

            while ((nRead = inputStream.read(data)) != -1) {
                buffer.write(data, 0, nRead);
            }

            inputStream.close();
            return buffer.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
