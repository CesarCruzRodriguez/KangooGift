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

import cruz.cesar.com.kangoogift.model.PersonaConRegalo;

/**
 * Created by practicas on 20/4/16.
 */
public class PersonaConEventoRecyclerAdapter extends RecyclerView.Adapter<PersonaConEventoRecyclerAdapter.RecyclerViewHolder> {

        ArrayList<PersonaConRegalo> personas = new ArrayList<>();
        Context ctx;

public PersonaConEventoRecyclerAdapter(ArrayList<PersonaConRegalo> personas, Context ctx) {
        this.personas = personas;
        this.ctx = ctx;
        }

@Override
public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.personas_navigation_drawer_row_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder= new RecyclerViewHolder(view, ctx, personas);

        return recyclerViewHolder;
        }

@Override
public void onBindViewHolder(RecyclerViewHolder holder, int position)  {

        PersonaConRegalo persona = personas.get(position);
        holder.Nombre.setText(persona.getNombre());
        holder.Fecha.setText(persona.getFecha());
        holder.Num_regalo.setText(String.valueOf(persona.getNum_regalos()));
        holder.Comentario.setText(persona.getComentario());
        }

@Override
public int getItemCount() {
        return personas.size();
        }

public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView Nombre, Fecha, Comentario, Num_regalo;
    public ArrayList<PersonaConRegalo> personas = new ArrayList<>();
    public Context ctx;

    public RecyclerViewHolder(View view, Context ctx, ArrayList<PersonaConRegalo> personas) {
        super(view);
        this.ctx = ctx;
        this.personas = personas;

        view.setOnClickListener(this);
        Nombre = (TextView) view.findViewById(R.id.nombre);
        Fecha = (TextView) view.findViewById(R.id.fecha);
        Num_regalo = (TextView) view.findViewById(R.id.num_regalos);
        Comentario = (TextView) view.findViewById(R.id.comentario);
    }

    @Override
    public void onClick(View v) {

        Log.d("click persona", "persona click");

        int position = getAdapterPosition();
        PersonaConRegalo persona = this.personas.get(position);

        Intent intent = new Intent(this.ctx, PersonaDetalle.class);
        intent.putExtra("id", persona.getId());
        intent.putExtra("nombre", persona.getNombre());
        intent.putExtra("fecha", persona.getFecha());
        intent.putExtra("comentario", persona.getComentario());
        this.ctx.startActivity(intent);
    }
}
}