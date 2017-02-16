package com.eagleeye.eagleeye;



import android.Manifest;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
import android.location.Location;



import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.places.Place;

import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Scanner;

import static android.R.attr.bitmap;
import static android.R.attr.width;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    Marker marcador;
    Double lat = 0.0;
    double lng = 0.0;

    static GoogleApiClient mGoogleApiClient;
    Button vista;

    static ArrayList<Lugar> lugaresList;
    ArrayList<Marker> markerArrayList;



    private String [] titulo = {"Alojamiento", "Comida","Dinero", "Emergencia", "Fallas", "Monumentos", "Ocio", "Transporte"};

    private int [] icon = {R.drawable.alojamiento,R.drawable.food,R.drawable.dinero,R.drawable.emergencia,R.drawable.fallas,R.drawable.monumentos,R.drawable.ocio,R.drawable.transportes};
    private int [] icon2 = {R.drawable.alojamiento2,R.drawable.food2,
            R.drawable.dinero2, R.drawable.emergencia2,
            R.drawable.fallas2,R.drawable.monumentos2,
            R.drawable.ocio2,R.drawable.transportes2};

    private boolean drawerOpen = false;

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ActionBarDrawerToggle toggle;

    private DrawerLayout drawerLayout;
    static NavDrawerAdapter adapter;
    static int filtroSeleccionat;
    static String filtroTypes;
    Button btn_lista;
    Button btn_chat;

    static ArrayList<Fallas> fallasL;
    static ArrayList<Monumentos>monumentosL;
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in,R.anim.left_out);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        lugaresList=new ArrayList<Lugar>();
        markerArrayList=new ArrayList<Marker>();
        filtroTypes="";
        btn_lista=(Button) findViewById(R.id.btn_lista);
        btn_chat=(Button) findViewById(R.id.btn_chat);
        fallasL=new ArrayList<Fallas>();
        monumentosL=new ArrayList<Monumentos>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().show();
        getSupportActionBar().setTitle("");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        Bundle b=getIntent().getExtras();
        filtroSeleccionat=b.getInt("filtro");

        adapter = new NavDrawerAdapter(icon2, titulo);

        recyclerView.setAdapter(adapter);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toggle = new CustomToggle(MapsActivity.this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();


        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(AppIndex.API).build();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ListaActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }
        });

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),FireBaseActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);
            }
        });

    }
    public static void canviarFiltroSeleccionat(int s){
        filtroSeleccionat=s;
        adapter.notifyDataSetChanged();
        switch (s){
            case 1:
                filtroTypes="lodging|campground|rv_park";
                break;
            case 2:
                filtroTypes="food|meal_delivery|meal_takeaway|restaurant|bar|grocery_or_supermarket|cafe|liquor_store";
                break;
            case 3:
                filtroTypes="bank|atm";
                break;
            case 4:
                filtroTypes="doctor|health|hospital|pharmacy|embassy|police|city_hall";
                break;
            case 5:
                filtroTypes="";
                break;
            case 6:
                filtroTypes="";
                break;
            case 7:
                filtroTypes="night_club|bowling_alley|casino|stadium|park|amusement_park|aquarium|zoo|spa|shopping_mall|museum|art_gallery|movie_theater";
                break;
            case 8:
                filtroTypes="bus_station|subway_station|taxi_stand|train_station|airport|parking";
                break;
        }


    }


    public void pintarLugaresMapa(){

        Bitmap bitmap= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),icon[filtroSeleccionat-1]),100,100,false);

        if(filtroSeleccionat==5){
            for(int i=0;i<fallasL.size();i++){


                Double lat=fallasL.get(i).getLatitud();
                Double lng=fallasL.get(i).getLongitud();
                LatLng coordenades = new LatLng(lat, lng);

                markerArrayList.add(mMap.addMarker(new MarkerOptions()
                        .position(coordenades)
                        .title(fallasL.get(i).getNombre())
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))));

            }
        }else if(filtroSeleccionat==6){
            for(int i=0;i<monumentosL.size();i++){


                Double lat=monumentosL.get(i).getLatitud();
                Double lng=monumentosL.get(i).getLongitud();
                LatLng coordenades = new LatLng(lat, lng);

                markerArrayList.add(mMap.addMarker(new MarkerOptions()
                        .position(coordenades)
                        .title(monumentosL.get(i).getNombre())
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))));

            }
        }else{
            for(int i=0;i<lugaresList.size();i++){


                Double lat=lugaresList.get(i).getLat();
                Double lng=lugaresList.get(i).getLng();
                LatLng coordenades = new LatLng(lat, lng);

                markerArrayList.add(mMap.addMarker(new MarkerOptions()
                        .position(coordenades)
                        .title(lugaresList.get(i).getName())
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))));

            }
        }


    }


    int razon;
    boolean botonUbicacionPulsado;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);

        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setPadding(0,125,0,125);

        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int r) {
                razon =r;

            }
        });
        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if(razon !=2 || botonUbicacionPulsado) {

                    mMap.clear();
                    lugaresList.clear();
                    fallasL.clear();
                    markerArrayList.clear();

                    if(filtroSeleccionat!=5 && filtroSeleccionat!=6){
                        comprovaConnexio();
                    }else if(filtroSeleccionat==5){
                        new FallesAsyncTask().execute();
                    }else{
                        new MonumentosAsyncTask().execute();
                    }

                }
                botonUbicacionPulsado=false;
                //Toast.makeText(getApplicationContext(),"menejant camara",Toast.LENGTH_SHORT).show();
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                botonUbicacionPulsado=true;
                return false;
            }

        });




    }



    private void actualitzarUbicacio(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            Toast.makeText(this, "Localizacion chachi: " + lat + "   " + lng, Toast.LENGTH_SHORT).show();
            //agregarMarcador(lat, lng);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 15));
        } else {

        }
    }





    //parte coger cosas de url api google/////////////////////////////////////////////////////////////////////////////

    protected boolean comprovaConnexio() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            new ConectaURL().execute("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyB38CQ8uYvqAmZfTnXAjX1A3IPEYHun-9s&location="+mMap.getCameraPosition().target.latitude+","+mMap.getCameraPosition().target.longitude+"&rankby=distance&types="+filtroTypes);
            return true;
        } else {
            /*
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.recyclerPost), "No hi ha connexió a la Xarxa", Snackbar.LENGTH_INDEFINITE);

            mySnackbar.setAction("Tornar a provar", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    xarxa = comprovaConnexio();
                }
            });
            mySnackbar.show();
            */
            return false;
        }
    }


    private class ConectaURL extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... url) {

            String documentJSON = conectaURL(url[0]);

            long tempsInicial = System.currentTimeMillis();

            parsejaJSON(documentJSON);

            long tempsFinal = System.currentTimeMillis();

            return ((tempsFinal-tempsInicial)+" ms.");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pintarLugaresMapa();
            //adaptadorRecyclerPost.notifyDataSetChanged();

        }
    }


    private String conectaURL(String llocAConnectar){
        URL url;
        String resposta=null;
        try {
            Log.d("tag","Iniciant la connexió: (");

            url = new URL(llocAConnectar);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            int response = conn.getResponseCode();
            Log.d("tag", "Rebent dades des del Servidor en streaming: ");

            InputStream is = new BufferedInputStream(conn.getInputStream());
            Log.d("tag","Convertint l'streaming en un String: ");

            resposta = converteixStreamAString(is);

            Log.d("tag","Resposta/////////////////////: ("+response+")"+resposta);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            return resposta;
        }
    }

    private String converteixStreamAString(InputStream is) {

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void parsejaJSON(String documentJSON) {

        Log.d("tag", "Resposta2: " + documentJSON);

        if (documentJSON != null) {
            try {
                JSONObject jsonTodo = new JSONObject(documentJSON);
                JSONArray jsonResultados = jsonTodo.getJSONArray("results") ;

                for (int i = 0; i < jsonResultados.length(); i++) {
                    JSONObject jsonObj = jsonResultados.getJSONObject(i);

                    String place_id = jsonObj.getString("place_id");
                    String icon=jsonObj.getString("icon");
                    String name=jsonObj.getString("name");
                    Double lat=Double.parseDouble(jsonObj.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                    Double lng=Double.parseDouble(jsonObj.getJSONObject("geometry").getJSONObject("location").getString("lng"));

                    Lugar unLugar = new Lugar();

                    unLugar.setId(place_id);
                    unLugar.setName(name);
                    unLugar.setLat(lat);
                    unLugar.setLng(lng);

                    Log.d("tag",unLugar.getId().toString());
                    lugaresList.add(unLugar);
                }
            } catch (final JSONException e) {
                Log.e("tag", "Error parsejant Json: " + e.getMessage());
                //Snackbar.make(findViewById(R.id.recyclerPost), "Error parsejant Json", Snackbar.LENGTH_LONG).show();
            }
        } else {
            Log.e("tag", "Error intentant rebre el Json.");
            //Snackbar.make(findViewById(R.id.recyclerPost), "Error intentant rebre el Json.", Snackbar.LENGTH_LONG).show();

        }

    }






    //metodes al iniciar el mapa////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(mGoogleApiClient, getIndexApiAction());
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mGoogleApiClient, getIndexApiAction());
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            actualitzarUbicacio(mLastLocation);

        } else {
            Toast.makeText(this, "onConnected: location null", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    //part metodes per al navigation/////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.vista){
            if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                item.setTitle("Vista normal");

            } else {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                item.setTitle("Vista satelite");
            }

            return true;
        }

        if(id == android.R.id.home) {


            if (drawerOpen) {
                drawerLayout.closeDrawers();

            }else{
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    class CustomToggle extends ActionBarDrawerToggle{

        public CustomToggle(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes){
            super (activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        public void onDrawerOpened (View drawerView){
            super.onDrawerOpened(drawerView);

            drawerOpen = true;
        }

        public void onDrawerClosed (View drawerView){
            super.onDrawerClosed(drawerView);

            drawerOpen = false;
        }
    }



    ///////falles//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public class  FallesAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            Resources res = getResources();
            InputStream is = res.openRawResource(R.raw.monumentos_falleros);
            Scanner scanner = new Scanner(is);

            StringBuilder builder = new StringBuilder();

            while (scanner.hasNextLine()){
                builder.append(scanner.nextLine());
            }

            parseJsonFalles(builder.toString());


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pintarLugaresMapa();
        }
    }

    private void parseJsonFalles(String doc) {
        if (doc != null){

            try {
                JSONObject jsonObject = new JSONObject(doc);
                JSONArray jsonArray = jsonObject.getJSONArray("features");
                for (int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("properties");
                    String nombre = jsonObject2.getString("nombre");
                    String seccion = jsonObject2.getString("seccion");
                    String lema = jsonObject2.getString("lema");
                    String imgSource = jsonObject2.getString("boceto");

                    Fallas fallas = new Fallas();
                    fallas.setNombre(nombre);
                    fallas.setSeccion(seccion);
                    fallas.setLema(lema);
                    fallas.setBoceto(imgSource);
                    fallas.setLongitud(jsonObject1.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0));
                    fallas.setLatitud(jsonObject1.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1));

                    Log.d("asdf","Nombre -> "+nombre+"Longitud ->"+jsonObject1.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0)+" Latitud -> "+jsonObject1.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1));

                    fallasL.add(fallas);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

////////////////monumentos////////////////////////////////////////////////////////////////////////////////////////
public class MonumentosAsyncTask extends AsyncTask<String,Void,String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls) {
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.monumentos);
        Scanner scanner = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()){
            builder.append(scanner.nextLine());
        }

        parseJsonMonumentos(builder.toString());


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pintarLugaresMapa();
    }
}
private void parseJsonMonumentos (String doc){
    if (doc != null){

        try {
            JSONObject jsonObject = new JSONObject(doc);
            JSONArray jsonArray = jsonObject.getJSONArray("monumentos");
            for (int i = 0; i< jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String nombre = jsonObject1.getString("nombre");
                String direccion = jsonObject1.getString("direccion");
                String entrada = jsonObject1.getString("entrada");
                String cerrado = jsonObject1.getString("cerrado");
                String imagen = jsonObject1.getString("imagen");


                Monumentos monumentos=new Monumentos();
                monumentos.setNombre(nombre);
                monumentos.setDireccion(direccion);
                monumentos.setEntrada(entrada);
                monumentos.setCerrado(cerrado);
                monumentos.setImagen(imagen);
                monumentos.setLongitud(jsonObject1.getJSONArray("coordenadas").getDouble(0));
                monumentos.setLatitud(jsonObject1.getJSONArray("coordenadas").getDouble(1));

                Log.d("asdf","nombre -> "+nombre+" longitud -> "+ jsonObject1.getJSONArray("coordenadas").getDouble(0)+" latitud -> "+ jsonObject1.getJSONArray("coordenadas").getDouble(1) );
                monumentosL.add(monumentos);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


}


