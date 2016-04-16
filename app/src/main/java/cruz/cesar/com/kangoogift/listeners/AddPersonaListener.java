package cruz.cesar.com.kangoogift.listeners;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cruz.cesar.com.kangoogift.db.DB_Helper;

/**
 * Created by Cesar on 16/04/2016.
 */
public class AddPersonaListener implements View.OnClickListener {

    int id;
    Context ctx;
    Dialog dialog;
    EditText etNombre;
    EditText etFecha;
    EditText etComentario;
    SQLiteDatabase db;
    DB_Helper db_helper;

    public AddPersonaListener(int id,Context ctx,  Dialog dialog, EditText etNombre, EditText etFecha, EditText etComentario,  DB_Helper db_helper, SQLiteDatabase db) {
        this.id = id;
        this.ctx = ctx;
        this.db = db;
        this.db_helper = db_helper;
        this.dialog = dialog;
        this.etComentario = etComentario;
        this.etFecha = etFecha;
        this.etNombre = etNombre;
    }

    @Override
    public void onClick(View v) {


        db_helper.insertarPersona(id, etNombre.getText().toString(), etFecha.getText().toString(), etComentario.getText().toString(), db);
        dialog.dismiss();
        Log.d("AddPersonaListener", "Insertando datos en la base de datos... " + etNombre.getText());
    }
}
