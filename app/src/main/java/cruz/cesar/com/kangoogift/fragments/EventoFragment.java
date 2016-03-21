package cruz.cesar.com.kangoogift.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cruz.cesar.com.kangoogift.EventoRecyclerAdapter;
import cruz.cesar.com.kangoogift.R;
import cruz.cesar.com.kangoogift.db.DB_Helper;
import cruz.cesar.com.kangoogift.model.Evento;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class EventoFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Evento> arrayList = new ArrayList<>();
    Context ctx;

    private OnFragmentInteractionListener mListener;

    public EventoFragment(){

    }
    public EventoFragment(Context ctx) {

        this.ctx = ctx;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_evento, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Eventos");

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewEventos);
        layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DB_Helper db_helper = new DB_Helper(ctx);
        SQLiteDatabase db = db_helper.getReadableDatabase();

        Cursor cursor = db_helper.getEvenetoDatos(db);

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
                db_helper.close();

        adapter = new EventoRecyclerAdapter(arrayList, ctx);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
