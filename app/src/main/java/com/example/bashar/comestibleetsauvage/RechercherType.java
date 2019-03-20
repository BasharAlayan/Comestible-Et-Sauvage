package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RechercherType extends AppCompatActivity {

    private Button Plante;
    private Button Fonatine;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher_type);
        Plante = findViewById(R.id.Syn_Plante);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Rechercher une Fontaine OU une Plante");
        Plante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_activity_RchercherP();
            }
        });

        Fonatine = findViewById(R.id.Syn_Fontaine);
        Fonatine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_activity_RechercherF();
            }
        });
    }

    //Méthode utilisées pour les boutons
    public void open_activity_RchercherP() {
        Intent intent = new Intent(this, Liste_plante_firebase.class);
        startActivity(intent);
    }

    public void open_activity_RechercherF() {
        Intent intent = new Intent(this, list_fontaine_firebase.class);
        startActivity(intent);
    }
}
