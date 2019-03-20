package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Controlleur de l'activité activity_rechercher.xml
 * Sert à choisir si l'utilisateur veut sa position ou une position de son choix
 */
public class Activity_rechercher extends AppCompatActivity {
    //Les variables globales du controlleur
    private Button ma_position;
    private Button recherche_position;
    private ActionBar actionBar;

    //Lors de la création de la page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Rechercher");

        ma_position = findViewById(R.id.Ma_Position);
        //Gestion du bouton de l'un des choix de l'utilisateur
        ma_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_activity_Maps();
            }
        });

        recherche_position = findViewById(R.id.Choisir_Lieu);
        //Gestion du bouton de l'un des choix de l'utilisateur
        recherche_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_activity_Maps2();
            }
        });
    }

    //Fonctions servant à rediriger vers la bonne activité selon le choix de l'utilisateur
    public void open_activity_Maps() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void open_activity_Maps2() {
        Intent intent = new Intent(this, Search_Location.class);
        startActivity(intent);
    }
}