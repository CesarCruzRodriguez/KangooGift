package cruz.cesar.com.kangoogift;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.fragments.EventoFragment;
import cruz.cesar.com.kangoogift.listeners.AddEventoListener;

public class EventoDetalle extends AppCompatActivity implements EventoFragment.OnFragmentInteractionListener{

    Toolbar toolbar;
    DB_Helper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

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


                        /////////////////////////
                        //CAMBIANDO EL TOOLBAR///
                        /////////////////////////

                        toolbar.getMenu().clear();
                        getMenuInflater().inflate(R.menu.evento_detalle_eventos_toolbar_items, toolbar.getMenu());


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

            case R.id.add_evento:

                Dialog dialogA = new Dialog(EventoDetalle.this);
                dialogA.setTitle(R.string.add_evento);
                dialogA.setContentView(R.layout.add_evento_customdialog_layout);
                dialogA.show();

                EditText editTextNombre = (EditText)dialogA.findViewById(R.id.nombre);
                EditText editTextFecha = (EditText)dialogA.findViewById(R.id.fecha);
                EditText editTextComentario = (EditText)dialogA.findViewById(R.id.comentario);

                //el datepicker para pillar la fecha
                editTextFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus){
                            DialogFragment fechaDialog = new FechaDialog(v);

                            fechaDialog.show(getSupportFragmentManager(), "DatePicker");


                        }
                    }
                });

                Button btnAddEvento = (Button)dialogA.findViewById(R.id.btnAddEvento);

                FragmentManager fragmentManager;
                fragmentManager = getSupportFragmentManager();

                AddEventoListener eventoListener = new AddEventoListener(R.id.relativeLEventoDetalle, EventoDetalle.this, fragmentManager , dialogA, editTextNombre, editTextFecha, editTextComentario, db, dbHelper);



                //manejador de insertar eventos en BD.
                btnAddEvento.setOnClickListener(eventoListener);

                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
