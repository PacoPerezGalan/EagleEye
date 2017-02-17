package com.eagleeye.eagleeye;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

public class AdaptadorJsonFallas extends RecyclerView.Adapter<AdaptadorJsonFallas.FallasViewHolder> {
    private List<Fallas> fallasL;
    Context context;
    LinearLayout linearLayout;

    public AdaptadorJsonFallas(List<Fallas> fallasL, Context context) {
        this.fallasL = fallasL;
        this.context = context;
    }

    public class FallasViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView seccion;
        private TextView lema;
        private ImageView img;
        ImageView ubicacio;


        public FallasViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView)itemView.findViewById(R.id.nombre);
            seccion = (TextView)itemView.findViewById(R.id.seccion);
            lema = (TextView)itemView.findViewById(R.id.lema);
            img = (ImageView)itemView.findViewById(R.id.img);
            ubicacio=(ImageView) itemView.findViewById(R.id.ubicacio);

        }
    }

    @Override
    public FallasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_falla,parent,false);

        return new FallasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FallasViewHolder holder, final int position) {
            holder.nombre.setText(this.fallasL.get(position).getNombre());
            holder.seccion.setText(this.fallasL.get(position).getSeccion());
            holder.lema.setText(this.fallasL.get(position).getLema());
            Picasso.with(context).load(this.fallasL.get(position).getBoceto()).into(holder.img);

        holder.ubicacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b=new Bundle();
                b.putDouble("lat",fallasL.get(holder.getAdapterPosition()).getLatitud());
                b.putDouble("lng",fallasL.get(holder.getAdapterPosition()).getLongitud());
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
        return fallasL.size();
    }

}
