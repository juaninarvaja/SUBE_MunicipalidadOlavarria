package com.example.aplicacionmunicipiodeolavarria;
import android.app.Dialog;
import android.os.Bundle;
import android.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class Dialogo extends DialogFragment {
    MainActivity mainActivity;
    String titulo;
    String mensaje;
    public Dialogo(MainActivity activity){
        this.mainActivity = activity;
    }
    public Dialogo(MainActivity main, String titulo, String mensaje){
        //usar estos valores para setear en el oncreate
        this.mainActivity = main;
        this.titulo = titulo;
        this.mensaje = mensaje;

    }
    public Dialog onCreateDialog(Bundle bundle) {
        /*Dialog generico*/

        AlertDialog.Builder builder = new AlertDialog.Builder(super.getActivity());
        builder.setTitle(this.titulo);
        builder.setMessage(this.mensaje);
        //ClickDialogo clickDialogGenerico = new ClickDialogo();
        //builder.setPositiveButton("OK",clickDialogGenerico);
        return builder.create();
    }

}
