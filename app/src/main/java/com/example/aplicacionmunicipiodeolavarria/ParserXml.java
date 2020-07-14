package com.example.aplicacionmunicipiodeolavarria;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ParserXml {
    public static List<Terminales> parserTerminales(String xmlText){
        List<Terminales> terminales = new ArrayList<Terminales>();
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(new StringReader(xmlText));
           int evento = parser.getEventType();
           //Log.d("el int de evento es" ,"" + evento);
            Terminales t  = null;
            boolean flag = false;
           while(evento!=XmlPullParser.END_DOCUMENT) {


               switch (evento) {
                   case XmlPullParser.START_TAG:
                       // parser.getName();
                       if (parser.getName().equals("row")) {
                           if(!flag){
                               flag = true;
                               break;
                           }
                          // Log.d("hay row","entro a un row");
                           //evento = parser.next();
                           //Log.d("el X es",parser.nextText());
                           t = new Terminales();
                           //Log.d("entro aca!","entro de nuevo");
                           parser.next(); //X
                           parser.nextText(); //????????????
                           parser.next(); //Y
                           parser.nextText(); //???????? SIEMPRE AHCER NEXT TEXT AUNQUE NO USE
                           parser.next(); //entidad
                           t.setEntidad(parser.nextText());
                           parser.next() ; //nombre
                           t.setNombre(parser.nextText());
                           parser.next();//calle
                           t.setCalle(parser.nextText());
                           parser.next();//altura calle
                           t.setAlturaCalle(parser.nextText());
                           //Log.d("el parse altura",parser.nextText());
                           parser.next();//partido
                           parser.nextText();
                           parser.next(); //provincia
                           parser.nextText();
                           parser.next();//localidad
                           t.setLocalidad(parser.nextText());
                           parser.next();//latitud
                           t.setLatitud(parser.nextText());
                           parser.next(); //longitud
                           t.setLongitud(parser.nextText());
                           parser.next(); //habilitado
                           parser.nextText();
                           parser.next(); //observacion
                           t.setObservacion(parser.nextText());
                           // t.setLatitud( Double.parseDouble(parser.nextText()));
                           //Log.d("el parse latitud",parser.nextText());
                           //Log.d("el parse localidad",parser.nextText());
                           //t.setLocalidad(parser.nextText());

                               //t.setEntidad(parser.nextText());
                               //t.setNombre(parser.nextText());


                       }
                       break;
                   case XmlPullParser.END_TAG:
                       if (parser.getName().equals("row") && t != null) {
                           terminales.add(t);
                       }
                       break;
               }
               evento = parser.next();
           }

    } catch (XmlPullParserException e) {
            Log.d("slago aca ","salgo por excepcion" + e);
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("slago aca ","salgo por excepcion" + e);
            e.printStackTrace();
        }
        return terminales;
    }
}
