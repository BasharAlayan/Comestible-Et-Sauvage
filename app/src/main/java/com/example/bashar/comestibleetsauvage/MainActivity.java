package com.example.bashar.comestibleetsauvage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

/**
 * Controlleur de activity_main.xml, correspond à la page d'accueil et ses boutons
 */
public class MainActivity extends AppCompatActivity
{
    //les variables du controlleur, corespondant aux boutons
    private Button ajouter;
    private Button rechercher;
    private Button syn;
    private ActionBar actionBar;


    //On gère ici les accès aux autres acitivtées selon le bouton cliqué
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Accueil");
        ajouter= findViewById(R.id.Ajouter);
        ajouter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open_activity_ajouter();
            }
        });

        rechercher= findViewById(R.id.Rechercher);
        rechercher.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open_activity_Rechercher();
            }
        });


        syn= findViewById(R.id.Synchro);
        syn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open_activity_Syn();
            }
        });
    }

    //Les fonctions d'ouverture d'activités selon les boutons
    public void open_activity_ajouter()
    {
        Intent intent = new Intent(this,Activity_Ajouter.class);
        startActivity(intent);
    }
    public void open_activity_Rechercher()
    {
        Intent intent = new Intent(this,Liste_plante_firebase.class);
        startActivity(intent);
    }
    public void open_activity_Syn()
    {
        Intent intent = new Intent(this,Synchronisation.class);
        startActivity(intent);
    }
}
