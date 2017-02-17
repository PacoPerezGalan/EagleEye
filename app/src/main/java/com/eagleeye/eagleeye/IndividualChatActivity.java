package com.eagleeye.eagleeye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class IndividualChatActivity extends AppCompatActivity {
    private Button btn_enviar;
    private EditText input;
    private TextView chat_conversacion;

    private String nombre_usuario;
    private String nombre_Emisor;
    private String temp_key;

    //Objeto de Database con el que se lograra comunicar con Firebase
    private DatabaseReference bd_Firebase;

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_in,R.anim.right_out);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_chat);

        btn_enviar = (Button)findViewById(R.id.btn_enviar);
        input = (EditText)findViewById(R.id.input);
        chat_conversacion = (TextView)findViewById(R.id.chat_conversacion);

        // primero recoger el nombre enviado desde el adaptador
        nombre_usuario = getIntent().getExtras().get("nombre").toString();
        nombre_Emisor = getIntent().getExtras().get("nombreE").toString();
        setTitle(" Chat con -"+nombre_usuario);
        // Crear una instancia con una referencia por nombre de usuario
        // se creara una tabla aparte del chat masivo
        bd_Firebase = FirebaseDatabase.getInstance().getReference("conversaciones").child(nombre_usuario);

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Al pulsar el boton se creara un HasMap que contendra el nombre
                // de la nueva tabla -getKey-, subirlo a Firebase por la instancia creada
                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = bd_Firebase.push().getKey();
                bd_Firebase.updateChildren(map);


                // Instanciar una nueva referencia a la bd, pero en este caso
                // se almacenara el contenido del campo previamente creado -temp_key-
                DatabaseReference bd_Firebase2 = bd_Firebase.child(temp_key);

                // Crear un nuevo HasMap que almacenara la información -usuario,msg-
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("usuario_mensaje",nombre_Emisor);
                map2.put("texto_mensaje",input.getText().toString());

                bd_Firebase2.updateChildren(map2);



            }
        });

        bd_Firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                ver_Chat(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ver_Chat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private  String user_msg, msg_chat;
    private void ver_Chat(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){
            //recorrer el contenido de la tabla añadiendo la inf a almacenar (en este caso usuario,msg)
            // Cada valor sera añadido como valor, es decir un campo de la tabla -getValue-
            user_msg = (String) ((DataSnapshot)i.next()).getValue();
            msg_chat = (String) ((DataSnapshot)i.next()).getValue();

            chat_conversacion.append(msg_chat+": \n "+user_msg+"\n\n");

            input.setText("");
        }
    }
}