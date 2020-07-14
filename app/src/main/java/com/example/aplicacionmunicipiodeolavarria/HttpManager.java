package com.example.aplicacionmunicipiodeolavarria;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {

    //que reciba si es get, post y tener resuelta esa parte
    public String consultarPersona(String urlString) throws MalformedURLException {
        try {
            Log.d("entro a", "consultarPErsona");
            Log.d("quiero entrar a", urlString);
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET"); //"POST "PUT" "DELETE";
            urlConnection.connect();
            int response = urlConnection.getResponseCode(); //la respuesta del servidorr (el codigo)
            Log.d("la response es", "es" + response);
            if (response == 200) {
                //entra al servidor
                InputStream is = urlConnection.getInputStream(); //agarramos el archivo de a
                // bytes para que se pueda usar de a mucha gente al mimso tiempo
                ByteArrayOutputStream bais = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int cantidadByte = 0;
                while ((cantidadByte = is.read(buffer)) != -1) {
                    bais.write(buffer, 0, cantidadByte);
                }
                is.close();
                Log.d("Respuesta String", bais.toString());
                return bais.toString();

            }
            else {
                Log.d("Respuesta String", "Se metio en Excepction");
                throw new IOException();
            }

        } catch (MalformedURLException ex) {
            Log.d("Respuesta String", "Se metio en Excepction" + ex);
            ex.printStackTrace();
        } catch (IOException e) {
            Log.d("Respuesta String", "Se metio en Excepction" + e);
            e.printStackTrace();
        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
        return null;
    }

    public byte[] consultarImg(String urlString) throws MalformedURLException {
        try {
            Log.d("entro a", "consultarPErsona");
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET"); //"POST "PUT" "DELETE";
            urlConnection.connect();
            int response = urlConnection.getResponseCode(); //la respuesta del servidorr (el codigo)
            if (response == 200) {
                //Log.d("entro a","if de response");
                //entra al servidor
                InputStream is = urlConnection.getInputStream(); //agarramos el archivo de a
                // bytes para que se pueda usar de a mucha gente al mimso tiempo
                ByteArrayOutputStream bais = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int cantidadByte = 0;
                while ((cantidadByte = is.read(buffer)) != -1) {
                    bais.write(buffer, 0, cantidadByte);
                }
                is.close();
                //Log.d("Respuesta String is", bais.toString());
                return bais.toByteArray();

            } else {
                throw new IOException();
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String subirPersona(Uri.Builder param, String urlString) throws IOException {
        // Log.d("aviso", "subirPersona: entro al subir persona del http manager");
        Log.d("llega la persona", param.toString());
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST"); //"POST "PUT" "DELETE";
        urlConnection.setDoOutput(true);
        String query = param.build().getEncodedQuery();
        OutputStream os = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

        writer.write(query);
        writer.flush();
        writer.close();
        os.close();
        int response = urlConnection.getResponseCode();
        if (response == 200) {
            InputStream is = urlConnection.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream bais = new ByteArrayOutputStream();
            int cantidaByte = 0;

            while ((cantidaByte = is.read(buffer)) != -1) {
                bais.write(buffer, 0, cantidaByte);
            }
            is.close();
            return bais.toString();
        } else {
            throw new IOException();
        }
        //urlConnection.connect();
    }
}