package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements RecyclerCancionesInterface {
    ArrayList<Cancion> listaCanciones = new ArrayList<>();
    AdaptadorCanciones adaptadorCanciones = new AdaptadorCanciones(listaCanciones, this);

    ControladorCancionesFavoritas controladorCancionesFavoritas = new ControladorCancionesFavoritas();

    private int cancionSeleccionada = -1;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        //Indico a la aplicacion que mi toolbar sera la action bar
        setSupportActionBar(toolbar);
        //Desactivo que se muestre el nombre de la aplicacion en el toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        /* Esto se ejecuta en un hilo secundario porque de hacerlo en el principal, el listado de canciones no
           se carga correctamente debido a que android bloquea tareas que tarden mucho en ejecutarse en el onCreate del
           main
        */
        new Thread(() -> {
            //Cargo un array list de canciones
            ArrayList<Cancion> canciones = DeezerApi.cargarPopulares();

            //Este metodo permite a un hilo secundario modificar elementos del hilo principal
            runOnUiThread(() -> {
                //Añado a la lista de canciones del metodo principal
                listaCanciones.clear();
                if (canciones != null) {
                    listaCanciones.addAll(canciones);
                }
                //Refresco para que se muestren en la interfaz
                adaptadorCanciones.notifyDataSetChanged();
            });
        }).start();
    }

    @Override
    public void onItemClick(int posicion) {
        Intent detalleCancionIntent = new Intent(this, DetalleCancionActivity.class);
        Cancion cancion = listaCanciones.get(posicion);

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
        openContextMenu(findViewById(R.id.rvCanciones));
    }

    /*MENU CONTEXTUAL*/
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

            Cancion cancion = listaCanciones.get(posicion);

            if(controladorCancionesFavoritas.guardarCancion(this, cancion)){
                Toast.makeText(getApplicationContext(), "Cancion añadida afavoritos", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "La cancion ya estaba añadida a favoritos", Toast.LENGTH_SHORT).show();
            }

            return true;
    }

    /*MENU OPCIONES*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opcion_buscar) {

            Intent intentBuscar = new Intent(this, ActivityBuscar.class);

            startActivity(intentBuscar);

            return true;
        }else if(id == R.id.opcion_favorito){
            Intent intentFavoritos = new Intent(this, FavoritosActivity.class);

            startActivity(intentFavoritos);

            return true;
        }
        else if(id == R.id.acerca_de){
            new InfoDialogFragment().show(getSupportFragmentManager(), "Info");
            return true;

        }
        else if(id == R.id.datos_usuario){
            Intent intentDatosUsuario = new Intent(this, DatosUsuarioActivity.class);
            startActivity(intentDatosUsuario);
            return true;
        }
        else{
            return true;
        }

    }
}