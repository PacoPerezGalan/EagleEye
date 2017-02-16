package com.eagleeye.eagleeye;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by ortim on 13/02/2017.
 */

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.ViewHolder>{
    private List<MensajesChat> mensajesChatL;
    private Context context;
    private String mensaje_usuario;
    private String mensaje_texto;
    private Date mensasje_fecha;
    private String mId;
    CardView cardView;

    private static final int CHAT_LEFT=1;
    private static final int CHAT_RIGHT=2;

    public AdaptadorChat(List<MensajesChat> mensajesChatL,String id,String mensaje_usuario) {
        this.mensajesChatL = mensajesChatL;
        this.mId=id;
        this.mensaje_usuario = mensaje_usuario;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message_text;
        public TextView message_user;
        public TextView message_date;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            message_text = (TextView)itemView.findViewById(R.id.message_text);
            message_user = (TextView)itemView.findViewById(R.id.message_user);
            message_date = (TextView)itemView.findViewById(R.id.message_time);
        }
    }


    public int getItemViewType (int position){
        if (mensajesChatL.get(position).getId().equals(mId)){
            return CHAT_RIGHT;
        }
        return CHAT_LEFT;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        if (viewType == CHAT_LEFT){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        } else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_right,parent,false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MensajesChat mensajesChat = mensajesChatL.get(position);
        holder.message_text.setText(mensajesChat.getTexto_mensaje());
        holder.message_user.setText(mensajesChat.getUsuario_mensaje());
        holder.message_date.setText(DateFormat.format("HH:mm",mensajesChat.getDate_mensaje()));

        // acción sobre el cardView para saber que item vamos a seleccionar
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //identificar el nombre del item y enviarlo al siguiente activity
                // esta acción identificara con quien se creara el chat
                String nombre = mensajesChatL.get(position).getUsuario_mensaje();
                String nombreEmisor = mensajesChatL.get(0).getUsuario_mensaje();
                Intent i = new Intent(view.getContext(),IndividualChatActivity.class);
                i.putExtra("nombre",nombre);
                i.putExtra("nombreE",nombreEmisor);
                view.getContext().startActivity(i);

            }
        });



    }



    @Override
    public int getItemCount() {
        return mensajesChatL.size();
    }
}
