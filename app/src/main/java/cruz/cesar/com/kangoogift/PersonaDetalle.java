package cruz.cesar.com.kangoogift;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.listeners.EditPeronaListener;

public class PersonaDetalle extends AppCompatActivity {

    int idPersona;
    private DB_Helper dbHelper;
    private SQLiteDatabase db;

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

        dbHelper = new DB_Helper(this);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

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
                dialog_edit.show();

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

                final Button btnEditPersona = (Button)dialog_edit.findViewById(R.id.btnEditEvento);

                final EditPeronaListener editPeronaListener = new EditPeronaListener(idPersona, PersonaDetalle.this, dialog_edit, editTextNombre, editTextFecha, editTextComentario, dbHelper, db);

                btnEditPersona.setOnClickListener(editPeronaListener);

                //////////////EVENTO CUANDO SE ACEPTA EL CUSTOM DIALOG ////////////////////////////////////
                dialog_edit.setOnDismissListener((new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                        Log.d("onDismiss", "mensaje de OnDismiss de EditarPersona");
                        Intent _intent = new Intent(PersonaDetalle.this, PersonaDetalle.class);
                        _intent.putExtra("nombre", editPeronaListener.get_nombre());
                        _intent.putExtra("id", editPeronaListener.get_id());
                        finish();
                        startActivity(_intent);
                    }
                }));

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
