package com.example.jeffe.login;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class ParkingMapsActivity extends AppCompatActivity implements OnMapReadyCallback,
                                                                      GoogleMap.OnInfoWindowClickListener,
                                                                      GoogleMap.OnMyLocationButtonClickListener,
                                                                      GoogleMap.OnMyLocationClickListener,
                                                                      ActivityCompat.OnRequestPermissionsResultCallback{


    private static final LatLng PARKING_EL_PRADO = new LatLng(11.003199799999999, -74.8016502);

    private static final LatLng PARKING_BUENAVISTA = new LatLng(11.013759466883691,-74.82715186779973);

    private GoogleMap mMap;
    private Marker pPrado;
    private Marker pBuenavista;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    public ParkingMapsActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //BottomNavigationView navigationActivity= (BottomNavigationView) findViewById(R.id.navigation);
        //navigationActivity.setOnNavigationItemSelectedListener(AbrirActivity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
     //Metodo para abrir activitis no fragments
    /**private BottomNavigationView.OnNavigationItemSelectedListener AbrirActivity=
             new BottomNavigationView.OnNavigationItemSelectedListener() {
                 @Override
                 public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                     switch (menuItem.getItemId()) {
                         case R.id.nav_home:
                             Intent intent = new Intent(ParkingMapsActivity.this, VistaUsuarioRegistradoMain.class);
                             startActivity(intent);
                             break;
                         case R.id.nav_map:
                             Intent intentMap = new Intent(ParkingMapsActivity.this, ParkingMapsActivity.class);
                             startActivity(intentMap);
                             break;
                         case R.id.nav_user:
                             //selectedFrag= new FragmentPerfil();

                             break;
                     }
                     return true;
                 }
             };*/

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFrag= null;

            switch (item.getItemId()) {
                case R.id.nav_home:

                    break;
                case R.id.nav_map:
                    //Intent intentMap = new Intent(ParkingMapsActivity.this, ParkingMapsActivity.class);
                    //startActivity(intentMap);
                    break;
                case R.id.nav_user:
                    selectedFrag= new FragmentPerfil();
                    //Intent intent = new Intent(ParkingMapsActivity.this, VistaUsuarioRegistradoMain.class);
                    //startActivity(intent);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();

            return true;
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(PARKING_EL_PRADO)
                .include(PARKING_BUENAVISTA)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        addMarkersToMap();

        //Mostrar y alistar InfoWindow
        //mMap.setInfoWindowAdapter(new CustomInfoWindow(MapsActivity.this));

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
    public void onInfoWindowClick(Marker marker) {

        //Toast.makeText(this, "Info window clicked", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder= new AlertDialog.Builder(ParkingMapsActivity.this);
        //builder.setTitle("");
        builder.setMessage(R.string.reservasDialog);
        builder.setNegativeButton("Cancelar",null);

        builder.setPositiveButton("Reservar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(ParkingMapsActivity.this, "Todo bien, todo bien", Toast.LENGTH_SHORT).show();

                //Intent intentFragment= new Intent(MapsActivity.this, ReservaActivity.class);
                //MapsActivity.this.startActivity(intentFragment);
            }
        });
        builder.show();
    }
}