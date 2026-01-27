package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface CancionDao {
    @Insert
    long insertarCancion(Cancion cancion);

    @Delete
    int borrarCancion(Cancion cancion);

    @Query("SELECT * FROM canciones")
    List<Cancion> obtenerTodas();

    @Query("SELECT * FROM canciones where titulo = :titulo LIMIT 1")
    Usuario obtenerPorNombreCancion(String titulo);
}
