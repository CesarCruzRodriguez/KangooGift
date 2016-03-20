package cruz.cesar.com.kangoogift.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cesar on 19/03/2016.
 */
public class DB_Helper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "KabgooGift.sqlite";

    public DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(EventoDb.SQL_CREATE_ENTRIES);
        db.execSQL(PersonaDb.SQL_CREATE_ENTRIES);
        db.execSQL(RegaloDb.SQL_CREATE_ENTRIES);

        eventoCumpleaños(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(EventoDb.SQL_DELETE_ENTRIES);
        db.execSQL(PersonaDb.SQL_DELETE_ENTRIES);
        db.execSQL(RegaloDb.SQL_DELETE_ENTRIES);

        onCreate(db);

    }

    public void eventoCumpleaños(SQLiteDatabase db){

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();

            values.put(EventoDb.FeedEntry.COLUMN_NAME_NOMBRE, "Cumpleaños");
            values.put(EventoDb.FeedEntry.COLUMN_NAME_COMENTARIO, "2016");
            db.insert(EventoDb.FeedEntry.TABLE_NAME, null, values);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
