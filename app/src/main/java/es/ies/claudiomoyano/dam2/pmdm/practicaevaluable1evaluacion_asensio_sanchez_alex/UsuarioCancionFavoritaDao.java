package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UsuarioCancionFavoritaDao {
    @Insert
    void insertarFavorito(UsuarioCancionFavorita usuarioCancionFavorita);

    @Delete
    void eliminarFavorito(UsuarioCancionFavorita usuarioCancionFavorita);


    @Query("SELECT c.* FROM canciones c INNER JOIN usuarioCancionFavorita uc ON c.id = uc.idCancion WHERE uc.idUsuario = :idUsuario")
    List<Cancion> obtenerCancionesFavoritas(long idUsuario);

    @Query("Select count(*) from usuarioCancionFavorita where idCancion = :idCancion")
    int obtenerTotalFavoritosCancion(long idCancion);

    @Query("Select * from usuarioCancionFavorita where idCancion = :idCancion and idUsuario = :idUsuario")
    UsuarioCancionFavorita getCancionFavorita(long idCancion, long idUsuario);

    @Query(" SELECT COUNT(*) FROM usuarioCancionFavorita WHERE idUsuario = :idUsuario AND idCancion = :idCancion")
    int existeFavorito(long idUsuario, long idCancion);

}

