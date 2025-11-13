package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ControladorCancionesFavoritas {

    static ArrayList<Cancion> listaCanciones = new ArrayList<>();

    public void cargarFavoritas(Context context){
        SharedPreferences prefs = context.getSharedPreferences("canciones-favoritas", Context.MODE_PRIVATE);
        String json = prefs.getString("listado-canciones-favoritas", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Cancion>>() {}.getType();

        if (json != null) {
            listaCanciones.addAll(gson.fromJson(json, type));
        }
    }

    public static ArrayList<Cancion> obtenerListadoFavoritas(){
        return listaCanciones;
    }

    public static boolean guardarCancion(Context context, Cancion cancion){

        if(!listaCanciones.contains(cancion)){
            listaCanciones.add(cancion);
            guardarFavoritos(context, listaCanciones);
            return true;
        }else{
            return false;
        }
    }

    public static void guardarFavoritos(Context context, List<Cancion> lista) {
            SharedPreferences prefs = context.getSharedPreferences("canciones-favoritas", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            Gson gson = new Gson();
            String json = gson.toJson(lista);
            editor.putString("listado-canciones-favoritas", json);
            editor.apply();
    }



}
