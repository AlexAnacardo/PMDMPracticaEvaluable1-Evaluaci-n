package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "canciones")
public class Cancion {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;
    @ColumnInfo(name = "titulo")
    String titulo;
    @ColumnInfo(name = "artista")
    String artista;
    @ColumnInfo(name = "duracion")
    int duracion;
    @ColumnInfo(name = "fotoCancion")
    String foto;
    @ColumnInfo(name = "fechaLanzamiento")
    String fechaLanzamiento;
    @ColumnInfo(name = "nombreAlbum")
    String nombreAlbum;
    @ColumnInfo(name = "fotoArtista")
    String fotoArtista;

    public Cancion(String titulo, String artista, int duracion, String foto, String fechaLanzamiento, String nombreAlbum, String fotoArtista) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
        this.foto = foto;
        this.fechaLanzamiento = fechaLanzamiento;
        this.nombreAlbum = nombreAlbum;
        this.fotoArtista = fotoArtista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public String getFotoArtista() {
        return fotoArtista;
    }

    public void setFotoArtista(String fotoArtista) {
        this.fotoArtista = fotoArtista;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cancion)) return false;
        Cancion otra = (Cancion) obj;
        return titulo.equals(otra.titulo) && artista.equals(otra.artista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, artista);
    }
}
