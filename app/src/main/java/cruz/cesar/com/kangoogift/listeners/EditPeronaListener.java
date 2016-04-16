package cruz.cesar.com.kangoogift.listeners;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;

import cruz.cesar.com.kangoogift.db.DB_Helper;

/**
 * Created by Cesar on 16/04/2016.
 */
public class EditPeronaListener implements View.OnClickListener {

    Context ctx;
    Dialog dialog;
    EditText etNombre;
    EditText etFecha;
    EditText etComentario;
    SQLiteDatabase db;
    DB_Helper db_helper;
    int id;

    private String _nombre;
    private int _id;

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public EditPeronaListener( int id, Context ctx, Dialog dialog, EditText etNombre, EditText etFecha, EditText etComentario, DB_Helper db_helper, SQLiteDatabase db) {
        this.etNombre = etNombre;
        this.etFecha = etFecha;
        this.etComentario = etComentario;
        this.db = db;
        this.db_helper = db_helper;
        this.id = id;
        this.dialog = dialog;
        this.ctx = ctx;
    }

    @Override
    public void onClick(View v) {

        db_helper.actualizarPersona(id, etNombre.getText().toString(),
                                         etFecha.getText().toString(),
                                         etComentario.getText().toString(), db);

        set_nombre(etNombre.getText().toString());
        set_id(id);
        dialog.dismiss();
    }
}
