package cruz.cesar.com.kangoogift;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.model.Evento;
import cruz.cesar.com.kangoogift.model.Persona;
import cruz.cesar.com.kangoogift.model.Regalo;

public class Lista extends AppCompatActivity {

    DB_Helper dbHelper;
    SQLiteDatabase db;

    String contenido = "";

    ArrayList<Evento> eventos = new ArrayList<>();
    ArrayList<Persona> personas = new ArrayList<>();
    ArrayList<Regalo> regalos = new ArrayList<>();

    Evento evento;
    Persona persona;
    Regalo regalo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        //BOTON BACK
        getSupportActionBar().setTitle("Listado");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView txt_list = (TextView)findViewById(R.id.textview_list);

        dbHelper = new DB_Helper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor_eventos = dbHelper.getAllEventos(db);
        Cursor cursor_personas = dbHelper.getAllPersonas(db);
        Cursor cursor_regalos = dbHelper.getAllRegalos(db);

        if (cursor_eventos.moveToFirst())
            do {
                evento = new Evento(
                        cursor_eventos.getInt(0),         //id
                        cursor_eventos.getString(1),      //nombre
                        cursor_eventos.getString(1),      //fecha
                        cursor_eventos.getString(1)       //comentario
                );
                eventos.add(evento);
            }while (cursor_eventos.moveToNext());

        if (cursor_personas.moveToFirst())
            do {
                persona = new Persona(
                        cursor_personas.getInt(0),         //id
                        cursor_personas.getInt(1),         //id_evento
                        cursor_personas.getString(2),      //nombre
                        cursor_personas.getString(3),      //fecha
                        cursor_personas.getString(4)       //comentario
                );
                personas.add(persona);
            }while (cursor_personas.moveToNext());

        if (cursor_regalos.moveToFirst())
            do {
                regalo = new Regalo(
                        cursor_regalos.getInt(0),         //id
                        cursor_regalos.getInt(1),         //id_persona
                        cursor_regalos.getString(2),      //nombre
                        cursor_regalos.getString(3),      //estado
                        cursor_regalos.getString(4)       //comentario
                );
                regalos.add(regalo);
            }while (cursor_regalos.moveToNext());

        String comprado = "";
        for ( int i = 0; i < eventos.size(); i++){
            contenido += "\r\n";
             contenido  += "- " + eventos.get(i).getNombre() + ":\r\n";
            for (int j = 0; j < personas.size(); j++){
                if(personas.get(j).getEvento_id() == eventos.get(i).getId()){
                    contenido += "   -> " + personas.get(j).getNombre() + "\r\n";
                        for (int k = 0; k < regalos.size(); k++){
                            if(regalos.get(k).getPersona_id() == personas.get(j).getId()){
                                if(regalos.get(k).getEstado().equals("comprado")){
                                    comprado = "(comprado)";
                                }
                                else comprado = "(idea)";
                                contenido += "        · " + regalos.get(k).getNombre() + " "+ comprado + "\r\n";
                            }
                        }
                }
            }
        }
        txt_list.setText(contenido);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
