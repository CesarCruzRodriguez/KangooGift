package cruz.cesar.com.kangoogift;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import cruz.cesar.com.kangoogift.model.Persona;

/**
 * Created by Cesar on 24/03/2016.
 */
public class PersonaRecyclerAdapter extends RecyclerView.Adapter<PersonaRecyclerAdapter.RecyclerViewHolder> {

    ArrayList<Persona> personas = new ArrayList<>();
    Context ctx;

    public PersonaRecyclerAdapter(ArrayList<Persona> personas, Context ctx) {
        this.personas = personas;
        this.ctx = ctx;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personas_row_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder= new RecyclerViewHolder(view, ctx, personas);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position)  {

        Persona persona = personas.get(position);
        holder.Nombre.setText(persona.getNombre());
        holder.Fecha.setText(persona.getFecha());
        holder.Comentario.setText(persona.getComentario());
    }

    @Override
    public int getItemCount() {
        return personas.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView Nombre, Fecha, Comentario;
        public ArrayList<Persona> personas = new ArrayList<>();
        public Context ctx;

        public RecyclerViewHolder(View view, Context ctx, ArrayList<Persona> personas) {
            super(view);
            this.ctx = ctx;
            this.personas = personas;

            view.setOnClickListener(this);
            Nombre = (TextView) view.findViewById(R.id.nombre);
            Fecha = (TextView) view.findViewById(R.id.fecha);
            Comentario = (TextView) view.findViewById(R.id.comentario);
        }

        @Override
        public void onClick(View v) {

            Log.d("click persona", "persona click");

            int position = getAdapterPosition();
            Persona persona = this.personas.get(position);

            Intent intent = new Intent(this.ctx, PersonaDetalle.class);
            intent.putExtra("id", persona.getId());
            intent.putExtra("nombre", persona.getNombre());
            intent.putExtra("fecha", persona.getFecha());
            intent.putExtra("comentario", persona.getComentario());
            this.ctx.startActivity(intent);
        }
    }
}
