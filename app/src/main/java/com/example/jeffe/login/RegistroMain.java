package com.example.jeffe.login;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class RegistroMain extends AppCompatActivity implements View.OnClickListener {


    DatePickerDialog datePickerDialog;
    Calendar c;

    EditText nombre_a_registro, correo_a_registro,
             contraseña_a_registro, edad_a_registro,
             direccion_a_registro, tel_a_registro;

    Button btn_RegistUser;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_main);

        nombre_a_registro=(EditText)findViewById(R.id.editText_RegNombre);
        correo_a_registro=(EditText)findViewById(R.id.editText_RegCorreo);
        contraseña_a_registro=(EditText)findViewById(R.id.editText_RegConstraseña);
        edad_a_registro=(EditText)findViewById(R.id.editText_RegEdad);
        direccion_a_registro=(EditText)findViewById(R.id.editText_RegDireccion);
        tel_a_registro=(EditText)findViewById(R.id.editText_RegTel);

        btn_RegistUser=findViewById(R.id.btn_RegistUser);
        btn_RegistUser.setOnClickListener(this);

        //Accion calendario
        edad_a_registro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                c= Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int ano = c.get(Calendar.YEAR);

                datePickerDialog= new DatePickerDialog(RegistroMain.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                        edad_a_registro.setText(mDay + "/" + (mMonth+1)+"/"+mYear);

                    }
                }, ano, mes, dia);
                datePickerDialog.show();

            }
        });

    }

    private boolean validateName() {
        String nombreInput = nombre_a_registro.getText().toString().trim();

        if (nombreInput.isEmpty()) {
            nombre_a_registro.setError("Este campo no puede estar vacio.");
            return false;
        } else {
            nombre_a_registro.setError(null);
            return true;

        }

    }


            //| correoInput.isEmpty() | passInput.isEmpty() | edadInput.isEmpty() |
           //direccionInput.isEmpty() | telInput.isEmpty()){

         /**   correo_a_registro.setError("Este campo no puede estar vacio.");
            contraseña_a_registro.setError("Este campo no puede estar vacio.");
            edad_a_registro.setError("Este campo no puede estar vacio.");
            direccion_a_registro.setError("Este campo no puede estar vacio.");
            tel_a_registro.setError("Este campo no puede estar vacio.");


            correo_a_registro.setError(null);
            contraseña_a_registro.setError(null);
            edad_a_registro.setError(null);
            direccion_a_registro.setError(null);
            tel_a_registro.setError(null);
            return true;*/

    @Override
    public void onClick(View v) {

        final String name= nombre_a_registro.getText().toString();
        final String email= correo_a_registro.getText().toString();
        final String password= contraseña_a_registro.getText().toString();
        //final int age= Integer.parseInt(editText_RegistEdad.getText().toString());
        final String age= edad_a_registro.getText().toString();
        final String address= direccion_a_registro.getText().toString();
        final String phone= tel_a_registro.getText().toString();

        Response.Listener<String>respoListerner= new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse= new JSONObject(response);
                    boolean success= jsonresponse.getBoolean("success");

                    if(success){
                        Intent intent= new Intent(RegistroMain.this,LoginMain.class);
                        RegistroMain.this.startActivity(intent);
                        Toasty.success(RegistroMain.this, "Registrado con exito !", Toast.LENGTH_SHORT, true).show();
                    }else{
                        AlertDialog.Builder builder= new AlertDialog.Builder(RegistroMain.this);
                        builder.setMessage("Error al registrar")
                                .setNegativeButton("Reintentar",null)
                                .create().show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        if(!validateName()){
            return;
        }

        RegistroRequest registroRequest= new RegistroRequest(name, email, age, password, address, phone, respoListerner);
        RequestQueue queue= Volley.newRequestQueue(RegistroMain.this);
        queue.add(registroRequest);

    }
}
