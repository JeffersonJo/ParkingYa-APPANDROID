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
    private TextView t1_name, t2_email, t3_pass, t4_age, t5_address, t6_tel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_usuario_registrado_main);

        RelativeLayoutUser= findViewById(R.id.RelativeLayoutUser);

         t1_name = (TextView) findViewById(R.id.textView_NombreRegis);
         t2_email = (TextView) findViewById(R.id.txtV_email_user);
         t3_pass = (TextView) findViewById(R.id.textView_PasswordRegis);
         t4_age = (TextView) findViewById(R.id.textView_EdadRegis);
         t5_address = (TextView) findViewById(R.id.textView_addressRegis);
         t6_tel = (TextView) findViewById(R.id.txtV_tel_reg_user);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        //cambiar a string - agregar ciudad??
        //int age= intent.getIntExtra("age", -1);
        String age = intent.getStringExtra("age");
        String address = intent.getStringExtra("address");
        String phone = intent.getStringExtra("phone");


        t1_name.setText(name);
        t2_email.setText(email);
        t3_pass.setText(password);
        //cambiar a string
        t4_age.setText(age);
        t5_address.setText(address);
        t6_tel.setText(phone);

        BottomNavigationView navigationActivity= (BottomNavigationView) findViewById(R.id.user_page_navigation);
        navigationActivity.setOnNavigationItemSelectedListener(OpenActivity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.user_page_navigation);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentTest()).commit();


    }

    /**@Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        String txt1_name = t1_name.getText().toString();
        String txt2_email = t2_email.getText().toString();
        String txt3_pass = t3_pass.getText().toString();
        String txt4_age  = t4_age.getText().toString();
        String txt5_address = t5_address.getText().toString();
        String txt6_tel  = t6_tel.getText().toString();

        outState.putString("nameSaved", txt1_name);
        outState.putString("emailSaved", txt2_email);
        outState.putString("passwordSaved", txt3_pass);
        outState.putString("ageSaved", txt4_age);
        outState.putString("addressSaved", txt5_address);
        outState.putString("phoneSaved", txt6_tel);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        String txtV1_name = savedInstanceState.getString("nameSaved");
        String txtV2_email = savedInstanceState.getString("emailSaved");
        String txtV3_pass = savedInstanceState.getString("passwordSaved");
        String txtV4_age = savedInstanceState.getString("ageSaved");
        String txtV5_address = savedInstanceState.getString("addressSaved");
        String txtV6_tel= savedInstanceState.getString("phoneSaved");

        t1_name.setText(txtV1_name);
        t2_email.setText(txtV2_email);
        t3_pass.setText(txtV3_pass);
        t4_age.setText(txtV4_age);
        t5_address.setText(txtV5_address);
        t6_tel.setText(txtV6_tel);
    }*/



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
    }

    /**@Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }*/





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
