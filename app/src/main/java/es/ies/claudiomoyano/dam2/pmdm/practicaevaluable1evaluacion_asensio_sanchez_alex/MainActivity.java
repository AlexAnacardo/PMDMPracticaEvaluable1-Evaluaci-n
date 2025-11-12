package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerCancionesInterface{
    ArrayList<Cancion> listaCanciones = new ArrayList<>();
    AdaptadorCanciones adaptadorCanciones = new AdaptadorCanciones(listaCanciones, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rvCanciones = findViewById(R.id.rvCanciones);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvCanciones.setLayoutManager(linearLayoutManager);

        rvCanciones.setAdapter(adaptadorCanciones);
        registerForContextMenu(rvCanciones);

        new Thread(() -> {
            // Esto se ejecuta en un hilo secundario
            ArrayList<Cancion> canciones = DeezerApi.cargarPopulares();

            // Actualizar la UI en el hilo principal
            runOnUiThread(() -> {
                listaCanciones.clear();
                if (canciones != null) {
                    listaCanciones.addAll(canciones);
                }
                adaptadorCanciones.notifyDataSetChanged();
            });
        }).start();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int posicion) {

    }

    private void cargarCancionesPopulares() {

    }
}