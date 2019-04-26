package com.example.jeffe.login;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class VistaUsuarioRegistradoMain extends AppCompatActivity {

    private RelativeLayout RelativeLayoutUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario_registrado_main);

        RelativeLayoutUser= findViewById(R.id.RelativeLayoutUser);

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

        BottomNavigationView navigationActivity= (BottomNavigationView) findViewById(R.id.user_page_navigation);
        navigationActivity.setOnNavigationItemSelectedListener(OpenActivity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.user_page_navigation);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentTest()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener OpenActivity=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            Snackbar snackbar;
                            snackbar= Snackbar.make(RelativeLayoutUser,"Seguro que quiere salir?",Snackbar.LENGTH_LONG);
                            View snackBarView = snackbar.getView();
                            snackbar.setAction("SI", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //Salir de la app
                                    /**Intent intentExit = new Intent(Intent.ACTION_MAIN);
                                    intentExit.addCategory(Intent.CATEGORY_HOME);
                                    intentExit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentExit);*/
                                    Intent intentExit = new Intent(VistaUsuarioRegistradoMain.this, LoginMain.class);
                                    startActivity(intentExit);
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                }
                            })
                                    .show();
                            //ACTION
                            snackbar.setActionTextColor(Color.WHITE);
                            //BACKGROUND
                            snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                            //TEXT
                            TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);

                            break;
                        case R.id.nav_map:
                            Intent intentMap = new Intent(VistaUsuarioRegistradoMain.this, ParkingMapsActivity.class);
                            startActivity(intentMap);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            break;
                        case R.id.nav_user:
                            Intent intentUser = new Intent(VistaUsuarioRegistradoMain.this, VistaUsuarioRegistradoMain.class);
                            startActivity(intentUser);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                            break;
                    }
                    return true;
                }
            };


    /**@Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/

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
