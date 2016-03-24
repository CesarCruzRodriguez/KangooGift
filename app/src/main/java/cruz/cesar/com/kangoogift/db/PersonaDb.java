package cruz.cesar.com.kangoogift.db;

import android.provider.BaseColumns;

/**
 * Created by Cesar on 19/03/2016.
 */
public class PersonaDb {

    public static final String INCREMENTS_TYPE = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String INT_TYPE = " INTEGER";
    public static final String TEXT_TYPE = " TEXT";
    public static final String DATE_TYPE = " DATE";
    public static final String COMMA_SEP = ",";

    public static final String RELACION_EVENTO = "FOREIGN KEY("+
                    FeedEntry.COLUMN_NAME_EVENTO_ID +") REFERENCES " +
                    EventoDb.FeedEntry.TABLE_NAME +"("+ EventoDb.FeedEntry.COLUMN_NAME_ID +")";

//    public static final String RELACION_REGALO = "FOREIGN KEY("+
//            FeedEntry.COLUMN_NAME_REGALO_ID +") REFERENCES " +
//            RegaloDb.FeedEntry.TABLE_NAME +"("+ RegaloDb.FeedEntry.COLUMN_NAME_ID +")";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID + INCREMENTS_TYPE +COMMA_SEP +
                    FeedEntry.COLUMN_NAME_EVENTO_ID + INT_TYPE + COMMA_SEP +
//                    FeedEntry.COLUMN_NAME_REGALO_ID + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_NOMBRE + TEXT_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_FECHA + DATE_TYPE + COMMA_SEP +
                    FeedEntry.COLUMN_NAME_COMENTARIO + TEXT_TYPE +
                    COMMA_SEP + RELACION_EVENTO +
//                    COMMA_SEP + RELACION_REGALO +
                    " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public PersonaDb(){

    }

    public static abstract class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "personas";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_EVENTO_ID = "evento_id";
//        public static final String COLUMN_NAME_REGALO_ID = "regalo_id";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_FECHA = "fecha";
        public static final String COLUMN_NAME_COMENTARIO = "comentario";

    }
}
