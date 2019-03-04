package com.example.bashar.comestibleetsauvage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_ajouter_plante extends AppCompatActivity {

    private String nomP;
    private String libelleP;
    private String statutP;

    private EditText NomP_TF;
    private EditText libelleP_TF;
    private EditText statutP_TF;

    DataBase_Local dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_plante);

        //get nom plante
        NomP_TF=(EditText)findViewById(R.id.Nom_Plante);


        //get libelle
        libelleP_TF=(EditText)findViewById(R.id.Libelle_Plante);


        //get statut
        statutP_TF=(EditText)findViewById(R.id.Statut_Plante);


        dataBase=new DataBase_Local(this);

        Button button = (Button)findViewById(R.id.button_Aj_P);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                nomP= NomP_TF.getText().toString();
                libelleP= libelleP_TF.getText().toString();
                statutP= statutP_TF.getText().toString();


                if(nomP.equals("") || libelleP.equals("") || statutP.equals("")){
                    Toast.makeText(Activity_ajouter_plante.this, "veuillez remplir tous les champs",Toast.LENGTH_LONG).show();
                }
                else {
                    dataBase.addNewPlante(new Plante(nomP,libelleP,statutP,"zz"));
                    NomP_TF.setText("");
                    libelleP_TF.setText("");
                    statutP_TF.setText("");
                    Toast.makeText(Activity_ajouter_plante.this,""+nomP+" , "+libelleP+" , "+statutP ,Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    /*
    public void addData(String new_Nom, String new_Libelle, String new_Statut, String new_Image){
        boolean insertData=dataBase.addPlante(new_Nom,new_Libelle,new_Statut,new_Image);
        if(insertData){
            Toast.makeText(Activity_ajouter_plante.this,""+nomP+" , "+libelleP+" , "+statutP ,Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(Activity_ajouter_plante.this,"une erreur",Toast.LENGTH_LONG).show();

        }

    }
    */
    public boolean checkNom(){
        if (nomP=="") {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkLibelle(){
        if (libelleP=="") {
            return false;
        }
        else {
            return true;
        }
    }
    public boolean checkStatut(){
        if (statutP=="") {
            return false;
        }
        else {
            return true;
        }
    }
    public boolean checkImage(){
        if (nomP=="") {
            return false;
        }
        else {
            return true;
        }
    }
}
