package com.eagleeye.eagleeye;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

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

        public MonumentosViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView)itemView.findViewById(R.id.nombre);
            direccion = (TextView)itemView.findViewById(R.id.direccion);
            entrada = (TextView)itemView.findViewById(R.id.entrada);
            cerrado = (TextView)itemView.findViewById(R.id.cerrado);
            img = (ImageView)itemView.findViewById(R.id.img);
        }
    }

    @Override
    public MonumentosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_monumento,parent,false);
        return new MonumentosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MonumentosViewHolder holder, int position) {
            holder.nombre.setText(this.monumentosL.get(position).getNombre());
            holder.direccion.setText(this.monumentosL.get(position).getDireccion());
            Picasso.with(context).load(this.monumentosL.get(position).getImagen()).into(holder.img);
            holder.entrada.setText(this.monumentosL.get(position).getEntrada());
            holder.cerrado.setText(this.monumentosL.get(position).getCerrado());
    }

    @Override
    public int getItemCount() {
        return monumentosL.size();
    }


}
