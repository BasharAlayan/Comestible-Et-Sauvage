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
 * Controlleur de l'activité ajouter_fontaine.xml
 * Contient le formulaire afin d'ajouter une fontaine à la BDD
 */
public class activity_ajouter_fontaine extends AppCompatActivity {

    //Variables globales du controlleur
    private static final int CAM_REQUEST = 1313;

    private String nomF;
    private String libelleF;
    private String statutF;

    private EditText NomF_TF;
    private EditText libelleF_TF;
    private EditText statutF_TF;
    private ActionBar actionBar;


    private ImageView imageView;
    private byte[] photo;
    private Button btnimage;

    private DataBase_Local_Fontaine dataBase;

    //lors de la création de la page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_fontaine);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Ajouter une Fontaine");

        NomF_TF = findViewById(R.id.Nom_Fontaine);
        libelleF_TF = findViewById(R.id.Libelle_Fontaine);
        statutF_TF = findViewById(R.id.StatutFontaine);

        //photo
        btnimage = findViewById(R.id.image_Fontaine);
        imageView = findViewById(R.id.photoFontaine);

        //Bouton servant à prendre une photo
        btnimage.setOnClickListener(new activity_ajouter_fontaine.btnTakePhotoClicker());

        Button button = findViewById(R.id.button_Aj_F);
        dataBase = new DataBase_Local_Fontaine(this);
        dataBase.createTable();
        //Lors d'un click sur le Button button, on utilise cette fonction
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                nomF = NomF_TF.getText().toString();
                libelleF = libelleF_TF.getText().toString();
                statutF = statutF_TF.getText().toString();

                //Si les champs sont remplis, on peut envoyer la nouvelle à la BDD locale
                if (nomF.equals("") || libelleF.equals("") || statutF.equals("")) {
                    Toast.makeText(activity_ajouter_fontaine.this, "veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
                } else {
                    dataBase.addNewFontaine(new Fontaine(nomF, libelleF, statutF, null, null, null));

                    NomF_TF.setText("");
                    libelleF_TF.setText("");
                    statutF_TF.setText("");


                    Toast.makeText(activity_ajouter_fontaine.this, "veuillez choisir le lieu", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(activity_ajouter_fontaine.this, Ajouter_Fontaine_Pos.class);
                    activity_ajouter_fontaine.this.startActivity(intent);
                }
            }
        });
    }

    //Pour ouvrir l'Appareil Photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    //Bouton pour prendre une photo
    class btnTakePhotoClicker implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAM_REQUEST);
        }
    }
}

