package com.example.aplicacionmunicipiodeolavarria;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
        HiloHttp miHIlo = new HiloHttp(handler,"GET","https://api.datosabiertos.olavarria.gov.ar/api/v2/datastreams/TERMI-SUBE-DEL-PARTI-DE/data.xml/?auth_key=DZg5bsjkdO3RutJv95NiJgTKhBdxiC5Bq75MXjaU",true,0);
        miHIlo.start(); //no run

        adap = new TerminalAdapter(terminales,this);
        RecyclerView rvPersona = super.findViewById(R.id.rvTerminales);
        rvPersona.setAdapter(adap);
        rvPersona.setLayoutManager(new LinearLayoutManager(this)); //uno abajo del otro

    }
    @Override
    public boolean handleMessage(@NonNull Message message) {
        //recorre y ve si hay mensajes para procesar
        Terminales tAux = null;
        Log.d("Mensaje","Llego el mansjae" + message.obj);
        SharedPreferences preferences = getSharedPreferences("terminal", MODE_PRIVATE);
        String nombre = preferences.getString("terminal", "none"); //el s1 es el valor por defecto
        if (nombre.equals("none")){
            Toast.makeText(this, "El shared es none", Toast.LENGTH_SHORT).show();
        }
        else{

            try {

                tAux = new  Terminales();
                JSONObject jsonAux = new JSONObject(nombre);
                tAux = parserJSONtoTerminal(jsonAux);
                //Toast.makeText(this, "tAux to string: "+ tAux.toString(), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        if(message.arg1 ==TEXTO){
            terminales.clear();
            if(tAux != null){
                terminales.add(tAux);
            }
            terminales.addAll((List<Terminales>) message.obj);
            adap.notifyDataSetChanged();

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
        if (item.getItemId() == R.id.mapa) {
            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
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
        else if(item.getItemId() == R.id.info){
            StringBuilder sb = new StringBuilder();
            sb.append("V 1.0 \n");
            sb.append("Desarrolador : Narvaja, Juan");
            Dialogo miDialogo = new Dialogo(this,"Info",sb.toString());
            miDialogo.show(super.getSupportFragmentManager(), "Confirm");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        verTodas = false;
        Log.d("onResume","estoy en el onresume");
        SharedPreferences preferences = getSharedPreferences("terminal", MODE_PRIVATE);
        String nombre = preferences.getString("terminal", "none"); //el s1 es el valor por defecto
        if (nombre.equals("none")) {
            Toast.makeText(this, "NONE", Toast.LENGTH_SHORT).show();
        }
        else{
            HiloHttp miHIlo = new HiloHttp(handler,"GET","https://api.datosabiertos.olavarria.gov.ar/api/v2/datastreams/TERMI-SUBE-DEL-PARTI-DE/data.xml/?auth_key=DZg5bsjkdO3RutJv95NiJgTKhBdxiC5Bq75MXjaU",true,0);
            miHIlo.start(); //no run
            //Toast.makeText(this, nombre, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(int postion) {
       // Log.d("llamando a", "llamando a " + postion);
        //Log.d("el nombre es" , terminales.get(postion).getNombre());
        SharedPreferences preferences = this.getSharedPreferences("terminal", Context.MODE_PRIVATE);
        //String nombre = preferences.getString("terminal", "none"); //el s1 es el valor por defecto
        SharedPreferences.Editor editor = preferences.edit();
        JSONObject auxjson = parserTerminalToJSON(terminales.get(postion));
        Log.d("el JSON es", auxjson.toString());
        editor.putString("terminal",auxjson.toString());
        editor.commit();
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
            super.startActivity(intent); //arranca la activity
        }
       // Log.d("llamando a", "llamando a "  + personas.get(postion).getApellido());
    }

    public JSONObject parserTerminalToJSON(Terminales t1){
        JSONObject jsonObject= new JSONObject();
        if(t1 != null){
            try {

                jsonObject.put("entidad", t1.getEntidad());
                jsonObject.put("nombre", t1.getNombre());
                jsonObject.put("calle", t1.getCalle());
                jsonObject.put("alturaCalle", t1.getAlturaCalle());
                jsonObject.put("observacion", t1.getObservacion());
                jsonObject.put("localidad", t1.getLocalidad());
                jsonObject.put("latitud", t1.getLatitud());
                jsonObject.put("longitud", t1.getLongitud());
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return jsonObject;
        }
        return null;
    }
    public Terminales parserJSONtoTerminal(JSONObject jsonObject) throws JSONException {
        if(jsonObject != null){
            //Toast.makeText(this, "al parser JSon to terminal llega: "+ jsonObject, Toast.LENGTH_SHORT).show();
            Terminales t1 = new Terminales();
            t1.setEntidad(jsonObject.get("entidad").toString());
            t1.setNombre(jsonObject.get("nombre").toString());
            t1.setCalle(jsonObject.get("calle").toString());
            t1.setAlturaCalle(jsonObject.get("alturaCalle").toString());
            t1.setObservacion(jsonObject.get("observacion").toString());
            t1.setLocalidad(jsonObject.get("localidad").toString());
            t1.setLatitud(jsonObject.get("latitud").toString());
            t1.setLongitud(jsonObject.get("longitud").toString());
            return t1;
        }

        return null;
    }

}
