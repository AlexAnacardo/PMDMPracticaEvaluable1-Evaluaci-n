package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class EditarActivity extends AppCompatActivity {

    int anioNacimineto, mesNacimiento, diaNacimiento, horaNacimiento, minutoNacimiento;



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


                editor.apply();

            }
        });
    }
}
