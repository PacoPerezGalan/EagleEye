package com.eagleeye.eagleeye;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button entrar;
    ImageView idioma;
    Configuration config;
    Locale locale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idioma=(ImageView) findViewById(R.id.idioma);
        entrar=(Button) findViewById(R.id.Entrar);

        config=new Configuration();
        locale=new Locale(Locale.getDefault().getLanguage().toString());
        if(Locale.getDefault().getDisplayLanguage().toString().compareTo("English")==0){
            idioma.setImageResource(R.drawable.idioma_en);
        }

        idioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ssToast.makeText(getApplicationContext(),Locale.getDefault().getLanguage().toString(),Toast.LENGTH_SHORT).show();
                if(locale.getLanguage().compareTo("en")==0){
                    locale=new Locale("es");
                    config.setLocale(locale);
                    getResources().updateConfiguration(config,null);
                    idioma.setImageResource(R.drawable.idioma_es);
                    entrar.setText(R.string.entrar2);
                }else {
                    locale=new Locale("en");
                    config.setLocale(locale);
                    getResources().updateConfiguration(config,null);
                    idioma.setImageResource(R.drawable.idioma_en);
                    entrar.setText(R.string.entrar2);
                }

            }
        });
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),FiltroActivity.class);

                startActivity(i);
                overridePendingTransition(R.anim.left_in,R.anim.left_out);

            }
        });


    }
}
