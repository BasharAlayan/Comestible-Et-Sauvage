package com.example.bashar.comestibleetsauvage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModelFirebasePlante extends AppCompatActivity {

    TextView txt_nom;
    TextView txt_libelle;
    TextView txt_statut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_firebase_plante);
//        txt_nom = (TextView) convertView.findViewById(R.id.publier);

    }
}
