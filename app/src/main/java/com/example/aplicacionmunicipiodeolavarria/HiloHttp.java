package com.example.aplicacionmunicipiodeolavarria;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class HiloHttp extends Thread {
    Handler handler;
    String url;
    boolean texto = true;
    int pos;
    String peticion;
    Uri.Builder parametros;
    List<Terminales> terminales = new ArrayList<>();

    public HiloHttp(Handler handler,String peticion,String url,Boolean texto, int i){
        this.handler = handler;
        this.url = url;
        this.texto = texto;
        this.pos = i;
        this.peticion =peticion;
    }
    public HiloHttp(Handler handler,String peticion,String url,Boolean texto,Uri.Builder parametros){
        this.handler = handler;
        this.url = url;
        this.texto = texto;
        this.peticion =peticion;
        this.parametros = parametros;
    }
    @Override
    public void run(){
        HttpManager manager = new HttpManager();

        try {
            //Thread.sleep(3000);
            if(texto && this.peticion == "GET"){
                //Log.d("estoy en","run");
                String respuesta = null;
                respuesta = manager.consultarPersona(this.url);
                if(respuesta != null){
                    Log.d("estoy en","respuesta del mensaje" + respuesta);
                    terminales = ParserXml.parserTerminales(respuesta);
                    Log.d("Array ","" + terminales.size());
                    for(Terminales ter : terminales){
                        //ter.toString();
                     // Log.d("nombre",ter.getNombre());
                      //Log.d("entidad",ter.getEntidad());
                      Log.d("ter", ter.toString());
                    }
                    Message message = new Message();
                    message.arg1=MainActivity.TEXTO;
                    //message.obj = respuesta;
                 //   List<Terminales> auxTerminales = new ArrayList<>();
                   // JSONArray array = new JSONArray(respuesta.toString());
                   // for(int i = 0; i<array.length(); i++){

//                    JSONObject object = array.getJSONObject(i);
//                    auxEmpresas.add(new Empresa(object.getString("nombre"),
//                            object.getString("apellido"),object.getString("telefono"),
//                            object.getString("img")));
                //    }
                    message.obj = terminales;
                    this.handler.sendMessage(message);
                }
                else{
                    Log.d("LA respuesta", "enel hiloHttp en  ---- NULL!! ---");
                }

            }
            else if(this.peticion =="GET"){
                Log.d("entra al hiloHttp","entro para trabajar en una imagen");
                byte[] imagen= manager.consultarImg(this.url);
                Message message = new Message();
                message.obj = imagen;
                message.arg1=MainActivity.IMAGEN;
                message.arg2 = pos;

                this.handler.sendMessage(message);
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
    }
}