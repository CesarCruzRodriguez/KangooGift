package cruz.cesar.com.kangoogift;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
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

import cruz.cesar.com.kangoogift.db.DB_Helper;
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
            final Regalo regalo = this.regalos.get(position);

            Log.d("click regalo", "regalo click " + regalo.getNombre());



            final Dialog dialog = new Dialog(ctx);
            dialog.setTitle(R.string.edit_regalo);
            dialog.setContentView(R.layout.edit_regalo_customdialog);

            final EditText editTextNombre = (EditText)dialog.findViewById(R.id.nombre);
            final EditText editTextComentario = (EditText)dialog.findViewById(R.id.comentario);
            final CheckBox checkBoxEstado = (CheckBox)dialog.findViewById(R.id.estado);
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
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

            btnEditRegalo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checkBoxEstado.isChecked()){
                        regalo.setEstado("comprado");
                    }
                    else{
                        regalo.setEstado("idea");
                    }
                    regalo.setNombre(editTextNombre.getText().toString());
                    regalo.setComentario(editTextComentario.getText().toString());
                    // EDITAR REGALO
                    DB_Helper db_helper = new DB_Helper(ctx);
                    SQLiteDatabase db = db_helper.getWritableDatabase();
                    db_helper.actualizarRegalo(regalo.getId(), regalo.getNombre(), regalo.getEstado(), regalo.getComentario(), db);
                    dialog.dismiss();
                }
            });

            btnDeleteRegalo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // BORRAR REGALO

                    AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                    builder.setMessage(R.string.confirmarBorrarRegalo)
                            .setTitle(R.string.borrar_regalo);

                    builder.setPositiveButton(R.string.eliminar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            String id_regalo_string = String.valueOf(regalo.getId());
                            DB_Helper db_helper = new DB_Helper(ctx);
                            SQLiteDatabase db = db_helper.getWritableDatabase();
                            db_helper.borrarRegalo(id_regalo_string, db);

                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // cancelar
                        }
                    });

                    AlertDialog dialog_confirm_borrar = builder.create();
                    dialog_confirm_borrar.show();

                    dialog_confirm_borrar.setOnDismissListener((new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                            Log.d("onDismiss", "mensaje de OnDismiss de ElminarRegalo");
                            ((Activity) ctx).finish();
                            ctx.startActivity(((Activity) ctx).getIntent());
                        }
                    }));
                }
            });



            dialog.setOnDismissListener((new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    Log.d("onDismiss", "mensaje de OnDismiss de EditarRegalo");
//                    Intent i = new Intent(ctx, PersonaDetalle.class);
//                    i.putExtra("id", regalo.getPersona_id());
//
//                    DB_Helper db_helper = new DB_Helper(ctx);
//                    SQLiteDatabase db = db_helper.getReadableDatabase();
//                    Cursor c = db_helper.getPersonaDatosWhereId(regalo.getPersona_id(), db);
//
//                    if(c.moveToFirst()){
//                        do {
//                            i.putExtra("id", c.getInt(0));
//                            i.putExtra("nombre", c.getString(2));
//                        }while(c.moveToNext());
//                    }
//                    ((Activity) ctx).finish();
//                    ((Activity) ctx).startActivity(i);


//                        Intent _intent = new Intent(PersonaDetalle.this, PersonaDetalle.class);
//                        _intent.putExtra("nombre", editPeronaListener.get_nombre());
//                        _intent.putExtra("id", editPeronaListener.get_id());
//                        finish();
//                        startActivity(_intent);

//                    ((Activity) ctx).onBackPressed();


                    ((Activity) ctx).finish();
                    ctx.startActivity(((Activity) ctx).getIntent());
                }
            }));
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
