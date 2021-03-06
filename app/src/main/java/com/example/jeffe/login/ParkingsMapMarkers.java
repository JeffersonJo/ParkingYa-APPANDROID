package com.example.jeffe.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class ParkingsMapMarkers extends AppCompatActivity implements OnMapReadyCallback,
                                                                      GoogleMap.OnInfoWindowClickListener,
                                                                      GoogleMap.OnMyLocationButtonClickListener,
                                                                      GoogleMap.OnMyLocationClickListener,
                                                                      ActivityCompat.OnRequestPermissionsResultCallback{

    //Generar marcadores
    private static final LatLng PARKING_EL_PRADO = new LatLng(11.003199799999999, -74.8016502);

    private static final LatLng PARKING_BUENAVISTA = new LatLng(11.013759466883691,-74.82715186779973);

    private static final LatLng PARKING_CENTRO_HISTORICO = new LatLng(10.985344995037604,-74.77950669690227);

    private static final LatLng PARKING_PASEO_BOLIVAR = new LatLng(10.979223530649021,-74.7792902873071);

    private static final LatLng PARKING_51B = new LatLng(11.008818255553496,-74.82293519239744);


    private GoogleMap mMap;
    private Marker pPrado;
    private Marker pBuenavista;
    private Marker pCentro;
    private Marker pPaseoBolivar;
    private Marker p51B;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;
    private RelativeLayout relativeLayout;

    public ParkingsMapMarkers(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_maps_markers);

        relativeLayout =findViewById(R.id.capaRelativL);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationView navigationActivity= (BottomNavigationView) findViewById(R.id.navigation);
        navigationActivity.setOnNavigationItemSelectedListener(AbrirActivity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
     //Metodo para abrir activitis no fragments
    private BottomNavigationView.OnNavigationItemSelectedListener AbrirActivity=
             new BottomNavigationView.OnNavigationItemSelectedListener() {
                 @Override
                 public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                     switch (menuItem.getItemId()) {
                         case R.id.nav_home:
                             Snackbar snackbar;
                             snackbar= Snackbar.make(relativeLayout,"Seguro que quiere salir?",Snackbar.LENGTH_LONG);
                             View snackBarView = snackbar.getView();
                             snackbar.setAction("SI", new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent intent = new Intent(Intent.ACTION_MAIN);
                                             intent.addCategory(Intent.CATEGORY_HOME);
                                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                             startActivity(intent);
                                             overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                                             Intent intentExit = new Intent(ParkingsMapMarkers.this, LoginMain.class);
                                             startActivity(intentExit);
                                         }
                                     });

                             //ACTION
                             snackbar.setActionTextColor(Color.WHITE);
                             //BACKGROUND
                             snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                             //TEXT
                             TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                             textView.setTextColor(Color.WHITE);

                             snackbar.show();

                             break;
                         case R.id.nav_map:
                             Intent intentMap = new Intent(ParkingsMapMarkers.this, ParkingsMapMarkers.class);
                             startActivity(intentMap);
                             break;
                         case R.id.nav_user:
                             Intent intentUser = new Intent(ParkingsMapMarkers.this, UserRegistredMain.class);
                             startActivity(intentUser);
                             overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                             break;
                     }
                     return true;
                 }
             };

    //Metodo para abrir fragments no activitys
    /**private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFrag= null;

            switch (item.getItemId()) {
                case R.id.nav_home:

                    break;
                case R.id.nav_map:
                    //Intent intentMap = new Intent(ParkingsMapMarkers.this, ParkingsMapMarkers.class);
                    //startActivity(intentMap);
                    break;
                case R.id.nav_user:
                    selectedFrag= new FragmentPerfil();
                    //Intent intent = new Intent(ParkingsMapMarkers.this, UserRegistredMain.class);
                    //startActivity(intent);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();

            return true;
        }
    };*/

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(PARKING_EL_PRADO)
                .include(PARKING_BUENAVISTA)
                .include(PARKING_CENTRO_HISTORICO)
                .include(PARKING_PASEO_BOLIVAR)
                .include(PARKING_51B)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        addMarkersToMap();

        // Add a marker, and move the camera
        /**LatLng ParkPrado = new LatLng(11.003199799999999, -74.8016502);
        mMap.addMarker(new MarkerOptions().position(ParkPrado).title("Parking Ya! - EL PRADO").snippet("Horario de atención: 6:00 am - 10:00 pm"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ParkPrado, 12));

        LatLng soledad = new LatLng(10.9198196994703, -74.80710044871824);
        mMap.addMarker(new MarkerOptions().position(soledad).title("Parking Ya! - BUENAVISTA").snippet("Horario de atención: 6:00 am - 10:00 pm"));*/

        //Control de zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //Click en infoWindow
        mMap.setOnInfoWindowClickListener((GoogleMap.OnInfoWindowClickListener) this);
    }

    private void addMarkersToMap() {

        pPrado = mMap.addMarker(new MarkerOptions()
                .position(PARKING_EL_PRADO)
                .title("Parking Ya! - EL PRADO")
                .snippet("Horario de atención: 6:00 am - 10:00 pm"));

        pBuenavista = mMap.addMarker(new MarkerOptions()
                .position(PARKING_BUENAVISTA)
                .title("Parking Ya! - BUENAVISTA")
                .snippet("Horario de atención: 6:00 am - 10:00 pm"));

        pCentro = mMap.addMarker(new MarkerOptions()
                .position(PARKING_CENTRO_HISTORICO)
                .title("Parking Ya! - CENTRO HISTORICO")
                .snippet("Horario de atención: 6:00 am - 08:00 pm"));

        pPaseoBolivar = mMap.addMarker(new MarkerOptions()
                .position(PARKING_PASEO_BOLIVAR)
                .title("Parking Ya! - PASEO BOLIVAR")
                .snippet("Horario de atención: 6:00 am - 08:00 pm"));

        p51B = mMap.addMarker(new MarkerOptions()
                .position(PARKING_51B)
                .title("Parking Ya! - 51B")
                .snippet("Horario de atención: 6:00 am - 10:00 pm"));

    }


    //Control encontrar mi ubicacion
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            // Deshabilitar boton de localizar ubicacion
            //mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }



    @Override
    public void onInfoWindowClick(final Marker marker) {

               /** final String name_park= pPrado.getTitle();
        //Enviar nombre del parqueadero
        Intent intentReserv = new Intent(ParkingsMapMarkers.this, ReservationsMain.class);
        intentReserv.putExtra("name_park", name_park);
        AbirDialogAlert();
        startActivity(intentReserv);*/

                Snackbar snackbar;
                snackbar = Snackbar.make(relativeLayout, "Esta seguro que desea reservar aqui?", Snackbar.LENGTH_LONG);
                View snackBarView = snackbar.getView();
                snackbar.setAction("Reservar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /**Snackbar snackbarOne = Snackbar.make(relativeLayout, "Reservado !", Snackbar.LENGTH_SHORT);
                        snackbarOne.setActionTextColor(Color.WHITE);
                        View snackBarOneView = snackbarOne.getView();
                        snackBarOneView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        snackbarOne.show();*/

                        AbirDialogAlert();

                    }
                });
                //ACTION
                snackbar.setActionTextColor(Color.WHITE);
                //BACKGROUND
                snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                //TEXT
                TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);

                snackbar.show();

                //Toast.makeText(ParkingsMapMarkers.this, "Todo bien, todo bien", Toast.LENGTH_SHORT).show();
                //Snackbar.make(relativeLayout,"Reserva Confirmada !", R.color.colorPrimaryDark Snackbar.LENGTH_LONG).show();

                //Intent intentFragment= new Intent(MapsActivity.this, ReservaActivity.class);
                //MapsActivity.this.startActivity(intentFragment);

        }

    private void AbirDialogAlert() {

        DialogReservMain dialogReserv = new DialogReservMain();
        dialogReserv.show(getSupportFragmentManager(), "dialog alert");
    }
}