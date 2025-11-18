package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity implements RecyclerCancionesInterface{

    ArrayList<Cancion> listaCancionesFavoritas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ControladorCancionesFavoritas controladorCancionesFavoritas = new ControladorCancionesFavoritas();

        setContentView(R.layout.canciones_favoritas);

        listaCancionesFavoritas = controladorCancionesFavoritas.obtenerListadoFavoritas(this);

        AdaptadorCanciones adaptadorFavoritos = new AdaptadorCanciones(listaCancionesFavoritas, this);

        RecyclerView rvCanciones = findViewById(R.id.rvCancionesFavoritas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvCanciones.setLayoutManager(linearLayoutManager);

        rvCanciones.setAdapter(adaptadorFavoritos);
    }

    @Override
    public void onItemClick(int posicion) {

    }

    @Override
    public void onItemLongClick(int posicion) {

    }
}
