package cruz.cesar.com.kangoogift.listeners;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;

import cruz.cesar.com.kangoogift.EventoDetalle;
import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.fragments.EventoFragment;

/**
 * Created by Cesar on 26/03/2016.
 */
public class EditEventoListener implements View.OnClickListener {

    int plantilla;
    Context ctx;
    FragmentManager fragmentManager;
    Dialog dialog;
    EditText etNombre;
    EditText etFecha;
    EditText etComentario;
    SQLiteDatabase db;
    DB_Helper db_helper;
    int id;

    public EditEventoListener(int plantilla, Context ctx, FragmentManager fragmentManager, Dialog dialog,EditText etNombre, EditText etFecha, EditText etComentario, SQLiteDatabase db, DB_Helper db_helper, int id) {

        this.plantilla = plantilla;
        this.ctx = ctx;
        this.fragmentManager = fragmentManager;
        this.dialog = dialog;
        this.etNombre = etNombre;
        this.etFecha = etFecha;
        this.etComentario = etComentario;
        this.db = db;
        this.db_helper = db_helper;
        this.id = id;


    }

    @Override
    public void onClick(View v) {

        db_helper.actualizarEvento(id, etNombre.getText().toString(),
                                        etFecha.getText().toString(),
                                        etComentario.getText().toString(), db);

        dialog.dismiss();

        Intent intent = new Intent(this.ctx, EventoDetalle.class);
        intent.putExtra("id", id);
        intent.putExtra("nombre",  etNombre.getText().toString());
        intent.putExtra("fecha",  etFecha.getText().toString());
        intent.putExtra("comentario", etComentario.getText().toString());
        this.ctx.startActivity(intent);

//        FragmentTransaction fragmentTransaction;
//
//        EventoFragment eventoFragment= new EventoFragment(ctx);
//        fragmentTransaction = this.fragmentManager.beginTransaction();
//        fragmentTransaction.add(plantilla, eventoFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

    }
}