package com.example.jeffe.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginMain extends AppCompatActivity {

    EditText txtInputEmail, txtInputContraseña;

    TextView textView_RegisterLogin;
    Button btn_Enter;
    ProgressDialog pdialog = null;

    //Crea acceso directo
    /**Context mContext= LoginMain.this;
    SharedPreferences appPreferences;
    boolean isAppInstalled= false;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        txtInputEmail= findViewById(R.id.editText_LoginName);
        txtInputContraseña= findViewById(R.id.editText_LoginPass);


        textView_RegisterLogin= (TextView)findViewById(R.id.textView_RegisterLogin);
        btn_Enter= findViewById(R.id.btn_Enter);

        //Crear acceso directo
        /**appPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        isAppInstalled= appPreferences.getBoolean("AppInstalada", false);
        if(isAppInstalled == false){ /*

        /**Intent shortcutIntent = new Intent(getApplicationContext(),LoginMain.class);
        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "ParkingYa!");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher_parkingya_op_round));

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);

        SharedPreferences.Editor editor= appPreferences.edit();
        editor.putBoolean("AppInstalada", true);
        editor.commit();
        } */

    }

    private boolean validateEmail(){
        String nombreInput= txtInputEmail.getText().toString().trim();

        if(nombreInput.isEmpty()){
            txtInputEmail.setError("Este campo no puede estar vacio.");
            return false;
        }else{
            txtInputEmail.setError(null);
            return true;
        }
    }


    /**private boolean validateEmail(){
        String nombreInput= txtInputUsuario.getEditText().getText().toString().trim();

        if(nombreInput.isEmpty()){
            txtInputUsuario.setError("El campo no puede estar vacio.");
            return false;
        }else if (nombreInput.length() > 15){
            txtInputUsuario.setError("El nombre de usuario es muy largo.");
            return false;
        }else{
            txtInputUsuario.setError(null);
            return true;
        }
    } */

    private boolean validateContraseña(){
        String contraseñaInput= txtInputContraseña.getText().toString().trim();

        if(contraseñaInput.isEmpty()){
            txtInputContraseña.setError("Este campo no puede estar vacio.");
            return false;
        }else{
            txtInputContraseña.setError(null);
            return true;
        }
    }



    public void ingresarLogin(View view){

        final String email= txtInputEmail.getText().toString();
        final String password= txtInputContraseña.getText().toString();

        Response.Listener<String> responseListener= new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse= new JSONObject(response);
                    boolean success= jsonResponse.getBoolean("success");
                    if(success){
                        String name= jsonResponse.getString("name");
                        String age= jsonResponse.getString("age");
                        //int age= jsonResponse.getInt("age");
                        String address= jsonResponse.getString("address");
                        String phone= jsonResponse.getString("phone");

                        //Enviar datos a otro activity
                        Intent intent= new Intent(LoginMain.this, VistaUsuarioRegistradoMain.class);
                        intent.putExtra("name", name);
                        intent.putExtra("email", email);
                        intent.putExtra("age", age);
                        intent.putExtra("password", password);
                        intent.putExtra("address", address);
                        intent.putExtra("phone", phone);


                        final ProgressDialog progressDialog = new ProgressDialog(LoginMain.this);
                        progressDialog.setMessage("Buscando parqueaderos...");
                        progressDialog.setIndeterminate(false);
                        progressDialog.dismiss();
                        progressDialog.show();

                        LoginMain.this.startActivity(intent);

                    }else{
                        AlertDialog.Builder builder= new AlertDialog.Builder(LoginMain.this);
                        builder.setMessage("Ups! credencial no registrada, por favor verifique su credencial.")
                                .setNegativeButton("Reintentar",null)
                                .create().show();

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest= new LoginRequest(email, password, responseListener);
        RequestQueue queue= Volley.newRequestQueue(LoginMain.this);
        queue.add(loginRequest);


       if(!validateEmail() | !validateContraseña()){
            return;
        }
    }


    public void onClick(View view) {

        Intent intentRegistro= new Intent(LoginMain.this, RegistroMain.class);
        LoginMain.this.startActivity(intentRegistro);

    }

}

