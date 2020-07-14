package com.example.aplicacionmunicipiodeolavarria;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

public class TerminalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public View view;

    TextView tvNombre;
    TextView tvLocalidad;
   //TextView tvSubActividad;
    TextView tvDireccion;
    TextView tvAltitudLongitud;
    ImageView ivImage;

    MyOnItemClick listener;
    int position;


    public TerminalViewHolder(@NonNull View itemView, MyOnItemClick listener)  {
        super(itemView);
        this.view = itemView;
        this.tvNombre= view.findViewById(R.id.tvNombre);
       // this.tvSubActividad = view.findViewById(R.id.tvSubActividad);
        this.tvDireccion = view.findViewById(R.id.tvDireccion);
        this.tvLocalidad = view.findViewById(R.id.tvLocalidad);
        this.tvAltitudLongitud = view.findViewById(R.id.tvAltitudLongitud);
        this.ivImage = view.findViewById(R.id.ivImage);

        itemView.setOnClickListener(this);
        this.listener = listener;

    }
    public void setPosition(int position){
        this.position = position;
    }
    @Override
    public void onClick(View view) {
        //Log.d("llamando a", "ENTRO A ONclICK EN pERSONA vIEW HOLDER");
        listener.onItemClick(position);
    }
}
