package com.example.aplicacionmunicipiodeolavarria;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TerminalAdapter extends RecyclerView.Adapter<TerminalViewHolder> {

    private List<Terminales> terminales;
    private MyOnItemClick listener;
    public TerminalAdapter(List<Terminales> terminales, MyOnItemClick listener ){
        this.terminales = terminales;
        this.listener = listener;
    }
    @Override
    public TerminalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_terminal,parent,false);
        TerminalViewHolder vh = new TerminalViewHolder(view,listener);
        Log.d("On create", "On create Persona");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TerminalViewHolder holder, int position) {
        Terminales e = this.terminales.get(position);
        if(e.getEntidad().equals("LOTERIA PROVINCIAL")){
            holder.tvNombre.setText(e.getEntidad());
           // holder.ivImage.setImageResource(R.d);
        }
        else if(e.getNombre().contains("(TR Argentina)") || e.getNombre().contains("(Tr Argentina)") ){
                //Log.d("hay un TR","hay un TR");
                int ubicacion = e.getNombre().indexOf('(');
                holder.tvNombre.setText(e.getNombre().substring(0,ubicacion));
                //if(partes[0] != null){}

                //holder.tvNombre.setText(partes[0]);
            }
        else if(e.getNombre().contains("Cooperativo")){
            holder.tvNombre.setText("Cooperativa Obrera");
        }
        else {
                holder.tvNombre.setText(e.getNombre());
            }
        if(e.getLocalidad().equals("Sierra Chica")){
            if(e.getCalle().equals("Av. P. Legorburu") || e.getCalle().equals("Gral Julio Argentino Roca")){
                holder.tvLocalidad.setText(e.getLocalidad());
            }
            else{
                holder.tvLocalidad.setText("Olavarría");
            }
        }
        else{
            holder.tvLocalidad.setText(e.getLocalidad());
        }

        holder.tvDireccion.setText(e.getCalle() +" "+ e.getAlturaCalle() + " " + e.getObservacion());
        //holder.tvAltitudLongitud.setText(e.getLatitud() + " " +e.getLongitud());
        //holder.ivImage.setImageResource(p.getByteImg());
        //holder.ivImage.setImageBitmap(BitmapFactory.decodeByteArray(p.getByteImg(),0,p.getByteImg().length));
        holder.setPosition(position);
        if(e.getEntidad().equals("LOTERIA PROVINCIAL")){
            holder.ivImage.setImageResource(R.drawable.loteria);
        }
        else if(e.getNombre().contains("Kiosco") || e.getNombre().contains("Kyosko") ){
            holder.ivImage.setImageResource(R.drawable.kiosco);
        }
        else if(e.getNombre().contains("Polirubro") ||
                e.getNombre().contains("Autoservicio")){
            holder.ivImage.setImageResource(R.drawable.market);
        }
        else if(e.getNombre().contains("Minimercado") || e.getNombre().contains("Minitodo")
                || e.getNombre().contains("Despensa"))  {
            holder.ivImage.setImageResource(R.drawable.mercado);
        }
        else if(e.getNombre().contains("Coope")){
            holder.ivImage.setImageResource(R.drawable.coope);
        }
        else if(e.getNombre().contains("Ciber")){
            holder.ivImage.setImageResource(R.drawable.cyber);
        }
        else {
            holder.ivImage.setImageResource(R.drawable.sube);
        }
//        if(e.getByteImg() != null){
//            //Log.d("el nombre es","es"+p.getByteImg().length);
//            holder.ivImage.setImageBitmap(BitmapFactory.decodeByteArray(p.getByteImg(),0,p.getByteImg().length));
//        }

        Log.d("On bind", "On bind persona en posicion" + position);
    }

    @Override
    public int getItemCount() {
        return this.terminales.size();
    }
}
