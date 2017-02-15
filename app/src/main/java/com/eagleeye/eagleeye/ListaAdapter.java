package com.eagleeye.eagleeye;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by 2dam on 13/02/2017.
 */

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.itemViewHolder> {
    ArrayList<Lugar> lugarList;

    public static class itemViewHolder extends RecyclerView.ViewHolder {
        TextView lugar,direccion,localizacion,telefono,web;
        ImageView imatge;
        RatingBar ratingBar;
        CardView card;
        public itemViewHolder(View v) {
            super(v);
            lugar= (TextView) v.findViewById(R.id.tv_lugar);
            direccion= (TextView) v.findViewById(R.id.tv_direccion);
            telefono= (TextView) v.findViewById(R.id.tv_telefono);
            web= (TextView) v.findViewById(R.id.tv_web);
            imatge=(ImageView) v.findViewById(R.id.iv_lugar);
            ratingBar=(RatingBar) v.findViewById(R.id.ratingBar);

            card= (CardView) v.findViewById(R.id.card);

        }

    }


    public ListaAdapter(ArrayList<Lugar> lugarList) {
        this.lugarList = lugarList;
    }

    @Override
    public int getItemCount() {
        return lugarList.size();
    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lugar, parent, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final itemViewHolder holder, int position) {
        holder.lugar.setText(lugarList.get(position).getName());
        holder.direccion.setText(lugarList.get(position).getAdress());
        //holder.localizacion.setText("Localizacion: "+lugarList.get(position).getLat()+" , "+lugarList.get(position).getLng());
        holder.telefono.setText(lugarList.get(position).getPhone());

        holder.web.setClickable(true);
        holder.web.setText(Html.fromHtml(lugarList.get(position).getWeb()+""));
        holder.web.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lugarList.get(holder.getAdapterPosition()).getWeb()+""));
                view.getContext().startActivity(myIntent);
            }
        });


        holder.imatge.setImageBitmap(lugarList.get(position).getFoto());

        if(lugarList.get(position).getRating()!=-1.0){
            holder.ratingBar.setRating(lugarList.get(holder.getAdapterPosition()).getRating());
        }else{
            holder.ratingBar.setVisibility(View.INVISIBLE);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),lugarList.get(holder.getAdapterPosition()).getName(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}