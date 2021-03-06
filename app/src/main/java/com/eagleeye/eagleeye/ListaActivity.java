package com.eagleeye.eagleeye;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.Map;

public class ListaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    ListaAdapter listaAdapter;
    AdaptadorJsonFallas fallasAdapter;
    AdaptadorMonumentos monumentosAdapter;
    String [] idLugares;
    boolean pendentFoto;
    static Context context;

    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        context=this;
        recyclerView=(RecyclerView) findViewById(R.id.recycler);
        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        if(MapsActivity.filtroSeleccionat==5){
            fallasAdapter=new AdaptadorJsonFallas(MapsActivity.fallasL,this);
            recyclerView.setAdapter(fallasAdapter);
        }else if(MapsActivity.filtroSeleccionat==6){
            monumentosAdapter=new AdaptadorMonumentos(MapsActivity.monumentosL,this);
            recyclerView.setAdapter(monumentosAdapter);
        }else{

            idLugares=new String [MapsActivity.lugaresList.size()];




            for(int i=0;i<MapsActivity.lugaresList.size();i++){
                idLugares[i]=MapsActivity.lugaresList.get(i).getId();
            }


            PendingResult<PlaceBuffer> pendingResult = Places.GeoDataApi.getPlaceById(MapsActivity.mGoogleApiClient, idLugares);
            pendingResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                @Override
                public void onResult(PlaceBuffer places) {
                    if (places.getStatus().isSuccess() && places.getCount() > 0) {
                        for (int i = 0; i < places.getCount(); i++) {
                            Place myPlace = places.get(i);
                            Log.i("place", "Place nombre: " + myPlace.getName() + "   l es= " + i);

                            MapsActivity.lugaresList.get(i).setAdress(myPlace.getAddress().toString());
                            Log.i("place", "Place direccion: " + myPlace.getAddress().toString());
                            MapsActivity.lugaresList.get(i).setWeb(myPlace.getWebsiteUri());
                            Log.i("place", "Place web: " + myPlace.getWebsiteUri());
                            MapsActivity.lugaresList.get(i).setPhone(myPlace.getPhoneNumber().toString());
                            Log.i("place", "Place telf: " + myPlace.getPhoneNumber());
                            MapsActivity.lugaresList.get(i).setRating(myPlace.getRating());
                            Log.i("place", "Place valoracion: " + myPlace.getRating());

                        }

                        listaAdapter=new ListaAdapter(MapsActivity.lugaresList);

                        recyclerView.setAdapter(listaAdapter);
                    } else {
                        Log.e("place", "Place not found");
                    }
                    places.release();
                }
            });
        }




        /*
        pendentFoto=false;
        for(int i=0;i<idLugares.length;i++) {
            new AgafarFoto().execute();

        }
        */

    }
    /*
    public class AgafarFoto extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... id) {
            PlacePhotoMetadataResult result = Places.GeoDataApi
                    .getPlacePhotos(MapsActivity.mGoogleApiClient, "ChIJN1t_tDeuEmsRUsoyG83frY4").await();
// Get a PhotoMetadataBuffer instance containing a list of photos (PhotoMetadata).
            if (result != null && result.getStatus().isSuccess()) {
                PlacePhotoMetadataBuffer photoMetadataBuffer = result.getPhotoMetadata();
                PlacePhotoMetadata photo = photoMetadataBuffer.get(0);

                Bitmap image = photo.getPhoto(MapsActivity.mGoogleApiClient).await()
                        .getBitmap();
                //MapsActivity.lugaresList.get
                Log.d("image", "agarrant image ");
            }




            return null;
        }
    }
     */
}
