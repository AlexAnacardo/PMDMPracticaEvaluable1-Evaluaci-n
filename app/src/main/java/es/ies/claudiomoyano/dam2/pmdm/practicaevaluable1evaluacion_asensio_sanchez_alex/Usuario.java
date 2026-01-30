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
    @ColumnInfo(name = "numeroTelefono")
    public String telefono;
    @ColumnInfo(name = "fechaNacimiento")
    public String fechaNacimiento;
    @ColumnInfo(name = "horaNacimiento")
    public String horaNacimiento;
    @ColumnInfo(name = "sexo")
    public String sexo;
    @ColumnInfo(name = "fotoPerfil", typeAffinity = ColumnInfo.BLOB)
    public byte[] imagen;

    public Usuario(String nombre, String password, String telefono, String fechaNacimiento, String horaNacimiento, String sexo, byte[] imagen) {
        this.nombre = nombre;
        this.password = password;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.horaNacimiento = horaNacimiento;
        this.sexo = sexo;
        this.imagen = imagen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getHoraNacimiento() {
        return horaNacimiento;
    }

    public void setHoraNacimiento(String horaNacimiento) {
        this.horaNacimiento = horaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
