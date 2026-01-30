package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario.class, Cancion.class, UsuarioCancionFavorita.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract UsuarioDao usuarioDao();
    public abstract CancionDao cancionDao();

    public abstract UsuarioCancionFavoritaDao usuarioCancionFavoritaDao();
}
