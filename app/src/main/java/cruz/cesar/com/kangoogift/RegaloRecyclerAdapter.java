package cruz.cesar.com.kangoogift;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import cruz.cesar.com.kangoogift.model.Regalo;

/**
 * Created by Cesar on 16/04/2016.
 */
public class RegaloRecyclerAdapter extends RecyclerView.Adapter<RegaloRecyclerAdapter.RecyclerViewHolder> {


    ArrayList<Regalo> regalos = new ArrayList<>();
    Context ctx;

    public RegaloRecyclerAdapter(ArrayList<Regalo> regalos, Context ctx) {
        this.regalos = regalos;
        this.ctx = ctx;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.regalos_row_layout, parent, false);
        RecyclerViewHolder recyclerViewHolder= new RecyclerViewHolder(view, ctx, regalos);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Regalo regalo = regalos.get(position);
        holder.Nombre.setText(regalo.getNombre());
        holder.Estado.setText(regalo.getEstado());
        holder.Comentario.setText(regalo.getComentario());

    }

    @Override
    public int getItemCount() {
        return regalos.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView Nombre, Estado, Comentario;
        public ArrayList<Regalo> regalos = new ArrayList<>();
        public Context ctx;

        public RecyclerViewHolder(View view, Context ctx, ArrayList<Regalo> regalos) {
            super(view);
            this.ctx = ctx;
            this.regalos = regalos;

            view.setOnClickListener(this);
            Nombre = (TextView) view.findViewById(R.id.nombre);
            Estado = (TextView) view.findViewById(R.id.estado);
            Comentario = (TextView) view.findViewById(R.id.comentario);
        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Regalo regalo = this.regalos.get(position);

            Log.d("click regalo", "regalo click " + regalo.getNombre());



            Dialog dialog = new Dialog(ctx);
            dialog.setTitle(R.string.edit_regalo);
            dialog.setContentView(R.layout.edit_regalo_customdialog);

            EditText editTextNombre = (EditText)dialog.findViewById(R.id.nombre);
            EditText editTextComentario = (EditText)dialog.findViewById(R.id.comentario);
            CheckBox checkBoxEstado = (CheckBox)dialog.findViewById(R.id.estado);
            Button btnEditRegalo = (Button)dialog.findViewById(R.id.btnEditRegalo);
            Button btnDeleteRegalo = (Button)dialog.findViewById(R.id.btnDeleteRegalo);

            editTextNombre.setText(regalo.getNombre());
            editTextComentario.setText(regalo.getComentario());
            if(regalo.getEstado().equals("comprado")){
                checkBoxEstado.setChecked(true);
            }
            else{
                checkBoxEstado.setChecked(false);
            }
            dialog.show();



//
//            Intent intent = new Intent(this.ctx, RegaloDetalle.class);
//            intent.putExtra("id", regalo.getId());
//            intent.putExtra("nombre", regalo.getNombre());
//            intent.putExtra("estado", regalo.getEstado());
//            intent.putExtra("comentario", regalo.getComentario());
//            this.ctx.startActivity(intent);
        }
    }
}
