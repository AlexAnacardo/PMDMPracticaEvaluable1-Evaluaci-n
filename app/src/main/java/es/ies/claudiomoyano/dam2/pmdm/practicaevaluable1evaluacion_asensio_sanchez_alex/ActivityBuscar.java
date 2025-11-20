package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivityBuscar extends AppCompatActivity implements RecyclerCancionesInterface, Serializable {

    ArrayList<Cancion> listaBusqueda = new ArrayList<>();

    AdaptadorCanciones adaptadorCanciones = new AdaptadorCanciones(listaBusqueda, this);

    private int cancionSeleccionada = -1;

    ControladorCancionesFavoritas controladorCancionesFavoritas = new ControladorCancionesFavoritas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);

        RecyclerView rvBusqueda = findViewById(R.id.rvBusqueda);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvBusqueda.setLayoutManager(linearLayoutManager);

        rvBusqueda.setAdapter(adaptadorCanciones);
        registerForContextMenu(rvBusqueda);

        EditText campoBusqueda = findViewById(R.id.campoBusqueda);
        Button botonBuscar = findViewById(R.id.botonBuscar);


        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreCancion = campoBusqueda.getText().toString();

                new Thread(() -> {
                    // Llamada a Deezer en segundo plano
                    ArrayList<Cancion> resultado = DeezerApi.buscarCancion(nombreCancion);

                    runOnUiThread(() -> {
                        // Actualizar la lista que usa el adaptador
                        listaBusqueda.clear();
                        listaBusqueda.addAll(resultado);

                        // Refrescar el RecyclerView
                        adaptadorCanciones.notifyDataSetChanged();
                    });
                }).start();
            }
        });

        //Si hay guardada una lista de una instancia anterior, se carga, lo uso para mantener el listado al
        //cambiar de vertical a horizontal
        if (savedInstanceState != null) {
            ArrayList<Cancion> guardada =
                    (ArrayList<Cancion>) savedInstanceState.getSerializable("listaBusqueda");

            if (guardada != null) {
                listaBusqueda.clear();
                listaBusqueda.addAll(guardada);

                adaptadorCanciones.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemClick(int posicion) {
        Intent detalleCancionIntent = new Intent(this, DetalleCancionActivity.class);
        Cancion cancion = listaBusqueda.get(posicion);

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
        openContextMenu(findViewById(R.id.rvBusqueda));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Recojo la posicion del metodo on long click
        int posicion = cancionSeleccionada;

        Cancion cancion = listaBusqueda.get(posicion);

        if(controladorCancionesFavoritas.guardarCancion(this, cancion)){
            Toast.makeText(getApplicationContext(), "Cancion añadida afavoritos", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "La cancion ya estaba añadida a favoritos", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    //Para mantener el listado de busqueda al cambair el display del layout
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("listaBusqueda", listaBusqueda);
    }

}
