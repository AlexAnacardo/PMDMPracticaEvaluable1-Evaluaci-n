package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    long insertarUsuario(Usuario usuario);

    @Update
    int actualizarUsuario(Usuario usuario);

    @Delete
    int borrarUsuario(Usuario usuario);

    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerTodos();

    @Query("SELECT * FROM usuarios where nombre = :nombre AND password = :pass LIMIT 1")
    Usuario obtenerPorNombreContraseña(String nombre, String pass);

    @Query("SELECT * FROM usuarios where id = :id")
    Usuario obtenerPorId(long id);
}
