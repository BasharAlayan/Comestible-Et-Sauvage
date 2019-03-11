package com.example.bashar.comestibleetsauvage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Classe précisant le modèle de la plante sur Firebase
 */
public class ModelFirebasePlante extends AppCompatActivity
{

    TextView txt_nom;
    TextView txt_libelle;
    TextView txt_statut;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_firebase_plante);
    }
}
