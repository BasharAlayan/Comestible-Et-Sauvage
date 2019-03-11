package com.example.bashar.comestibleetsauvage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Controlleur de l'activité ajouter_fontaine.xml
 * Contient le formulaire afin d'ajouter une fontaine à la BDD
 */
public class activity_ajouter_fontaine extends AppCompatActivity
{

    //variables globales du controlleur
    private String nomF;
    private String libelleF;
    private String statutF;

    private EditText NomF_TF;
    private EditText libelleF_TF;
    private EditText statutF_TF;
    private ActionBar actionBar;

    //lors de la création de la page
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_fontaine);

        NomF_TF= findViewById(R.id.Nom_Fontaine);
        libelleF_TF= findViewById(R.id.Libelle_Fontaine);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Ajouter une Fontaine");
        statutF_TF= findViewById(R.id.StatutFontaine);

        Button button = findViewById(R.id.button_Aj_F);

        //Lors d'un click sur le Button button, on utilise cette fonction
        button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                nomF= NomF_TF.getText().toString();
                libelleF= libelleF_TF.getText().toString();
                statutF= statutF_TF.getText().toString();

                //Si l'un des champs est vide
                if(nomF.equals("") || libelleF.equals("") || statutF.equals(""))
                {
                    Toast.makeText(activity_ajouter_fontaine.this, "veuillez remplir tous les champs",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(activity_ajouter_fontaine.this,""+nomF+" , "+libelleF+" , "+statutF ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Les getteurs
    public String getNomPlante()
    {
        return  nomF;
    }
    public String getLibellePlante()
    {
        return libelleF;
    }
    public String getStatutPlante()
    {
        return statutF;
    }


}

