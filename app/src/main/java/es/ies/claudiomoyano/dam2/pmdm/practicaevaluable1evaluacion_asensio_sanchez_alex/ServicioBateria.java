package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.concurrent.Executors;


public class ServicioBateria extends Service {

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Extra level devuelve el % actual de bateria, scale devuelve el maximo
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            int porcentajeBateria = (int) ((level / (float) scale) * 100);

            if (porcentajeBateria <= 5) {
                Toast.makeText(context,
                        "Batería baja: " + porcentajeBateria + "%",
                        Toast.LENGTH_LONG).show();

                sendSMS("Batería baja (" + porcentajeBateria + "%)");
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendSMS(String mensaje) {

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Usuario usuarioAdmin = DatabaseClient
                        .getInstance(ServicioBateria.this)
                        .getDb()
                        .usuarioDao()
                        .obtenerPorNombreContraseña("Admin", "Admin");

                if(usuarioAdmin == null){
                    usuarioAdmin = new Usuario("Admin", "Admin", "348676106", null, null, null, null);
                }

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(usuarioAdmin.getTelefono(), null, mensaje, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
