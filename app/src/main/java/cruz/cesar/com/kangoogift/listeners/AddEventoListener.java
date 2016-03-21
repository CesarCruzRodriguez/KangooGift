package cruz.cesar.com.kangoogift.listeners;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Cesar on 21/03/2016.
 */
public class AddEventoListener implements View.OnClickListener {

    Dialog view;
    EditText nombre;
    EditText fecha;
    EditText comentario;

    public AddEventoListener(Dialog ctx, EditText nombre, EditText fecha, EditText comentario) {

        this.view = ctx;
        this.nombre = nombre;
        this.fecha = fecha;
        this.comentario = comentario;
    }

    @Override
    public void onClick(View v) {

        //insertar datos en la base de datos...
        Log.d("AddEventoListener","Insertando datos en la base de datos... " + nombre.getText());


    }
}
