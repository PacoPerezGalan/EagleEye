package com.eagleeye.eagleeye;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

import static com.eagleeye.eagleeye.MapsActivity.fallasL;

/**
 * Created by ortim on 11/02/2017.
 */

public class AdaptadorMonumentos extends RecyclerView.Adapter<AdaptadorMonumentos.MonumentosViewHolder> {
    private List<Monumentos> monumentosL;
    Context context;
    LinearLayout linear;

    public AdaptadorMonumentos(List<Monumentos> monumentosL, Context context) {
        this.context = context;
        this.monumentosL = monumentosL;
    }

    public class MonumentosViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView direccion;
        public TextView entrada;
        public TextView cerrado;
        public ImageView img;
        ImageView ubicacio;

        public MonumentosViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView)itemView.findViewById(R.id.nombre);
            direccion = (TextView)itemView.findViewById(R.id.direccion);
            entrada = (TextView)itemView.findViewById(R.id.entrada);
            cerrado = (TextView)itemView.findViewById(R.id.cerrado);
            img = (ImageView)itemView.findViewById(R.id.img);
            ubicacio=(ImageView) itemView.findViewById(R.id.ubicacio);
        }
    }

    @Override
    public MonumentosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_monumento,parent,false);
        return new MonumentosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MonumentosViewHolder holder, int position) {
            holder.nombre.setText(this.monumentosL.get(position).getNombre());
            holder.direccion.setText(this.monumentosL.get(position).getDireccion());
            Picasso.with(context).load(this.monumentosL.get(position).getImagen()).into(holder.img);
            holder.entrada.setText(this.monumentosL.get(position).getEntrada());
            holder.cerrado.setText(this.monumentosL.get(position).getCerrado());

        holder.ubicacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=new Bundle();
                b.putDouble("lat",monumentosL.get(holder.getAdapterPosition()).getLatitud());
                b.putDouble("lng",monumentosL.get(holder.getAdapterPosition()).getLongitud());
                b.putInt("marker",holder.getAdapterPosition());
                ((Activity)ListaActivity.context).getIntent().putExtras(b);
                ((Activity)ListaActivity.context).setResult(Activity.RESULT_OK,((Activity)ListaActivity.context).getIntent());
                ((Activity)ListaActivity.context).finish();
                //Toast.makeText(view.getContext(),lugarList.get(holder.getAdapterPosition()).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return monumentosL.size();
    }


}
