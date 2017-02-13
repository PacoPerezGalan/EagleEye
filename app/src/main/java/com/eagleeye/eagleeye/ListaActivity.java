package com.eagleeye.eagleeye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    ListaAdapter listaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        manager=new LinearLayoutManager(this);
        listaAdapter=new ListaAdapter(MapsActivity.lugaresList);

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(listaAdapter);



    }
}
