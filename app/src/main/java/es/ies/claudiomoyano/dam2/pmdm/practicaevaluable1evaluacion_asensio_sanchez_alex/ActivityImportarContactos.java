package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executors;

public class ActivityImportarContactos extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_importar_contactos);

        checkContactsPermission();
    }


    private void checkContactsPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
        ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    101
            );

        } else {
            importContacts();
        }
    }



    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            importContacts();
        }
    }


    private void importContacts() {

        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        if (cursor == null) return;

        int nameIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        );

        int phoneIndex = cursor.getColumnIndexOrThrow(
                ContactsContract.CommonDataKinds.Phone.NUMBER
        );

        while (cursor.moveToNext()) {
            Executors.newSingleThreadExecutor().execute(() -> {
                //En este caso hare que la contraseña y el nombre sean la misma
                Usuario usuario = new Usuario(cursor.getString(nameIndex), cursor.getString(nameIndex), cursor.getString(phoneIndex), null, null, null, null);

                DatabaseClient
                        .getInstance(ActivityImportarContactos.this)
                        .getDb()
                        .usuarioDao()
                        .insertarUsuario(usuario);
            });
        }

        Toast.makeText(ActivityImportarContactos.this, R.string.contactosImportados, Toast.LENGTH_SHORT).show();

        cursor.close();
        finish();
    }
}
