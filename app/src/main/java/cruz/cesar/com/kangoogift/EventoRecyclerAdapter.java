package cruz.cesar.com.kangoogift;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cruz.cesar.com.kangoogift.model.Evento;

/**
 * Created by Cesar on 20/03/2016.
 */
public class EventoRecyclerAdapter extends RecyclerView.Adapter<EventoRecyclerAdapter.RecyclerViewHolder> {

    ArrayList<Evento> eventos = new ArrayList<>();
    Context ctx;

    public EventoRecyclerAdapter(ArrayList<Evento> arrayList, Context ctx){

        this.eventos = arrayList;
        this.ctx = ctx;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder= new RecyclerViewHolder(view, ctx, eventos);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Evento evento = eventos.get(position);
        holder.Nombre.setText(evento.getNombre());
        holder.Comentario.setText(evento.getComentario());

    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView Nombre, Comentario;
        public ArrayList<Evento> eventos = new ArrayList<>();
        public Context ctx;

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        public RecyclerViewHolder(View view, Context ctx, ArrayList<Evento> eventos){
            super(view);

            this.eventos = eventos;
            this.ctx = ctx;

            //metodo click en los CardsView de la lista de eventos
            view.setOnClickListener(this);

            Nombre = (TextView)view.findViewById(R.id.nombre);
            Comentario = (TextView)view.findViewById(R.id.comentario);

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Evento evento = this.eventos.get(position);

            Intent intent = new Intent(this.ctx, EventoDetalle.class);
            intent.putExtra("id", evento.getId());
            intent.putExtra("nombre", evento.getNombre());
            intent.putExtra("comentario", evento.getComentario());
            this.ctx.startActivity(intent);

        }

    }
}
