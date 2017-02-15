package com.eagleeye.eagleeye;

import android.app.Activity;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

import static com.eagleeye.eagleeye.FireBaseActivity.PREF;


public class ChatActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private  AdaptadorChat adaptador;
    private RecyclerView rv;
    private List<MensajesChat> mensajesChatL;
    private Firebase mFirebase;
    LinearLayout activity_main;
    ImageView enviar;
    EditText input;
    String nombre_usuario;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        rv = (RecyclerView)findViewById(R.id.recyclerV);
        enviar = (ImageView) findViewById(R.id.btn_enviar);
        input = (EditText) findViewById(R.id.input);
        mensajesChatL = new ArrayList<>();

        mId = Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);

        auth = FirebaseAuth.getInstance();

        rv.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorChat(mensajesChatL,mId,nombre_usuario);
        rv.setAdapter(adaptador);
        Firebase.setAndroidContext(this);

        mFirebase = new Firebase("https://eagleeye-565ad.firebaseio.com/").child("eagleeye-565ad");
        // instanciar un objeto sharedPreference

        SharedPreferences preferences = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        nombre_usuario = preferences.getString("nik","");
        Log.d("asdf","He recuperado -> "+nombre_usuario);


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creara una instacia hacia firebase insertando el mensaje sobre la clave del autor (email)
                mFirebase.push().setValue(new MensajesChat(input.getText().toString(),mId,nombre_usuario ));
                // vaciar texto
                input.setText("");
                input.requestFocus();
            }
        });


        mFirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null){
                    try {
                        MensajesChat model = dataSnapshot.getValue(MensajesChat.class);
                        mensajesChatL.add(model);
                        rv.scrollToPosition(mensajesChatL.size()-1);
                        adaptador.notifyItemInserted(mensajesChatL.size()-1);

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_sign_out) {
            auth.signOut();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}