package cruz.cesar.com.kangoogift.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Cesar on 19/03/2016.
 */
public class DB_Helper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "KangooGift.sqlite";

    public DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(EventoDb.SQL_CREATE_ENTRIES);
        db.execSQL(PersonaDb.SQL_CREATE_ENTRIES);
        db.execSQL(RegaloDb.SQL_CREATE_ENTRIES);

        //BASES DE DATOS DE PRUEBA.......................................................

        insertarEventoCumpleaños("Cumpleaños", "... comentario ...", db);
        insertarEvento("Navidad", "25-12-2016", "Este año es en casa de mi tía.", db);
//        insertarEventoCumpleaños("Navidad", "Este año es en la casa de mi Tia ...", db);

        insertarPersona("1", "Pepe", "22-12-2016", "mi tio", db);
        insertarPersona("1", "Juana", "21-12-2016", "mi tia", db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(EventoDb.SQL_DELETE_ENTRIES);
        db.execSQL(PersonaDb.SQL_DELETE_ENTRIES);
        db.execSQL(RegaloDb.SQL_DELETE_ENTRIES);

        onCreate(db);

    }

    //////////////////////
    //MÉTODOS DE EVENTOS//
    /////////////////////

    public void insertarEventoCumpleaños(String nombre, String comentario, SQLiteDatabase db){

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(EventoDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
            long l =  db.insert(EventoDb.FeedEntry.TABLE_NAME, null, values);

            Log.d("Database operaciion","Una fila insertada...");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }

    public void insertarEvento(String nombre, String fecha, String comentario, SQLiteDatabase db){

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(EventoDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_FECHA, fecha);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
            long l =  db.insert(EventoDb.FeedEntry.TABLE_NAME, null, values);

            Log.d("Database operaciion", "Una fila insertada...");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }


    }

    public void actualizarEvento(int id, String nombre, String fecha, String comentario, SQLiteDatabase db){

        db.beginTransaction();

        String selection = EventoDb.FeedEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

            ContentValues values = new ContentValues();
            values.put(EventoDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_FECHA, fecha);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
            long l =  db.update(EventoDb.FeedEntry.TABLE_NAME, values, selection, selectionArgs);

            Log.d("Database operaciion", "Una fila actualizada...");

    }

    ////////////////
    //BORRAR////////
    ////////////////

    public void borrarEvento(String id, SQLiteDatabase db){

        String selection = EventoDb.FeedEntry.COLUMN_NAME_ID + " LIKE ?";

        String[] valor = { String.valueOf(id) };

        try{
            db.delete(EventoDb.FeedEntry.TABLE_NAME, selection, valor);
            Log.d("Database operaciion", "Una fila borrada...");
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }


    }

    public Cursor getEvenetoDatos(SQLiteDatabase db){

        String[] projection = {EventoDb.FeedEntry.COLUMN_NAME_ID,
                                EventoDb.FeedEntry.COLUMN_NAME_NOMBRE,
                                EventoDb.FeedEntry.COLUMN_NAME_FECHA,
                                EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO};

        Cursor cursor = db.query(EventoDb.FeedEntry.TABLE_NAME, projection, null,null, null, null, null);

        return cursor;
    }

    //////////////////////
    //METODOS PERSONAS////
    //////////////////////

    public void insertarPersona(String evento_id, String nombre,  String fecha, String comentario, SQLiteDatabase db){

        db.beginTransaction();

        //TIPO INT EN BASE DE DATOS.................................................................
        int numeroEvento_id = Integer.parseInt(evento_id);

        try {
            ContentValues values = new ContentValues();
            values.put(PersonaDb.FeedEntry.COLUMN_NAME_EVENTO_ID, numeroEvento_id);
            values.put(PersonaDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(PersonaDb.FeedEntry.COLUMN_NAME_FECHA, fecha);
            values.put(PersonaDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
            long l =  db.insert(PersonaDb.FeedEntry.TABLE_NAME, null, values);

            Log.d("Database operaciion", "Una fila insertada...");

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public Cursor getPersonaWhereEvento_id(SQLiteDatabase db, int evento_id){

        String ev_id = String.valueOf(evento_id);

        Cursor cursor = db.rawQuery("SELECT * FROM personas WHERE evento_id like " + ev_id , null);

        return cursor;
    }

    public Cursor getPersonaDatos(SQLiteDatabase db){

        String[] projection = {PersonaDb.FeedEntry.COLUMN_NAME_ID,
                PersonaDb.FeedEntry.COLUMN_NAME_EVENTO_ID,
                PersonaDb.FeedEntry.COLUMN_NAME_NOMBRE,
                PersonaDb.FeedEntry.COLUMN_NAME_FECHA,
                PersonaDb.FeedEntry.COLUMN_NAME_COMENTARIO};

        Cursor cursor = db.query(PersonaDb.FeedEntry.TABLE_NAME, projection, null,null, null, null, null);

        return cursor;
    }

}
