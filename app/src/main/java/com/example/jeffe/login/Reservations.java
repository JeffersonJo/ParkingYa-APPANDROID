package com.example.jeffe.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class Reservations extends AppCompatActivity implements View.OnClickListener {

    EditText placa_usuario_a_reservar, horas_a_reservar;

    TextView nombre_park_a_reservar;

    Button btn_reg_reserv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        nombre_park_a_reservar = (TextView) findViewById(R.id.nombre_park_a_reservar);
        placa_usuario_a_reservar = (EditText) findViewById(R.id.placa_usuario_a_reservar);
        horas_a_reservar = (EditText) findViewById(R.id.horas_a_reservar);

        //Obtener el nombre del parqueadero
        Intent intent = getIntent();
        String name_park = intent.getStringExtra("name_park");

        nombre_park_a_reservar.setText(name_park);

        btn_reg_reserv = findViewById(R.id.btn_reg_reserv);
        btn_reg_reserv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final String name_park = nombre_park_a_reservar.getText().toString();
        final String license_user = placa_usuario_a_reservar.getText().toString();
        final String hours_reserv = horas_a_reservar.getText().toString();

        Response.Listener<String> respoListerner = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    boolean success = jsonresponse.getBoolean("success");

                    if (success) {
                        Intent intent = new Intent(Reservations.this, ParkingMapsActivity.class);
                        final ProgressDialog progressDialog = new ProgressDialog(Reservations.this);
                        progressDialog.setMessage("Creando Reserva...");
                        progressDialog.setIndeterminate(false);
                        progressDialog.dismiss();
                        progressDialog.show();
                        Toasty.success(Reservations.this, "Rerservado con exito !", Toast.LENGTH_SHORT, true).show();
                        Reservations.this.startActivity(intent);

                        //Toasty.success(Reservations.this, "Rerservado con exito !", Toast.LENGTH_SHORT, true).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Reservations.this);
                        builder.setMessage("Error al reservar")
                                .setNegativeButton("Reintentar", null)
                                .create().show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        ReservationsRequest reservationsRequest = new ReservationsRequest(name_park, license_user, hours_reserv, respoListerner);
        RequestQueue queue = Volley.newRequestQueue(Reservations.this);
        queue.add(reservationsRequest);
    }
}
