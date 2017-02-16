package com.eagleeye.eagleeye;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView;

public class FiltroActivity extends AppCompatActivity{
    
    ImageView alojamiento;
    ImageView comida;
    ImageView dinero;
    ImageView emergencia;
    ImageView fallas;
    ImageView monumentos;
    ImageView ocio;
    ImageView transporte;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        
        alojamiento=(ImageView) findViewById(R.id.ESalojamiento);
        comida=(ImageView) findViewById(R.id.EScomida);
        dinero=(ImageView) findViewById(R.id.ESdinero);
        emergencia=(ImageView) findViewById(R.id.ESemergencia);
        fallas=(ImageView) findViewById(R.id.ESfallas);
        monumentos=(ImageView) findViewById(R.id.ESmonumentos);
        ocio=(ImageView) findViewById(R.id.ESocio);
        transporte=(ImageView) findViewById(R.id.EStransporte);

        alojamiento.setOnClickListener(filtroClickListener);
        comida.setOnClickListener(filtroClickListener);
        dinero.setOnClickListener(filtroClickListener);
        emergencia.setOnClickListener(filtroClickListener);
        fallas.setOnClickListener(filtroClickListener);
        monumentos.setOnClickListener(filtroClickListener);
        ocio.setOnClickListener(filtroClickListener);
        transporte.setOnClickListener(filtroClickListener);


    }
    private View.OnClickListener filtroClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){
            int id=v.getId();
            Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
            Bundle b=new Bundle();

            switch (id){
                case R.id.ESalojamiento:
                    b.putInt("filtro",1);
                    break;
                case R.id.EScomida:
                    b.putInt("filtro",2);
                    break;
                case R.id.ESdinero:
                    b.putInt("filtro",3);
                    break;
                case R.id.ESemergencia:
                    b.putInt("filtro",4);
                    break;
                case R.id.ESfallas:
                    b.putInt("filtro",5);
                    break;
                case R.id.ESmonumentos:
                    b.putInt("filtro",6);
                    break;
                case R.id.ESocio:
                    b.putInt("filtro",7);
                    break;
                case R.id.EStransporte:
                    b.putInt("filtro",8);
                    break;
            }

            intent.putExtras(b);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in,R.anim.left_out);
        }
    };

}
