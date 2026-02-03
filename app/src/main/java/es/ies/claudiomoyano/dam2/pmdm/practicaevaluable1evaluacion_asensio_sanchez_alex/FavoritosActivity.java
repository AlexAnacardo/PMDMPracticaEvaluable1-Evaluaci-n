package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritosActivity extends AppCompatActivity implements RecyclerCancionesInterface {

    ArrayList<Cancion> listaCancionesFavoritas = new ArrayList<>();

    private int cancionSeleccionada = -1;

    ControladorCancionesFavoritas controladorCancionesFavoritas = new ControladorCancionesFavoritas();

    AdaptadorCanciones adaptadorFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canciones_favoritas);

        RecyclerView rvCanciones = findViewById(R.id.rvCancionesFavoritas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvCanciones.setLayoutManager(linearLayoutManager);

        // Inicializo el adapter con lista vacía
        listaCancionesFavoritas = new ArrayList<>();
        adaptadorFavoritos = new AdaptadorCanciones(listaCancionesFavoritas, this);
        rvCanciones.setAdapter(adaptadorFavoritos);

        registerForContextMenu(rvCanciones);

        // Cargar canciones favoritas desde BD
        controladorCancionesFavoritas.obtenerListadoFavoritas(this, canciones -> {
            listaCancionesFavoritas.clear();
            listaCancionesFavoritas.addAll(canciones);
            adaptadorFavoritos.notifyDataSetChanged();
        });
    }


    @Override
    public void onItemClick(int posicion) {
        Intent detalleCancionIntent = new Intent(this, DetalleCancionActivity.class);
        Cancion cancion = listaCancionesFavoritas.get(posicion);

        detalleCancionIntent.putExtra("titulo", cancion.getTitulo());
        detalleCancionIntent.putExtra("artista", cancion.getArtista());
        detalleCancionIntent.putExtra("duracion", cancion.getDuracion());
        detalleCancionIntent.putExtra("foto", cancion.getFoto());
        detalleCancionIntent.putExtra("fechaLanzamiento", cancion.getFechaLanzamiento());
        detalleCancionIntent.putExtra("nombreAlbum", cancion.getNombreAlbum());
        detalleCancionIntent.putExtra("fotoArtista", cancion.getFotoArtista());

        startActivity(detalleCancionIntent);
    }

    @Override
    public void onItemLongClick(int posicion) {
        cancionSeleccionada = posicion;

        // Abrimos el menú contextual manualmente
        openContextMenu(findViewById(R.id.rvCancionesFavoritas));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_eliminar_favoritos, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Recojo la posicion del metodo on long click
        int posicion = cancionSeleccionada;

        Cancion cancion = listaCancionesFavoritas.get(posicion);

        controladorCancionesFavoritas.eliminarFavorita(this, cancion);
        listaCancionesFavoritas.remove(cancion);

        adaptadorFavoritos.notifyItemRemoved(posicion);

        Toast.makeText(getApplicationContext(), cancion.getTitulo()+" "+getString(R.string.eliminadaFavorita), Toast.LENGTH_SHORT).show();


        return true;
    }
}
