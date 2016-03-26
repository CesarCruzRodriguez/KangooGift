package cruz.cesar.com.kangoogift.listeners;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cruz.cesar.com.kangoogift.R;
import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.fragments.EventoFragment;

/**
 * Created by Cesar on 21/03/2016.
 */
public class AddEventoListener implements View.OnClickListener {

    FragmentManager fragmentManager;

    int plantilla;
    Context ctx;
    Dialog dialog;
    EditText etNombre;
    EditText etFecha;
    EditText etComentario;
    SQLiteDatabase db;
    DB_Helper db_helper;

    public AddEventoListener(int plantilla, Context ctx ,FragmentManager fragmentManager , Dialog dialog, EditText etNombre, EditText etFecha, EditText etComentario, SQLiteDatabase db, DB_Helper db_helper) {

        this.plantilla = plantilla;
        this.ctx =ctx;
        this.fragmentManager = fragmentManager;
        this.dialog = dialog;
        this.etNombre = etNombre;
        this.etFecha = etFecha;
        this.etComentario = etComentario;
        this.db = db;
        this.db_helper = db_helper;
    }

    @Override
    public void onClick(View v) {

        db_helper.insertarEvento(etNombre.getText().toString(), etFecha.getText().toString(), etComentario.getText().toString(), db);

        dialog.dismiss();

        FragmentTransaction fragmentTransaction;


        EventoFragment eventoFragment= new EventoFragment(ctx);
        fragmentTransaction = this.fragmentManager.beginTransaction();
        fragmentTransaction.add(plantilla, eventoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.d("AddEventoListener","Insertando datos en la base de datos... " + etNombre.getText());


    }
}
