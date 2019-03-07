package com.example.bashar.comestibleetsauvage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Activity_ajouter_plante extends AppCompatActivity {

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

        //photo
        btnimage= (Button) findViewById(R.id.image_plante);
        imageView=(ImageView)findViewById(R.id.photo);

        btnimage.setOnClickListener(new btnTakePhotoClicker());


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
                    dataBase.addNewPlante(new Plante(nomP,libelleP,statutP,null));
                    NomP_TF.setText("");
                    libelleP_TF.setText("");
                    statutP_TF.setText("");
                    Toast.makeText(Activity_ajouter_plante.this,""+nomP+" , "+libelleP+" , "+statutP ,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

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

    // pour ouvrir l'Appareil Photo


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_REQUEST){
            Bitmap bitmap=(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    class btnTakePhotoClicker implements Button.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAM_REQUEST);
        }
    }

}