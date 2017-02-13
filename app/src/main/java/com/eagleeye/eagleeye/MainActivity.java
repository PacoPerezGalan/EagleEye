package com.eagleeye.eagleeye;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrar=(Button) findViewById(R.id.Entrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(i,ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });


    }
}
