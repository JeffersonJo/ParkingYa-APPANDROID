package com.example.jeffe.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class ExampleDialog extends AppCompatDialogFragment {

    private EditText editTextUsername;
    private EditText editTextPassword;

    private TextView horas_a_reservar;

    //private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("RESERVAS PARKING YA!")
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                })
                .setPositiveButton("reservar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                       final String name_park = editTextUsername.getText().toString();
                       final String license_user = editTextPassword.getText().toString();

                        final String hours_reserv = horas_a_reservar.getText().toString();
                        //listener.applyTexts(username, password);

                        Response.Listener<String> respoListerner = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonresponse = new JSONObject(response);
                                    boolean success = jsonresponse.getBoolean("success");

                                    if (success) {
                                        Intent intent = new Intent(Reservations.this, ParkingMapsActivity.class);
                                        final ProgressDialog progressDialog = new ProgressDialog(ParkingMapsActivity.this);
                                        progressDialog.setMessage("Creando Reserva...");
                                        progressDialog.setIndeterminate(false);
                                        progressDialog.dismiss();
                                        progressDialog.show();
                                        Toasty.success(Reservations.this, "Rerservado con exito !", Toast.LENGTH_SHORT, true).show();
                                        Reservations.this.startActivity(intent);

                                        sendTestEmail();

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
                });

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        horas_a_reservar = view.findViewById(R.id.horas_a_reservar);

        return builder.create();
    }

    private void sendTestEmail() {

        BackgroundMail.newBuilder(this)
                .withUsername("noreplyparkingya@gmail.com")
                .withPassword("park100891")
                .withMailto("jeffersonj-o@hotmail.com")
                .withSubject("CONFIRMACION DE RESERVA")
                .withBody("Reserva para el vehiculo con numero de placa "
                        +editTextUsername.getText().toString()
                        + " su codigo de verficacion es #215")

                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                    }
                })
                .send();
    }

    /**@Override
    public void onAttach(Context context) {
    super.onAttach(context);

    try {
    listener = (ExampleDialogListener) context;
    } catch (ClassCastException e) {
    throw new ClassCastException(context.toString() +
    "must implement ExampleDialogListener");
    }
    }

    public interface ExampleDialogListener {
    void applyTexts(String username, String password);
    }*/

}