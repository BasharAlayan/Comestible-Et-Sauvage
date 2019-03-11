package com.example.bashar.comestibleetsauvage;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class Search_Location extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    //The vars we need to store globally, lat and lng are sent to the next activity with Intent
    private final Marker[] marker = new Marker[1];
    private Double lat;
    private Double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //We create what is going to happen when we hit the 'Valider' button
        Button clickButton = findViewById(R.id.Valider);
        clickButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(marker[0]!= null)
                {
                    //La valeur de Search_Location correspond à la prochaine page
                    //We create a new intent for the next activity
                    Intent intent = new Intent(getBaseContext(), Search_Location.class);
                    intent.putExtra("Lat", lat);
                    intent.putExtra("Lng", lng);

                    //Then we open the new activity
                    open_activity_showResults();
                }
            }
        });
    }

    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.cordonnes:
                EditText addressField = findViewById(R.id.search);
                String address = addressField.getText().toString();
                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if(!TextUtils.isEmpty(address))
                {
                    Geocoder geocoder = new Geocoder(this);
                    try
                    {
                        addressList = geocoder.getFromLocationName(address, 5);

                        if(addressList != null)
                        {

                            for(int i = 0; i < addressList.size(); i++)
                            {
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

                        }
                        else
                        {
                            Toast.makeText(this, "Position non trouvée...", Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }
                else
                {
                    //Si l'utilisateur clique sans indiquer l'endroit, il reçoit un pop-up
                    Toast.makeText(this, "Saisissez un endroit...", Toast.LENGTH_SHORT).show();
                }
            break;
        }
    }

    //Méthode utilisée pour ouvrir la porchaine page avec les résultats de la recherche
    public void open_activity_showResults()
    {
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
                //Si non null, on enlève le marker précédent afin de n'avoir qu'un seul marqueur à tout instant
                if (marker[0] != null)
                {
                    marker[0].remove();
                }

                //On ajoute le marqueur à la map
                marker[0] = mMap.addMarker(new MarkerOptions().position(point));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(point));
            }
        });
    }
}