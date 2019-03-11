package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Controlleur de l'activité ajouter_plante.xml
 * Contient le formulaire servant à ajouter une plante dans la BDD locale
 */
public class Activity_ajouter_plante extends AppCompatActivity {

    //Variables globales du controlleur
    private static final int CAM_REQUEST=1313;

    private String nomP;
    private String libelleP;
    private String statutP;

    private EditText NomP_TF;
    private EditText libelleP_TF;
    private EditText statutP_TF;

    private ImageView imageView;
    private byte[] photo;
    private Button btnimage;
    private ActionBar actionBar;

    DataBase_Local dataBase;

    //Lors de la création de la page
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_plante);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Ajouter une Plante");

        //get nom plante
        NomP_TF= findViewById(R.id.Nom_Plante);

        //get libelle
        libelleP_TF= findViewById(R.id.Libelle_Plante);

        //get statut
        statutP_TF= findViewById(R.id.Statut_Plante);

        //photo
        btnimage= findViewById(R.id.image_plante);
        imageView= findViewById(R.id.photo);

        //Bouton servant à prendre une photo
        btnimage.setOnClickListener(new btnTakePhotoClicker());

        dataBase=new DataBase_Local(this);

        Button button = findViewById(R.id.button_Aj_P);
        button.setOnClickListener(new View.OnClickListener()
        {
            //lors d'un click sur la page
            @Override
            public void onClick(View v)
            {

                nomP= NomP_TF.getText().toString();
                libelleP= libelleP_TF.getText().toString();
                statutP= statutP_TF.getText().toString();

                //Si les champs sont remplis, on peut envoyer la nouvelle à la BDD locale
                if(nomP.equals("") || libelleP.equals("") || statutP.equals(""))
                {
                    Toast.makeText(Activity_ajouter_plante.this, "veuillez remplir tous les champs",Toast.LENGTH_LONG).show();
                }
                else
                {
                    dataBase.addNewPlante(new Plante(nomP,libelleP,statutP,null,null,null));

                    NomP_TF.setText("");
                    libelleP_TF.setText("");
                    statutP_TF.setText("");
                    Toast.makeText(Activity_ajouter_plante.this,""+nomP+" , "+libelleP+" , "+statutP ,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Activity_ajouter_plante.this,Ajouter_Plante_Pos.class);
                    Activity_ajouter_plante.this.startActivity(intent);
                }
            }
        });
    }

    public boolean checkNom()
    {
        return nomP != "";
    }

    public boolean checkLibelle()
    {
        return libelleP != "";
    }
    public boolean checkStatut()
    {
        return statutP != "";
    }
    public boolean checkImage()
    {
        return nomP != "";
    }

    //Pour ouvrir l'Appareil Photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_REQUEST)
        {
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    //Bouton pour prendre une photo
    class btnTakePhotoClicker implements Button.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAM_REQUEST);
        }
    }

    //Les getteurs
    public String getNomPlante()
    {
        return nomP;
    }
    public String getLibellePlante()
    {
        return libelleP;
    }
    public String getStatutPlante()
    {
        return statutP;
    }

}