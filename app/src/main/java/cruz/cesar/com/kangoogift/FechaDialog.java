package cruz.cesar.com.kangoogift;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Cesar on 21/03/2016.
 */
public class FechaDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txtFecha;

    public FechaDialog(){

    }

    public FechaDialog(View view) {
        txtFecha = (EditText)view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        String fecha = day+"-"+month+"-"+year;
        txtFecha.setText(fecha);

    }
}
