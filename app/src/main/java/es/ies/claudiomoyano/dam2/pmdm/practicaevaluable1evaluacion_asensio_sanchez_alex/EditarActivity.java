package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditarActivity extends AppCompatActivity {

    int anioNacimineto, mesNacimiento, diaNacimiento, horaNacimiento, minutoNacimiento;
    Uri imagenSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_usuario);

        Button botonAniadirFecha = findViewById(R.id.botonSeleccionarFecha);
        Button botonAniadirHora = findViewById(R.id.botonSeleccionarHora);
        Button botonSeleccionaFoto = findViewById(R.id.botonSeleccionarFoto);
        Button botonAceptarEditar = findViewById(R.id.botonAceptarEditar);



        EditText nombreIntroducido = findViewById(R.id.etNombre);
        RadioGroup selectorSexo = findViewById(R.id.rbGroupSexo);


        DatePickerDialog.OnDateSetListener fecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                anioNacimineto = year;
                mesNacimiento = month;
                diaNacimiento = day;
            }
        };

        botonAniadirFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(EditarActivity.this, fecha, 2000, 1, 1);
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

        botonAniadirHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialogoHora = new TimePickerDialog(EditarActivity.this, hora, 0, 0, true);
                dialogoHora.show();
            }
        });


        botonSeleccionaFoto.setOnClickListener(new View.OnClickListener() {
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

        botonAceptarEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("datos_usuario", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();

                String nombreNuevo = nombreIntroducido.getText().toString().trim();
                String nombreAnterior = prefs.getString("nombre", "");

                if (!nombreNuevo.isEmpty()) {
                    editor.putString("nombre", nombreNuevo);
                } else {
                    editor.putString("nombre", nombreAnterior);
                }


                int radioSeleccionado = selectorSexo.getCheckedRadioButtonId();

                String sexoAnterior = prefs.getString("sexo", "");

                if (radioSeleccionado == R.id.rbHombre) {
                    editor.putString("sexo", getString(R.string.botonHombre));
                } else if (radioSeleccionado == R.id.rbMujer) {
                    editor.putString("sexo", getString(R.string.botonMujer));
                } else if (radioSeleccionado == R.id.rbOtro) {
                    editor.putString("sexo", getString(R.string.botonOtro));
                } else {
                    // si no seleccionó nada, mantener valor anterior
                    editor.putString("sexo", sexoAnterior);
                }

                String fechaAnterior = prefs.getString("fechaNacimiento", "");
                String fechaNueva = diaNacimiento + "/" + mesNacimiento + "/" + anioNacimineto;


                if (diaNacimiento != -1 && mesNacimiento != -1 && anioNacimineto != -1) {
                    editor.putString("fechaNacimiento", fechaNueva);
                } else {
                    editor.putString("fechaNacimiento", fechaAnterior);
                }

                String horaAnterior = prefs.getString("horaNacimiento", "");
                String horaNueva = horaNacimiento + ":" + minutoNacimiento;

                if (horaNacimiento != -1 && minutoNacimiento != -1) {
                    editor.putString("horaNacimiento", horaNueva);
                } else {
                    editor.putString("horaNacimiento", horaAnterior);
                }

                String fotoAnterior = prefs.getString("uriFotoSeleccionada", "");

                if (imagenSeleccionada != null) {
                    editor.putString("uriFotoSeleccionada", imagenSeleccionada.toString());
                } else {
                    editor.putString("uriFotoSeleccionada", fotoAnterior);
                }

                editor.apply();

                Intent intentFinalizar = new Intent(EditarActivity.this, DatosUsuarioActivity.class);
                startActivity(intentFinalizar);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] p, @NonNull int[] g) {
        super.onRequestPermissionsResult(requestCode, p, g);

        if (requestCode == 1) {
            if (g.length > 0 && g[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                startActivityForResult(intent, 2);
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Si la actividad lanzada fue la de seleccionar la foto y el usuario subio un archivo, se ejecuta el codigo
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            if (uri != null) {

                imagenSeleccionada = uri;

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
}
