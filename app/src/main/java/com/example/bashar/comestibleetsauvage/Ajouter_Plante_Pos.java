package com.example.bashar.comestibleetsauvage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
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

                    //update the table Plante en ajoutant two column Lat and lon
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
        Button ma_pos=(Button)findViewById(R.id.Ma_Position_Aj_Plante);
        ma_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getLocation();
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

                //ONCTIONNALITEE NON UTILISEE
                //On ajouter le texte à la barre de recherche
                //EditText searchText = (EditText) findViewById(R.id.searchPos);
                lat = point.latitude;
                lng = point.longitude;
                //searchText.setText(point.toString());
            }
        });

    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(Ajouter_Plante_Pos.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (Ajouter_Plante_Pos.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Ajouter_Plante_Pos.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                String lastId=dataBase_local.getLastId();

                if(dataBase_local.getLastId()==""+1){
                    dataBase_local.updateTableLON();
                    dataBase_local.updateTableLAT();
                }

                //Then we open the new activity
                dataBase_local.addLat(lastId,""+latti);
                dataBase_local.addLng(lastId,""+longi);

                open_activity_showResults();

                // textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitud + "\n" + "Longitude = " + longitude);

            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                String lastId=dataBase_local.getLastId();

                if(dataBase_local.getLastId()==""+1){
                    dataBase_local.updateTableLON();
                    dataBase_local.updateTableLAT();
                }

                //Then we open the new activity
                dataBase_local.addLat(lastId,""+latti);
                dataBase_local.addLng(lastId,""+longi);

                open_activity_showResults();

                // textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitud+"\n" + "Longitude = " + longitude);


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                //lattitude = String.valueOf(latti);
                //longitude = String.valueOf(longi);

                String lastId=dataBase_local.getLastId();

                if(dataBase_local.getLastId()==""+1){
                    dataBase_local.updateTableLON();
                    dataBase_local.updateTableLAT();
                }

                //Then we open the new activity
                dataBase_local.addLat(lastId,""+latti);
                dataBase_local.addLng(lastId,""+longi);

                open_activity_showResults();

                //textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude + "\n" + "Longitude = " + longitude);

            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}