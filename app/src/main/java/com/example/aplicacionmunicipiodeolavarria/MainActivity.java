package com.example.aplicacionmunicipiodeolavarria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements MyOnItemClick, Handler.Callback, LifecycleObserver {

    private  int MY_PERMISSION_REQUEST;
    public static List<Terminales> terminales;
    public static boolean verTodas = false;
    public static Terminales TermClickeada = null;
    public static final int TEXTO = 1;
    public static final int IMAGEN = 2;
    //public static
    public Handler handler = new Handler(this);
    public static TerminalAdapter adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        terminales = new ArrayList<>();

        //recibe una clase q implemente callback
        HiloHttp miHIlo = new HiloHttp(handler,"GET","https://api.datosabiertos.olavarria.gov.ar/api/v2/datastreams/TERMI-SUBE-DEL-PARTI-DE/data.xml/?auth_key=DZg5bsjkdO3RutJv95NiJgTKhBdxiC5Bq75MXjaU",true,0);

        //HiloHttp miHIlo = new HiloHttp(handler,"GET","https://api.datosabiertos.olavarria.gov.ar/api/v2/datastreams/EMPRE-HABIL-2019/data.xml/?auth_key=edd4b1b997754f90c7373547e4a88d5ca9b8b2b9",true,0);

        miHIlo.start(); //no run
        //HiloHttp miHiloImagen = new HiloHttp(handler,"http://192.168.0.12:3000/personas",false);
        //miHiloImagen.start();


        adap = new TerminalAdapter(terminales,this);
        RecyclerView rvPersona = super.findViewById(R.id.rvTerminales);
        rvPersona.setAdapter(adap);
        rvPersona.setLayoutManager(new LinearLayoutManager(this)); //uno abajo del otro

    }
    @Override
    public boolean handleMessage(@NonNull Message message) {
        //recorre y ve si hay mensajes para procesar
        Log.d("Mensaje","Llego el mansjae" + message.obj);
        if(message.arg1 ==TEXTO){
            terminales.clear();
            terminales.addAll((List<Terminales>) message.obj);
            adap.notifyDataSetChanged();
//            for(int i = 0; i<terminales.size();i++){
//                HiloHttp miHiloImagen = new HiloHttp(handler,"GET",personas.get(i).getImagen(),false,i);
//                miHiloImagen.start();
//            }

            // message.arg2
            //TextView tv  = super.findViewById(R.id.tvPersonas);
            //tv.setText(message.obj.toString());
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        super.getMenuInflater().inflate(R.menu.menu, m);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.alta) {
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_REQUEST);
                }
            else{
                Log.d("Llego a menu", "Se hizo click en el alta");
                Intent intent = new Intent(this, MapsActivity.class);
                //Intent intent = new Intent(this, FormularioAlta.class);
                //p.edad = 23;
                //p.nombre = "Juan";
                verTodas = true;
                super.startActivity(intent); //arranca la activity
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verTodas = false;

    }

    @Override
    public void onItemClick(int postion) {
       // Log.d("llamando a", "llamando a " + postion);
        //Log.d("el nombre es" , terminales.get(postion).getNombre());
        Log.d("todo", terminales.get(postion).toString());
        TermClickeada = terminales.get(postion);
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST);
        }
        else{
            Log.d("Llego a menu", "Se hizo click en el alta");
            Intent intent = new Intent(this, MapsActivity.class);
            //Intent intent = new Intent(this, FormularioAlta.class);
            //p.edad = 23;
            //p.nombre = "Juan";
            //verTodas = true;
            super.startActivity(intent); //arranca la activity
        }
       // Log.d("llamando a", "llamando a "  + personas.get(postion).getApellido());
    }

}
