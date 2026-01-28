package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "usuarioCancionFavorita",
        primaryKeys = {"idUsuario", "idCancion"},
        foreignKeys = {
                @ForeignKey(entity = Cancion.class,
                            parentColumns = "id",
                            childColumns = "idCancion",
                            onDelete = ForeignKey.CASCADE),

                @ForeignKey(entity = Usuario.class,
                        parentColumns = "id",
                        childColumns = "idUsuario",
                        onDelete = ForeignKey.CASCADE),
        }
)
public class UsuarioCancionFavorita {
    @ColumnInfo(name = "idUsuario")
    public long idUsuario;
    @ColumnInfo(name = "idCancion")
    public long idCancion;

    public UsuarioCancionFavorita(long idUsuario, long idCancion) {
        this.idUsuario = idUsuario;
        this.idCancion = idCancion;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(long idCancion) {
        this.idCancion = idCancion;
    }
}
