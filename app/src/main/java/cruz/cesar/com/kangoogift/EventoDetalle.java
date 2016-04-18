package cruz.cesar.com.kangoogift;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.fragments.EventoFragment;
import cruz.cesar.com.kangoogift.listeners.AddEventoListener;
import cruz.cesar.com.kangoogift.listeners.AddPersonaListener;
import cruz.cesar.com.kangoogift.listeners.EditEventoListener;
import cruz.cesar.com.kangoogift.model.Persona;

public class EventoDetalle extends AppCompatActivity implements EventoFragment.OnFragmentInteractionListener{

    Toolbar toolbar;

    DB_Helper dbHelper;
    SQLiteDatabase db;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Persona> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //cambiando cabecera con nombre del evento.
        toolbar.setTitle(getIntent().getStringExtra("nombre"));
        setSupportActionBar(toolbar);

        //BOTON BACK
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /////////////////////////
        //RECYCLERVIEW PERSONAS//
        /////////////////////////

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewPersonas);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        dbHelper = new DB_Helper(this);
        db = dbHelper.getWritableDatabase();

//       Cursor cursor = db_helper.getPersonaDatos(db);
        Cursor cursor = dbHelper.getPersonaWhereEvento_id(db, getIntent().getIntExtra("id", 1));


        if(cursor.moveToFirst()) {
            do {

                Persona persona = new Persona(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );

                arrayList.add(persona);

            } while (cursor.moveToNext());
            cursor.close();

            adapter = new PersonaRecyclerAdapter(arrayList, this);
            recyclerView.setAdapter(adapter);
        }
        else{
            Log.d("Cursor.moveToFirst " , "El cursor estaba vacío" );
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

//    @Override
//    public void onRestart()
//    {
//        super.onRestart();
//        finish();
//        startActivity(getIntent());
//    }

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

            ///////////////////////////
            //BORRAR EVENTO////////////
            ///////////////////////////

            case R.id.delete_evento:

                // 1. Instantiate an AlertDialog.Builder with its constructor
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.confirmarBorrarEvento)
                        .setTitle(R.string.borrar_evento);


                       // Add the buttons
                builder.setPositiveButton(R.string.eliminar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        /////////////////
                        //BORRAR EVENTO//
                        /////////////////

                        int idEvento = getIntent().getIntExtra("id", 1000);
                        String stringId = String.valueOf(idEvento);
                        dbHelper.borrarEvento(stringId, db);

                        //////////////////////////////////////
                        //SE REDIRIGE A ACTIVYITY DE EVENTOS//
                        //////////////////////////////////////

                        dialog.dismiss();
//
                        /////////////////////////
                        //CAMBIANDO EL TOOLBAR///
                        /////////////////////////

//                        toolbar.getMenu().clear();
//                        getMenuInflater().inflate(R.menu.evento_detalle_eventos_toolbar_items, toolbar.getMenu());


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

                //Redireccion a Eventos
                dialog.setOnDismissListener((new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        Log.d("onDismiss", "mensaje de OnDismiss de BorrarEvento");
                        onBackPressed();
                    }
                }));

                break;

            ///////////////////////////
            //EDITAR EVENTO////////////
            ///////////////////////////

            case R.id.edit_evento:


                DB_Helper dbHelperEDIT = new DB_Helper(this);
                SQLiteDatabase dbEDIT = dbHelperEDIT.getWritableDatabase();

                int idEvento = getIntent().getIntExtra("id", 1000);

                Dialog dialogB = new Dialog(EventoDetalle.this);
                dialogB.setTitle(R.string.edit_evento);
                dialogB.setContentView(R.layout.edit_evento_customdialog);

                EditText editTextNombreB = (EditText)dialogB.findViewById(R.id.nombre);
                EditText editTextFechaB = (EditText)dialogB.findViewById(R.id.fecha);
                EditText editTextComentarioB = (EditText)dialogB.findViewById(R.id.comentario);

                editTextFechaB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            DialogFragment fechaDialog = new FechaDialog(v);
                            fechaDialog.show(getSupportFragmentManager(), "DatePicker");
                        }
                    }
                });

                Cursor cursor = dbHelper.getEvenetoDatosWhereId(idEvento, db);

                cursor.moveToFirst();
                do{
                    editTextNombreB.setText(cursor.getString(1));
                    editTextFechaB.setText(cursor.getString(2));
                    editTextComentarioB.setText(cursor.getString(3));
                }while(cursor.moveToNext());
                cursor.close();

                dialogB.show();

                Button btnEditEvento = (Button)dialogB.findViewById(R.id.btnEditEvento);

                final EditEventoListener editEventoListener = new EditEventoListener(EventoDetalle.this, dialogB, editTextNombreB, editTextFechaB, editTextComentarioB, dbHelperEDIT, dbEDIT, idEvento);

                btnEditEvento.setOnClickListener(editEventoListener);

                //////////////EVENTO CUANDO SE ACEPTA EL CUSTOM DIALOG ////////////////////////////////////
                dialogB.setOnDismissListener((new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        Log.d("onDismiss", "mensaje de OnDismiss de EditarEvento");
                        onBackPressed();
//                        Intent _intent = new Intent(EventoDetalle.this, EventoDetalle.class);
//                        _intent.putExtra("nombre", editEventoListener.getResultado());
//                        _intent.putExtra("id", editEventoListener.get_id());
//                        finish();
//                        startActivity(_intent);
                    }
                }));

                return true;

            case R.id.add_evento:

                DB_Helper dbHelperADD = new DB_Helper(this);
                SQLiteDatabase dbADD = dbHelperADD.getWritableDatabase();

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

                AddEventoListener eventoListener = new AddEventoListener(EventoDetalle.this, dialogA, editTextNombre, editTextFecha, editTextComentario, db, dbHelper);

                //manejador de insertar eventos en BD.
                btnAddEvento.setOnClickListener(eventoListener);

                return true;

            case R.id.add_persona:

                Dialog dialogC = new Dialog(EventoDetalle.this);
                dialogC.setTitle(R.string.add_persona);
                dialogC.setContentView(R.layout.add_persona_customdialog_layout);
                dialogC.show();

                EditText editTextNombre_persona = (EditText)dialogC.findViewById(R.id.nombre);
                EditText editTextFecha_persona = (EditText)dialogC.findViewById(R.id.fecha);
                EditText editTextComentario_persona = (EditText)dialogC.findViewById(R.id.comentario);

                //el datepicker para pillar la fecha
                editTextFecha_persona.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus){
                            DialogFragment fechaDialog = new FechaDialog(v);

                            fechaDialog.show(getSupportFragmentManager(), "DatePicker");


                        }
                    }
                });
                int idEventoC = getIntent().getIntExtra("id", 1000);
                Button btnAddPersona = (Button)dialogC.findViewById(R.id.btnAddPersona);
                AddPersonaListener personaListener = new AddPersonaListener(idEventoC, EventoDetalle.this, dialogC, editTextNombre_persona, editTextFecha_persona, editTextComentario_persona, dbHelper, db);

                btnAddPersona.setOnClickListener(personaListener);

                dialogC.setOnDismissListener((new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        Log.d("onDismiss", "mensaje de OnDismiss de EditarEvento");
//                        Intent _intent = new Intent(EventoDetalle.this, EventoDetalle.class);
                        finish();
                        startActivity(getIntent());
                    }
                }));

                break;

            case android.R.id.home:
                onBackPressed();
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
