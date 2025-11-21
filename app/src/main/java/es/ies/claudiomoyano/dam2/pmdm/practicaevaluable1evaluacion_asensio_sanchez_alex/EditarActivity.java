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
                if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.setType("image/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    startActivityForResult(intent, 2);
                }
            }
        });

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

                editor.putString("fechaNacimiento", diaNacimiento+"/"+mesNacimiento+"/"+anioNacimineto);

                editor.putString("uriFotoSeleccionada", imagenSeleccionada.toString());


                editor.apply();

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

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            imagenSeleccionada = uri;
        }
    }
}
