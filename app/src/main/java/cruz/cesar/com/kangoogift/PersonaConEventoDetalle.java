package cruz.cesar.com.kangoogift;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.model.PersonaConRegalo;

public class PersonaConEventoDetalle extends AppCompatActivity {

    DB_Helper dbHelper;
    SQLiteDatabase db;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<PersonaConRegalo> arrayList = new ArrayList<>();

    PersonaConRegalo persona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona_con_evento_detalle);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //cambiando cabecera con nombre del evento.
//        setSupportActionBar(toolbar);

        //BOTON BACK
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPersonasConEventoDetalle);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        dbHelper = new DB_Helper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = dbHelper.getAllPersonas(db);

        int num_regalo = 2;

        if (cursor.moveToFirst())
            do {

                persona = new PersonaConRegalo(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        num_regalo
                );

                arrayList.add(persona);

            } while (cursor.moveToNext());
            cursor.close();


        for (int indice = 0; indice < arrayList.size(); indice++){

            arrayList.get(indice).setNum_regalos(Integer.parseInt(String.valueOf(DatabaseUtils.queryNumEntries(db, "regalos", "persona_id=?", new String[]{String.valueOf(arrayList.get(indice).getId())}))));

        }

            adapter = new PersonaConEventoRecyclerAdapter(arrayList, this);
            recyclerView.setAdapter(adapter);

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

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
