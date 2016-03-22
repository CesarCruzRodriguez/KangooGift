package cruz.cesar.com.kangoogift;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.fragments.EventoFragment;

public class EventoDetalle extends AppCompatActivity implements EventoFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //cambiando cabecera con nombre del evento.
        toolbar.setTitle(getIntent().getStringExtra("nombre"));
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.evento_detalle_toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id){
            case R.id.delete_evento:

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.confirmarBorrarEvento)
                        .setTitle(R.string.borrar_evento);


                       // Add the buttons
                builder.setPositiveButton(R.string.eliminar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        /////////////////
                        //BORRAR EVENTO//
                        /////////////////

                        DB_Helper dbHelper;
                        SQLiteDatabase db;
                        dbHelper = new DB_Helper(EventoDetalle.this);
                        db = dbHelper.getWritableDatabase();
                        int idEvento = getIntent().getIntExtra("id", 1000);
                        String stringId = String.valueOf(idEvento);
                        dbHelper.borrarEvento(stringId , db);

                        //////////////////////////////////////
                        //SE REDIRIGE AL FRAGMENT DE EVENTOS//
                        //////////////////////////////////////

                        FragmentTransaction fragmentTransaction;
                        android.support.v4.app.FragmentManager fragmentManager;
                        fragmentManager = getSupportFragmentManager();
                        EventoFragment eventoFragment= new EventoFragment(EventoDetalle.this);
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.relativeLEventoDetalle, eventoFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();


                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                // Set other dialog properties

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
