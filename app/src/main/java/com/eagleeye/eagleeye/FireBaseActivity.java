package com.eagleeye.eagleeye;

        import android.app.Activity;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.annotation.NonNull;
        import android.support.design.widget.Snackbar;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;

public class FireBaseActivity extends Activity {

    EditText email;
    EditText pwd;
    EditText nickname;
    Button btnEntrar;
    TextView registrar;
    LinearLayout activity_registro;
    private FirebaseAuth auth;
    public static final String PREF = "preferencias";
    Intent i;
    boolean nuevoUsuario = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base);

        btnEntrar = (Button)findViewById(R.id.btnEntrar);
        email = (EditText)findViewById(R.id.email);
        pwd = (EditText) findViewById(R.id.pass);
        nickname = (EditText)findViewById(R.id.nikname);
        registrar = (TextView)findViewById(R.id.registrar);


        activity_registro = (LinearLayout) findViewById(R.id.activity_registro);
        auth = FirebaseAuth.getInstance();

        // rellenara los campos automaticamente si es que ya tiene cuenta
        SharedPreferences preferences = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
        String emailPreference = preferences.getString("email", "");
        String pwdPreference = preferences.getString("pwd", "");

        email.setText(emailPreference);
        pwd.setText(pwdPreference);



        // TextView que mostrara los campos a registrarse
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    nuevoUsuario = true;
                    btnEntrar.setText("REGISTRARSE");
                    nickname.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    Snackbar.make(activity_registro, "No se encuentran tus datos, vuelve a registrarte", Snackbar.LENGTH_SHORT).show();
                }


            }

        });


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Si es nuevo usuario llamar al metodo de Firebase signUp donde se le pasara los
                // campos para registrarse, además guardarlos en SharedPreference para aprovecharlo en un
                // nuevo acceso con la misma cuenta
                if (nuevoUsuario == true){
                    signUpUser(email.getText().toString(),pwd.getText().toString());
                    SharedPreferences preferences = getSharedPreferences(PREF, Activity.MODE_PRIVATE);
                    // instanciar editor que podra modificar los datos
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email",email.getText().toString());
                    editor.putString("pwd",pwd.getText().toString());
                    editor.putString("nik",nickname.getText().toString());
                    editor.commit();

                    // volver a poner false el boolean para un acceso futuro
                    nuevoUsuario = false;
                    btnEntrar.setText("ENTRAR");
                } else {
                    try {
                        // Llamar al metodo singIn pasandole los campos que contienen la inf de usuario
                        // (para eso se guarda en SharedPreference en el register)
                        signIn(email.getText().toString(), pwd.getText().toString());
                    }catch (Exception e){
                        Snackbar.make(activity_registro, "Debes registrarte antes de acceder a esta opción", Snackbar.LENGTH_SHORT).show();

                    }

                }
            }
        });
    }

    private void signUpUser(String email, String pass){
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Snackbar.make(activity_registro, "No se ha podido registrar...vuelve a intentarlo", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(activity_registro, "Registrado", Snackbar.LENGTH_SHORT).show();
                            i = new Intent(getApplicationContext(),ChatActivity.class);
                            startActivity(i);
                        }

                    }
                });
    }

    private void signIn(String email, String pass){
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Snackbar.make(activity_registro, "No se ha podido acceder...vuelve a intentarlo", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(activity_registro, "Bienvenido", Snackbar.LENGTH_SHORT).show();
                            i = new Intent(getApplicationContext(),ChatActivity.class);
                            startActivity(i);
                        }

                    }
                });
    }




}
