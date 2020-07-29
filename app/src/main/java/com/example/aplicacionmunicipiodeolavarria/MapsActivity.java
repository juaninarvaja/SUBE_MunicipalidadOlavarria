package com.example.aplicacionmunicipiodeolavarria;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    Double longitudeGPS;
    Double latitudeGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Log.d("aca", "esta la locacion");
        Log.d("location obteni", "es" + locationManager.toString());

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng olavarria = new LatLng(-36.8927193, -60.3225403);
        // mMap.addMarker(new MarkerOptions().position(olavarria).title("Marker in Olavarria"));

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(12);

        //String nuevalinea = System.getProperty("line.separator");
        if (MainActivity.verTodas) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(olavarria));
            for (Terminales ter : MainActivity.terminales) {
                marcarEnMapa(ter.getLatitud(), ter.getLongitud(), ter.getNombre(),
                        ter.getCalle() + " " + ter.getAlturaCalle() + " " + ter.getObservacion());
            }
        } else if (MainActivity.TermClickeada != null) {
            // mMap.setMinZoomPreference(12);
            LatLng seleccionada = new LatLng(Double.parseDouble(MainActivity.TermClickeada.getLatitud()), Double.parseDouble(MainActivity.TermClickeada.getLongitud()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(seleccionada, 15));
            marcarEnMapa(MainActivity.TermClickeada.getLatitud(),
                    MainActivity.TermClickeada.getLongitud(), MainActivity.TermClickeada.getNombre(),
                    MainActivity.TermClickeada.getCalle() + " " + MainActivity.TermClickeada.getAlturaCalle()
                            + " " + MainActivity.TermClickeada.getObservacion());
        }

    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            //editLocation.setText("");
           // pb.setVisibility(View.INVISIBLE);
            Toast.makeText(
                    getBaseContext(),
                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " + loc.getLongitude();
            Log.v("longitud", longitude);
            String latitude = "Latitude: " + loc.getLatitude();
            Log.v("latitud", latitude);

            /*------- To get city name from coordinates -------- */
            String cityName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(loc.getLatitude(),
                        loc.getLongitude(), 1);
                if (addresses.size() > 0) {
                    System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                    + cityName;
            Log.d("city",s);
            //editLocation.setText(s);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(
                    getBaseContext(),
                    "On provider Disabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(
                    getBaseContext(),
                    "On providerEnabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(
                    getBaseContext(),
                    "On Estatus changed", Toast.LENGTH_SHORT).show();
        }
    }
    public void marcarEnMapa(String latitud, String longitud, String nombre,String direccion){
        if(mMap != null){
            LatLng aux = new LatLng(Double.parseDouble(latitud),Double.parseDouble(longitud));
            mMap.addMarker(new MarkerOptions().position(aux).title(nombre).snippet(direccion));
        }

    }








    public boolean onMyLocationButtonClick() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }else {
//            Toast.makeText(this, "EL location manager" + LocationManager.GPS_PROVIDER, Toast.LENGTH_SHORT).show();
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        //Log.d("clickeo","click en el boton de locacion");
        return false;
    }

}
