package cruz.cesar.com.kangoogift;


import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cruz.cesar.com.kangoogift.model.Evento;

/**
 * Created by Cesar on 20/03/2016.
 */
public class EventoRecyclerAdapter extends RecyclerView.Adapter<EventoRecyclerAdapter.RecyclerViewHolder>{

    ArrayList<Evento> arrayList = new ArrayList<>();

    public EventoRecyclerAdapter(ArrayList<Evento> arrayList){

        this.arrayList = arrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder= new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Evento evento = arrayList.get(position);
        holder.Nombre.setText(evento.getNombre());
        holder.Comentario.setText(evento.getComentario());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public TextView Nombre, Comentario;

        public RecyclerViewHolder(View view){
            super(view);

            Nombre = (TextView)view.findViewById(R.id.nombre);
            Comentario = (TextView)view.findViewById(R.id.comentario);


        }
    }
}
