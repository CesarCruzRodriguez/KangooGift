package cruz.cesar.com.kangoogift;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.listeners.EditPeronaListener;
import cruz.cesar.com.kangoogift.model.Regalo;

public class PersonaDetalle extends AppCompatActivity {

    int idPersona;
    private DB_Helper dbHelper;
    private SQLiteDatabase db;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Regalo> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(getIntent().getStringExtra("nombre"));
        setSupportActionBar(toolbar);

        //BOTON BACK
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //RECYCLERVIEW
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewRegalos);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        dbHelper = new DB_Helper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = dbHelper.getRegaloWherePersona_id(db, getIntent().getIntExtra("id", 1));

        if(cursor.moveToFirst()){
            do {
                Regalo regalo = new Regalo(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                Log.d("Cursor Regalo", " regalo nombre: " + cursor.getString(2));
                arrayList.add(regalo);

            }while (cursor.moveToNext());
            cursor.close();

            adapter = new RegaloRecyclerAdapter(arrayList, this);
            recyclerView.setAdapter(adapter);
        }
        else{
            Log.d("Cursor.moveToFirst ", "El cursor estaba vacío");
        }
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
        getMenuInflater().inflate(R.menu.persona_detalle_toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id){

            ///////////////////////////
            //BORRAR PERSONA //////////
            ///////////////////////////

            case R.id.delete_persona:

                idPersona = getIntent().getIntExtra("id", 1000);

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.confirmarBorrarPersona)
                        .setTitle(R.string.borrar_persona);


                // Add the buttons
                builder.setPositiveButton(R.string.eliminar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String stringId = String.valueOf(idPersona);
                        dbHelper.borrarPersona(stringId, db);

                        dialog.dismiss();
                    }});
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

                        Log.d("onDismiss", "mensaje de OnDismiss de delete_persona");
                        onBackPressed();
                    }
                }));


                break;

            ///////////////////////////
            //EDITAR PERSONA //////////
            ///////////////////////////

            case R.id.edit_persona:

                idPersona = getIntent().getIntExtra("id", 1000);
                Dialog dialog_edit = new Dialog(PersonaDetalle.this);
                dialog_edit.setTitle(R.string.edit_persona);
                dialog_edit.setContentView(R.layout.edit_evento_customdialog);


                EditText editTextNombre = (EditText)dialog_edit.findViewById(R.id.nombre);
                EditText editTextFecha = (EditText)dialog_edit.findViewById(R.id.fecha);
                EditText editTextComentario = (EditText)dialog_edit.findViewById(R.id.comentario);

                editTextFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            DialogFragment fechaDialog = new FechaDialog(v);
                            fechaDialog.show(getSupportFragmentManager(), "DatePicker");
                        }
                    }
                });

                Cursor cursor_edit_persona = dbHelper.getPersonaDatosWhereId(idPersona, db);
                cursor_edit_persona.moveToFirst();
                do{
                    editTextNombre.setText(cursor_edit_persona.getString(2));
                    editTextFecha.setText(cursor_edit_persona.getString(3));
                    editTextComentario.setText(cursor_edit_persona.getString(4));

                }while(cursor_edit_persona.moveToNext());
                cursor_edit_persona.close();

                dialog_edit.show();

                final Button btnEditPersona = (Button)dialog_edit.findViewById(R.id.btnEditEvento);

                final EditPeronaListener editPeronaListener = new EditPeronaListener(idPersona, PersonaDetalle.this, dialog_edit, editTextNombre, editTextFecha, editTextComentario, dbHelper, db);

                btnEditPersona.setOnClickListener(editPeronaListener);

                //////////////EVENTO CUANDO SE ACEPTA EL CUSTOM DIALOG ////////////////////////////////////
                dialog_edit.setOnDismissListener((new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        Log.d("onDismiss", "mensaje de OnDismiss de EditarPersona");
                        onBackPressed();
//                        Intent _intent = new Intent(PersonaDetalle.this, PersonaDetalle.class);
//                        _intent.putExtra("nombre", editPeronaListener.get_nombre());
//                        _intent.putExtra("id", editPeronaListener.get_id());
//                        finish();
//                        startActivity(_intent);
                    }
                }));

                break;

            case R.id.add_regalo:

                final DB_Helper db_helper_add_regalo = new DB_Helper(this);
                final SQLiteDatabase db_add_regalo = db_helper_add_regalo.getWritableDatabase();

                final int idPersona_add_regalo = getIntent().getIntExtra("id", 1000);

                final Dialog dialogo_add_regalo = new Dialog(this);
                dialogo_add_regalo.setTitle(R.string.add_regalo);
                dialogo_add_regalo.setContentView(R.layout.add_regalo_customdialog_layout);

                final EditText editTextNombre_add_regalo = (EditText)dialogo_add_regalo.findViewById(R.id.nombre);
                final CheckBox check_estado_add_regalo = (CheckBox)dialogo_add_regalo.findViewById(R.id.estado);
                final EditText editTextComentario_add_regalo = (EditText)dialogo_add_regalo.findViewById(R.id.comentario);

                dialogo_add_regalo.show();

                Button btn_add_regalo = (Button)dialogo_add_regalo.findViewById(R.id.btnAddRegalo);
                btn_add_regalo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nombre =  editTextNombre_add_regalo.getText().toString();

                        String estado = "";
                        if(check_estado_add_regalo.isChecked()) estado ="comprado";
                        else estado = "idea";

                        String comentario = editTextComentario_add_regalo.getText().toString();

                        db_helper_add_regalo.insertarRegalo(idPersona_add_regalo, nombre, estado, comentario, db_add_regalo);

                        dialogo_add_regalo.dismiss();
                    }
                });

                dialogo_add_regalo.setOnDismissListener(new DialogInterface.OnDismissListener(){

                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        PersonaDetalle.this.onBackPressed();
                    }
                });


                break;

            ///////////////////////////
            //BACK PERSONA ////////////
            ///////////////////////////
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
