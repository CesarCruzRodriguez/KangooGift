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

        insertarEventoCumpleaños("Cumpleaños", "... comentario ...", db);
        insertarEventoCumpleaños("Navidad", "Este año es en la casa de mi Tia ...", db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(EventoDb.SQL_DELETE_ENTRIES);
        db.execSQL(PersonaDb.SQL_DELETE_ENTRIES);
        db.execSQL(RegaloDb.SQL_DELETE_ENTRIES);

        onCreate(db);

    }

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

    public Cursor getEvenetoDatos(SQLiteDatabase db){

        String[] projection = {EventoDb.FeedEntry.COLUMN_NAME_ID,
                                EventoDb.FeedEntry.COLUMN_NAME_NOMBRE,
                                EventoDb.FeedEntry.COLUMN_NAME_FECHA,
                                EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO};

        Cursor cursor = db.query(EventoDb.FeedEntry.TABLE_NAME, projection, null,null, null, null, null);

        return cursor;
    }

}
