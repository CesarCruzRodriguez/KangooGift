package cruz.cesar.com.kangoogift.db;

import android.provider.BaseColumns;

/**
 * Created by Cesar on 19/03/2016.
 */
public class RegaloDb {

    public static final String INCREMENTS_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String INT_TYPE = " INTEGER";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";

    public static final String RELACION_PERSONA = "FOREIGN KEY("+
            FeedEntry.COLUMN_NAME_PERSONA_ID +") REFERENCES " +
            PersonaDb.FeedEntry.TABLE_NAME +"("+ PersonaDb.FeedEntry.COLUMN_NAME_ID +")";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID + INCREMENTS_TYPE +COMMA_SEP +
                    FeedEntry.COLUMN_NAME_PERSONA_ID + INT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_NOMBRE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_ESTADO + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_COMENTARIO + TEXT_TYPE + COMMA_SEP +
                    RELACION_PERSONA +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public RegaloDb(){

    }

    public static abstract class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "regalos";
        public static final String COLUMN_NAME_ID = "regalo_id";
        public static final String COLUMN_NAME_PERSONA_ID = "persona_id";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_ESTADO = "estado";
        public static final String COLUMN_NAME_COMENTARIO = "comentario";

    }
}

