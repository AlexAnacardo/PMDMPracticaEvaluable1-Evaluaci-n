package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DeezerApi {

    public static ArrayList<Cancion> cargarPopulares(){
        ArrayList<Cancion> cancionesPopulares = new ArrayList<>();

        try{
            //Defino la URl de la api, esta carga las canciones mas populares del momento
            URL url = new URL("https://api.deezer.com/chart/0/tracks");

            //Defino la conexión http y le indico que se usara 'GET' para el request (lo normal para una API Rest vaya)
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Defino un reader en el que cargo todo el texto recogido de la llamada a la api
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null){
                //Voy leyendo linea a linea del reader y lo voy cargando el contenido en el stringbuilder
                sb.append(linea);
            }
            reader.close();

            //Construyo un objeto json desde el builder
            JSONObject json = new JSONObject(sb.toString());
            //Recojo el elemento raiz del json
            JSONArray data = json.getJSONArray("data");


            for (int i = 0; i < data.length(); i++) {
                //En cada iteración del bucle accedo a una cancion
                JSONObject track = data.getJSONObject(i);

                //De dicha canción recogo los siguientes datos
                String titulo = track.getString("title");
                String artista = track.getJSONObject("artist").getString("name");
                int duracion = track.getInt("duration");
                String foto = track.getJSONObject("album").getString("cover");
                //Como no todas las canciones tienen definida una fecha de lanzamiento, uso optString, si la tiene, se mostrara, si no, saldrá "Desconocida"
                String fechaLanzamiento = track.getJSONObject("album").optString("release_date", "Desconocida");;
                String nombreAlbum = track.getJSONObject("album").getString("title");
                String fotoArtista = track.getJSONObject("artist").getString("picture_medium");

                //Añado un objeto de tipo "Cancion" al array list que usare para el recyclerView
                cancionesPopulares.add(new Cancion(titulo, artista, duracion, foto, fechaLanzamiento, nombreAlbum, fotoArtista));
            }
            //Devuelvo el array
            return cancionesPopulares;
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static ArrayList<Cancion> buscarCancion(String tituloCancion){

        ArrayList<Cancion> listaBusqueda = new ArrayList<>();

        try{
            //Defino la URl de la api, pasandole el titulo de la cancion a buscar como un parametro
            URL url = new URL("https://api.deezer.com/search?q=track:\""+tituloCancion+"\"");

            //Defino la conexión http y le indico que se usara 'GET' para el request (lo normal para una API Rest vaya)
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Defino un reader en el que cargo todo el texto recogido de la llamada a la api
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null){
                //Voy leyendo linea a linea del reader y lo voy cargando el contenido en el stringbuilder
                sb.append(linea);
            }
            reader.close();

            //Construyo un objeto json desde el builder
            JSONObject json = new JSONObject(sb.toString());
            //Recojo el elemento raiz del json
            JSONArray data = json.getJSONArray("data");


            for (int i = 0; i < data.length(); i++) {
                //En cada iteración del bucle accedo a una cancion
                JSONObject track = data.getJSONObject(i);

                //De dicha canción recogo los siguientes datos
                String titulo = track.getString("title");
                String artista = track.getJSONObject("artist").getString("name");
                int duracion = track.getInt("duration");
                String foto = track.getJSONObject("album").getString("cover");
                //Como no todas las canciones tienen definida una fecha de lanzamiento, uso optString, si la tiene, se mostrara, si no, saldrá "Desconocida"
                String fechaLanzamiento = track.getJSONObject("album").optString("release_date", "Desconocida");;
                String nombreAlbum = track.getJSONObject("album").getString("title");
                String fotoArtista = track.getJSONObject("artist").getString("picture_medium");

                //Añado un objeto de tipo "Cancion" al array list que usare para el recyclerView
                listaBusqueda.add(new Cancion(titulo, artista, duracion, foto, fechaLanzamiento, nombreAlbum, fotoArtista));
            }
            //Devuelvo el array
            return listaBusqueda;
        }catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
