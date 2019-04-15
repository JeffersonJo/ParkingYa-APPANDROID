package com.example.jeffe.login;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class VistaUsuarioRegistradoMain extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario_registrado_main);

        TextView textView_NombreRegis = (TextView) findViewById(R.id.textView_NombreRegis);
        TextView textView_UsuarioRegis = (TextView) findViewById(R.id.txtV_email_user);
        TextView textView_PasswordRegis = (TextView) findViewById(R.id.textView_PasswordRegis);
        TextView textView_EdadRegis = (TextView) findViewById(R.id.textView_EdadRegis);
        TextView textView_addressRegis = (TextView) findViewById(R.id.textView_addressRegis);
        TextView txtV_tel_reg_user = (TextView) findViewById(R.id.txtV_tel_reg_user);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String usuario = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        //cambiar a string - agregar ciudad??
        //int age= intent.getIntExtra("age", -1);
        String age = intent.getStringExtra("age");
        String address = intent.getStringExtra("address");
        String phone = intent.getStringExtra("phone");


        textView_NombreRegis.setText(name);
        textView_UsuarioRegis.setText(usuario);
        textView_PasswordRegis.setText(password);
        //cambiar a string
        textView_EdadRegis.setText(age);
        textView_addressRegis.setText(address);
        txtV_tel_reg_user.setText(phone);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentTest()).commit();


    }

}




        /**bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = new Intent(VistaUsuarioRegistradoMain.this, LoginMain.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });*/


        //setupActionBar();


//flecha back con barra invisible
    /**private void setupActionBar() {
        ActionBar actionBar= getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }*/
