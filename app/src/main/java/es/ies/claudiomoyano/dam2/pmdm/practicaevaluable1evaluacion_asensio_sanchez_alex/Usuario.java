package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;
    @ColumnInfo(name = "nombre")
    public String nombre;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "fechaNacimiento")
    public String fechaNacimiento;
    @ColumnInfo(name = "horaNacimiento")
    public String horaNacimiento;
    @ColumnInfo(name = "sexo")
    public String sexo;
    @ColumnInfo(name = "fotoPerfil", typeAffinity = ColumnInfo.BLOB)
    public byte[] imagen;

    public Usuario(String nombre, String password, String fechaNacimiento, String horaNacimiento, String sexo, byte[] imagen) {
        this.nombre = nombre;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
        this.horaNacimiento = horaNacimiento;
        this.sexo = sexo;
        this.imagen = imagen;
    }
}
