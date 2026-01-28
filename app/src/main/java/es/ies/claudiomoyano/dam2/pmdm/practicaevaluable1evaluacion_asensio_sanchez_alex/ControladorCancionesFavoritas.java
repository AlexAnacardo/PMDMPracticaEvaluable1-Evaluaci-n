package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ControladorCancionesFavoritas {

    private ArrayList<Cancion> listaCanciones = new ArrayList<>();
    SharedPreferences prefs;

    public void cargarFavoritas(Context context){
        Executors.newSingleThreadExecutor().execute(() -> {

            prefs = context.getSharedPreferences("usuarioLogueado", MODE_PRIVATE);

            listaCanciones.clear();

            listaCanciones = new ArrayList<>( DatabaseClient
                    .getInstance(context)
                    .getDb()
                    .usuarioCancionFavoritaDao().obtenerCancionesFavoritas(prefs.getLong("idUsuario", 1)));
        });
    }

    public void obtenerListadoFavoritas(Context context, Consumer<ArrayList<Cancion>> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {

            SharedPreferences prefs =
                    context.getSharedPreferences("usuarioLogueado", MODE_PRIVATE);

            ArrayList<Cancion> resultado = new ArrayList<>(
                    DatabaseClient.getInstance(context)
                            .getDb()
                            .usuarioCancionFavoritaDao()
                            .obtenerCancionesFavoritas(
                                    prefs.getLong("idUsuario", 1)
                            )
            );

            // volver al hilo principal, uso callback accept para pasarle al hilo principal la lista de canciones
            //no se puede modificar nada de la UI desde un hilo secundario
            new Handler(Looper.getMainLooper()).post(() -> {
                callback.accept(resultado);
            });
        });
    }

    public void guardarCancion(Context context, Cancion cancion, Consumer<Boolean> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            SharedPreferences prefs = context.getSharedPreferences("usuarioLogueado", MODE_PRIVATE);
            long idUsuario = prefs.getLong("idUsuario", 1);
            var db = DatabaseClient.getInstance(context).getDb();

            // Insertar canción si no existe
            Cancion existente = db.cancionDao().obtenerPorId(cancion.getId());
            if (existente == null) {
                long idCancion = db.cancionDao().insertarCancion(cancion); // IGNORE si ya existe
                cancion.id = idCancion;
            } else {
                cancion.id = existente.id;
            }

            // Comprobar si ya es favorita
            int existe = db.usuarioCancionFavoritaDao().existeFavorito(idUsuario, cancion.getId());

            boolean insertada;
            if (existe == 0) {
                db.usuarioCancionFavoritaDao().insertarFavorito(new UsuarioCancionFavorita(idUsuario, cancion.getId()));
                insertada = true;
            } else {
                insertada = false;
            }

            // Volver al hilo principal, devuelvo el resultado de la operacion
            new Handler(Looper.getMainLooper()).post(() -> callback.accept(insertada));
        });
    }

    public void eliminarFavorita(Context context, Cancion cancion){
        Executors.newSingleThreadExecutor().execute(() -> {

            prefs = context.getSharedPreferences("usuarioLogueado", MODE_PRIVATE);

            UsuarioCancionFavorita ucf =

                    DatabaseClient
                            .getInstance(context)
                            .getDb()
                            .usuarioCancionFavoritaDao().getCancionFavorita(prefs.getLong("idUsuario", 1), cancion.getId());

            DatabaseClient
                    .getInstance(context)
                    .getDb()
                    .usuarioCancionFavoritaDao().eliminarFavorito(ucf);

            cargarFavoritas(context);

            //Si la cancion ya no esta en la tabla de favoritos, la elimino de la base de datos
            if(!(DatabaseClient.getInstance(context).getDb().usuarioCancionFavoritaDao().obtenerTotalFavoritosCancion(cancion.getId()) == 0)){
                DatabaseClient
                        .getInstance(context)
                        .getDb()
                        .cancionDao().borrarCancion(cancion);
            }
        });
    }
}
