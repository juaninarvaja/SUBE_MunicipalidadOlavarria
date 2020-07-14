package com.example.aplicacionmunicipiodeolavarria;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        if(MainActivity.verTodas){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(olavarria));
            for(Terminales ter : MainActivity.terminales){
                marcarEnMapa(ter.getLatitud(),ter.getLongitud(),ter.getNombre(),
                        ter.getCalle() + " "+ ter.getAlturaCalle() + " "+ ter.getObservacion());
            }
        }
        else if(MainActivity.TermClickeada != null){
           // mMap.setMinZoomPreference(12);
            LatLng seleccionada = new LatLng(Double.parseDouble(MainActivity.TermClickeada.getLatitud()), Double.parseDouble(MainActivity.TermClickeada.getLongitud()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(seleccionada,15));
            marcarEnMapa(MainActivity.TermClickeada.getLatitud(),
                    MainActivity.TermClickeada.getLongitud(),MainActivity.TermClickeada.getNombre(),
                    MainActivity.TermClickeada.getCalle() + " "+ MainActivity.TermClickeada.getAlturaCalle()
                            + " "+ MainActivity.TermClickeada.getObservacion());
        }

    }
    public void marcarEnMapa(String latitud, String longitud, String nombre,String direccion){
        if(mMap != null){
            LatLng aux = new LatLng(Double.parseDouble(latitud),Double.parseDouble(longitud));
            mMap.addMarker(new MarkerOptions().position(aux).title(nombre).snippet(direccion));
        }

    }
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        //Log.d("clickeo","click en el boton de locacion");
        return false;
    }

}
