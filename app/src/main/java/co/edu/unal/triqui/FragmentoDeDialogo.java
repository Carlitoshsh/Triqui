package co.edu.unal.triqui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;

public class FragmentoDeDialogo extends DialogFragment {
    public int navItem;
    int nivelSeleccionado;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());

        if(navItem == 2){
            System.out.println("Test2"+navItem);

            builder.setSingleChoiceItems(R.array.dificultad, nivelSeleccionado, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Muestra los niveles que tiene el juego
                    nivelSeleccionado = i;
                }
            })
                    .setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //getResources().getStringArray(R.array.dificultad)[nivelSeleccionado]
                            JuegoTriqui.dificultadJuego = nivelSeleccionado;
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

        } else if(navItem == 3) {
            builder.setMessage(R.string.app_name)
                    .setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

        }

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
