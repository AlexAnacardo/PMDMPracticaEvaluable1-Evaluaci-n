package es.ies.claudiomoyano.dam2.pmdm.practicaevaluable1evaluacion_asensio_sanchez_alex;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private static DatabaseClient instancia;
    private final AppDatabase db;

    private DatabaseClient(Context context){
        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "gestionMusical.db").build();
    }

    public static synchronized DatabaseClient getInstance(Context context){
        if(instancia==null){
            instancia = new DatabaseClient(context);
        }
        return instancia;
    }

    public AppDatabase getDb(){
        return db;
    }
}
