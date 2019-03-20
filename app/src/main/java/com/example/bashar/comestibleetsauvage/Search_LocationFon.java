package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Search_LocationFon extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Database_Res_Fon database_res_fon;
    //The vars we need to store globally, lat and lng are sent to the next activity with Intent
    private final Marker[] marker = new Marker[1];
    private Double lat;
    private Double lng;


    private int numberRowFon;
    private String[] nomFon;
    private String[] idFon;
    private String[] libelleFon;
    private String[] statutFon;
    private String[] latFon;
    private String[] lngFon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__location_fon);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //We create what is going to happen when we hit the 'Valider' button
        database_res_fon = new Database_Res_Fon(this);
        Button clickButton = findViewById(R.id.Valider);
        listview_DB_Fontaine();
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_activity_showResults();
            }
        });

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cordonnes:
                EditText addressField = findViewById(R.id.search);
                String address = addressField.getText().toString();
                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if (!TextUtils.isEmpty(address)) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(address, 5);

                        if (addressList != null) {

                            for (int i = 0; i < addressList.size(); i++) {
                                Address userAddress = addressList.get(i);
                                LatLng coord = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                userMarkerOptions.position(coord);
                                //donner un titre
                                userMarkerOptions.title(address);
                                //changer la couleur
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                                //Caméra se déplacer vers l'endroit indiqué
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
                                //Zoom de caméra
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                                //On ajoute le marqueur généré dans le marqueur général
                                marker[0] = mMap.addMarker(userMarkerOptions);
                                //Meme chose pour la lat et la lng
                                lat = coord.latitude;
                                lng = coord.longitude;
                            }

                        } else {
                            Toast.makeText(this, "Position non trouvée...", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    //Si l'utilisateur clique sans indiquer l'endroit, il reçoit un pop-up
                    Toast.makeText(this, "Saisissez un endroit...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //Méthode utilisée pour ouvrir la porchaine page avec les résultats de la recherche
    public void open_activity_showResults() {
        Intent intent = new Intent(this, ResultFontaineSearch.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (int i = 0; i < numberRowFon; i++) {
            double latDouble = Double.parseDouble(latFon[i]);
            double lngDouble = Double.parseDouble(lngFon[i]);

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latDouble, lngDouble))
                    .title(nomFon[i])
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.logoapp));
            googleMap.addMarker(markerOptions);
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                //Si non null, on enlève le marker précédent afin de n'avoir qu'un seul marqueur à tout instant
                if (marker[0] != null) {
                    marker[0].remove();
                }

                //On ajoute le marqueur à la map
                marker[0] = mMap.addMarker(new MarkerOptions().position(point));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(point));
            }
        });
    }

    public void listview_DB_Fontaine() {

        numberRowFon = database_res_fon.CountFontaines();
        nomFon = new String[numberRowFon];
        idFon = new String[numberRowFon];
        libelleFon = new String[numberRowFon];
        statutFon = new String[numberRowFon];
        latFon = new String[numberRowFon];
        lngFon = new String[numberRowFon];

        Cursor id = database_res_fon.id();
        Cursor nom = database_res_fon.noms();
        Cursor libelle = database_res_fon.libelles();
        Cursor statut = database_res_fon.status();
        Cursor lat = database_res_fon.lats();
        Cursor lng = database_res_fon.lngs();

        int i = 0;
        while (nom.moveToNext()) {
            if (nom.getString(0) == "") {
                break;
            }
            nomFon[i] = nom.getString(0);
            i++;
        }

        int u = 0;
        while (id.moveToNext()) {
            if (id.getString(0) == "") {
                break;
            }
            idFon[u] = id.getString(0);
            u++;
        }

        int j = 0;
        while (libelle.moveToNext()) {
            if (libelle.getString(0) == "") {
                break;
            }
            libelleFon[j] = libelle.getString(0);
            j++;
        }


        int z = 0;
        while (statut.moveToNext()) {
            if (statut.getString(0) == "") {
                break;
            }
            statutFon[z] = statut.getString(0);
            z++;

        }

        z = 0;
        while (lat.moveToNext()) {
            if (lat.getString(0) == "") {
                break;
            }
            latFon[z] = lat.getString(0);
            z++;

        }

        z = 0;
        while (lng.moveToNext()) {
            if (lng.getString(0) == "") {
                break;
            }
            lngFon[z] = lng.getString(0);
            z++;

        }
    }
}