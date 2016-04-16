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

        insertarPersona(1, "Pepe", "22-12-2016", "mi tio", db);
        insertarPersona(1, "Juana", "21-12-2016", "mi tia", db);

        insertarRegalo(2, "Plancha", "comprado", "marca Techk", db);
        insertarRegalo(2, "Taladro", "comprado", "marca Techk", db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(EventoDb.SQL_DELETE_ENTRIES);
        db.execSQL(PersonaDb.SQL_DELETE_ENTRIES);
        db.execSQL(RegaloDb.SQL_DELETE_ENTRIES);

        onCreate(db);

    }

    //////////////////////
    //MÉTODOS DE EVENTOS/////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////

    public void insertarEventoCumpleaños(String nombre, String comentario, SQLiteDatabase db){

            ContentValues values = new ContentValues();
            values.put(EventoDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
            long l =  db.insert(EventoDb.FeedEntry.TABLE_NAME, null, values);

            Log.d("Database operaciion","Una fila insertada...");
    }

    public void insertarEvento(String nombre, String fecha, String comentario, SQLiteDatabase db){

            ContentValues values = new ContentValues();
            values.put(EventoDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_FECHA, fecha);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
            long l =  db.insert(EventoDb.FeedEntry.TABLE_NAME, null, values);

            Log.d("Database operaciion", "Una fila insertada... de eventos");

    }

    public void actualizarEvento(int id, String nombre, String fecha, String comentario, SQLiteDatabase db){

        String selection = EventoDb.FeedEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

            ContentValues values = new ContentValues();
            values.put(EventoDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_FECHA, fecha);
            values.put(EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
            long l =  db.update(EventoDb.FeedEntry.TABLE_NAME, values, selection, selectionArgs);

            Log.d("Database operaciion", "Una fila actualizada... de eventos");

    }

    ////////////////
    //BORRAR////////
    ////////////////

    public void borrarEvento(String id, SQLiteDatabase db){

        String selection = EventoDb.FeedEntry.COLUMN_NAME_ID + " LIKE ?";

        String[] valor = { String.valueOf(id) };
        db.delete(EventoDb.FeedEntry.TABLE_NAME, selection, valor);
        Log.d("Database operaciion", "Una fila borrada... de eventos");
    }

    public Cursor getEvenetoDatos(SQLiteDatabase db){

        String[] projection = {EventoDb.FeedEntry.COLUMN_NAME_ID,
                                EventoDb.FeedEntry.COLUMN_NAME_NOMBRE,
                                EventoDb.FeedEntry.COLUMN_NAME_FECHA,
                                EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO};

        Cursor cursor = db.query(EventoDb.FeedEntry.TABLE_NAME, projection, null,null, null, null, null);

        return cursor;
    }

    public Cursor getEvenetoDatosWhereId(int evento_id, SQLiteDatabase db){

        String selection = EventoDb.FeedEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(evento_id) };

        String[] projection = {EventoDb.FeedEntry.COLUMN_NAME_ID,
                EventoDb.FeedEntry.COLUMN_NAME_NOMBRE,
                EventoDb.FeedEntry.COLUMN_NAME_FECHA,
                EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO};

        Cursor cursor = db.query(EventoDb.FeedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        return cursor;
    }

    //////////////////////
    //METODOS PERSONAS///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////

    public void insertarPersona(int evento_id, String nombre,  String fecha, String comentario, SQLiteDatabase db){
        //TIPO INT EN BASE DE DATOS.................................................................
//        int numeroEvento_id = Integer.parseInt(evento_id);

            ContentValues values = new ContentValues();
            values.put(PersonaDb.FeedEntry.COLUMN_NAME_EVENTO_ID, evento_id);
            values.put(PersonaDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
            values.put(PersonaDb.FeedEntry.COLUMN_NAME_FECHA, fecha);
            values.put(PersonaDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
            long l =  db.insert(PersonaDb.FeedEntry.TABLE_NAME, null, values);

            Log.d("Database operaciion", "Una fila insertada... en personas");
    }

    public Cursor getPersonaWhereEvento_id(SQLiteDatabase db, int evento_id){

        String ev_id = String.valueOf(evento_id);

        Cursor cursor = db.rawQuery("SELECT * FROM personas WHERE evento_id like " + ev_id, null);

        return cursor;
    }

    public void borrarPersona(String id, SQLiteDatabase db){

        String selection = PersonaDb.FeedEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] valor = { String.valueOf(id) };

        db.delete(PersonaDb.FeedEntry.TABLE_NAME, selection, valor);
        Log.d("Database operaciion", "Una fila borrada... de personas");
    }

    public void actualizarPersona(int id, String nombre, String fecha, String comentario, SQLiteDatabase db){

        String selection = PersonaDb.FeedEntry.COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };

        ContentValues values = new ContentValues();
        values.put(PersonaDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
        values.put(PersonaDb.FeedEntry.COLUMN_NAME_FECHA, fecha);
        values.put(PersonaDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
        long l =  db.update(PersonaDb.FeedEntry.TABLE_NAME, values, selection, selectionArgs);

        Log.d("Database operaciion", "Una fila actualizada... de personas " + id);

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

    //////////////////////
    //METODOS REGALOS ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////

    public void insertarRegalo(int persona_id ,String nombre, String estado, String comentario, SQLiteDatabase db){

        ContentValues values = new ContentValues();
        values.put(RegaloDb.FeedEntry.COLUMN_NAME_PERSONA_ID, persona_id);
        values.put(RegaloDb.FeedEntry.COLUMN_NAME_NOMBRE, nombre);
        values.put(RegaloDb.FeedEntry.COLUMN_NAME_ESTADO, estado);
        values.put(RegaloDb.FeedEntry.COLUMN_NAME_COMENTARIO, comentario);
        long l =  db.insert(RegaloDb.FeedEntry.TABLE_NAME, null, values);

        Log.d("Database operaciion", "Una fila insertada... de regalos");

    }

    public Cursor getRegaloWherePersona_id(SQLiteDatabase db, int persona_id){

        String selection = RegaloDb.FeedEntry.COLUMN_NAME_PERSONA_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(persona_id) };

        String[] projection = {RegaloDb.FeedEntry.COLUMN_NAME_ID,
                RegaloDb.FeedEntry.COLUMN_NAME_PERSONA_ID,
                RegaloDb.FeedEntry.COLUMN_NAME_NOMBRE,
                RegaloDb.FeedEntry.COLUMN_NAME_ESTADO,
                RegaloDb.FeedEntry.COLUMN_NAME_COMENTARIO};

//        String ps_id = String.valueOf(persona_id);
//        Cursor cursor = db.rawQuery("SELECT * FROM regalos WHERE persona_id like " + ps_id, null);
        Cursor cursor = db.query(RegaloDb.FeedEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Log.d("Database operaciion", "getRegaloWherePersona... de regalos " + persona_id);

        return cursor;
    }
}
