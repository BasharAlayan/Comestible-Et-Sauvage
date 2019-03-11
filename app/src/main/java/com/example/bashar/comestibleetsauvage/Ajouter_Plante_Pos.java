package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Controlleur servant à choisir une position sur une map GoogleMap, position qui pourra ensuite être utilisée
 */
public class Ajouter_Plante_Pos extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    //Les variables globales du controlleur, lat et lng étant envoyés à la page suivante via Intent
    private final Marker[] marker = new Marker[1];
    private Double lat;
    private Double lng;
    private ActionBar actionBar;

    DataBase_Local dataBase_local;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__plante__pos);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //On défini ce qui va se passe lors de la validation avec le bouton valider
        Button clickButton = findViewById(R.id.ValiderPos);
        dataBase_local=new DataBase_Local(this);
        clickButton.setOnClickListener( new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(marker[0]!= null)
                {
                    // SEARCH_LOCATION représente la page suivante
                    //We create a new intent for the next activity
                    Intent intent = new Intent(getBaseContext(), Search_Location.class);

                    intent.putExtra("Lat", lat);
                    intent.putExtra("Lng", lng);

                    String lastId=dataBase_local.getLastId();

                    if(dataBase_local.getLastId()==""+1){
                        dataBase_local.updateTableLON();
                        dataBase_local.updateTableLAT();
                    }

                    //Ensuite, nous ouvrons cette autre activité
                    dataBase_local.addLat(lastId,""+lat);
                    dataBase_local.addLng(lastId,""+lng);

                    open_activity_showResults();
                }
            }
        });
    }

    //Methode utilisée afin d'ouvri la prochaine page google map
    public void open_activity_showResults()
    {
        //TODO
        //THE REFERENCED ACTIVITY IS TO BE CHANGED BY A MAP WITH THE RESULTS
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng point)
            {
                //Si non null,  on enlève le marqueur précédent, car nous ne devons avoir qu'un seul marqueur a tout instant
                if (marker[0] != null)
                {
                    marker[0].remove();
                }

                //On ajoute le nouveau markeur à la map
                marker[0] = mMap.addMarker(new MarkerOptions().position(point));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(point));

                //FONCTIONNALITEE NON UTILISEE
                //On ajouter le texte à la barre de recherche
                /*EditText searchText = findViewById(R.id.searchPos);
                lat = point.latitude;
                lng = point.longitude;
                searchText.setText(point.toString());*/
            }
        });
    }
}