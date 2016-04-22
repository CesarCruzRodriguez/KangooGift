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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.fragments.EventoFragment;
import cruz.cesar.com.kangoogift.fragments.InicioFragment;
import cruz.cesar.com.kangoogift.listeners.AddEventoListener;
import cruz.cesar.com.kangoogift.model.Evento;

public class Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        InicioFragment.OnFragmentInteractionListener,
        EventoFragment.OnFragmentInteractionListener{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<Evento> arrayList = new ArrayList<>();

    DB_Helper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        dbHelper = new DB_Helper(this);
        db = dbHelper.getWritableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Eventos");
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewEventos);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Cursor cursor = dbHelper.getEvenetoDatos(db);

        cursor.moveToFirst();
        do{

            Evento evento = new Evento(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );

            arrayList.add(evento);

        }while (cursor.moveToNext());
        cursor.close();

        adapter = new EventoRecyclerAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_evento) {

            Dialog dialog = new Dialog(Principal.this);
            dialog.setTitle(R.string.add_evento);
            dialog.setContentView(R.layout.add_evento_customdialog_layout);
            dialog.show();

            EditText editTextNombre = (EditText)dialog.findViewById(R.id.nombre);
            EditText editTextFecha = (EditText)dialog.findViewById(R.id.fecha);
            EditText editTextComentario = (EditText)dialog.findViewById(R.id.comentario);

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

            Button btnAddEvento = (Button)dialog.findViewById(R.id.btnAddEvento);

//            FragmentManager fragmentManager;
//            fragmentManager = getSupportFragmentManager();
//
//            AddEventoListener eventoListener = new AddEventoListener(R.id.relativeLPrincipal,Principal.this, fragmentManager , dialog, editTextNombre, editTextFecha, editTextComentario, db, dbHelper);
            AddEventoListener eventoListener = new AddEventoListener(this,dialog, editTextNombre, editTextFecha, editTextComentario, db, dbHelper);

            //manejador de insertar eventos en BD.
            btnAddEvento.setOnClickListener(eventoListener);

            //EVETNO DISMISS DEL DIALOG
            dialog.setOnDismissListener((new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    Log.d("onDismiss", "mensaje de OnDismiss");
                    Intent _intent = new Intent(Principal.this, Principal.class);
                    finish();
                    startActivity(_intent);
                }
            }));
//            finish();
//            startActivity(getIntent());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.eventos:

                Intent intent = new Intent(this, Principal.class);
                intent.putExtra("nombre", "Eventos");
                finish();
                startActivity(intent);

//                item.setChecked(true);
//                setFragment(0);
                break;
            case R.id.personas:

                Intent intentPersonsConEventoRegalo = new Intent(this, PersonaConEventoDetalle.class);
                startActivity(intentPersonsConEventoRegalo);

//                item.setChecked(true);
//                setFragment(1);
                break;
            case R.id.regalos:

                break;
            case R.id.lista_resumen:

                break;
            case R.id.informacion:

                break;
            case R.id.contacto:

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(int pos){

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        switch (pos){
            case 0:

//                fragmentManager = getSupportFragmentManager();
//                EventoFragment eventoFragment= new EventoFragment(this);
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.relativeLPrincipal, eventoFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                break;

            case 1:

//                fragmentManager = getSupportFragmentManager();
//                InicioFragment inicioFragment= new InicioFragment();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.relativeLPrincipal, inicioFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
                break;

            default:
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
