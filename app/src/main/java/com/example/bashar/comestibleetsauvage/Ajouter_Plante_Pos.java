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

public class Ajouter_Plante_Pos extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    //The vars we need to store globally, lat and lng are sent to the next activity with Intent
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

        //We create what is going to happen when we hit the 'Valider' button
        Button clickButton =(Button) findViewById(R.id.ValiderPos);
        dataBase_local=new DataBase_Local(this);
        clickButton.setOnClickListener( new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(marker[0]!= null)
                {
                    //TODO
                    //CHANGE THE VALUE search_Location.class TO THE NAME OF THE NEXT GOOGLE MAP THAT WILL SHOW THE RESULTS
                    //We create a new intent for the next activity
                    Intent intent = new Intent(getBaseContext(), Search_Location.class);

                    intent.putExtra("Lat", lat);
                    intent.putExtra("Lng", lng);

                    String lastId=dataBase_local.getLastId();

                    if(dataBase_local.getLastId()==""+1){
                        dataBase_local.updateTableLON();
                        dataBase_local.updateTableLAT();
                    }

                    //Then we open the new activity
                    dataBase_local.addLat(lastId,""+lat);
                    dataBase_local.addLng(lastId,""+lng);

                    open_activity_showResults();
                }
            }
        });
    }

    //Method used to open the next google map with the results
    public void open_activity_showResults()
    {
        //TODO
        //THE REFERENCED ACTIVITY IS TO BE CHANGED BY A MAP WITH THE RESULTS
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    // Not used right now
    /*
    public void onSearch(View view){
        EditText location_textF=(EditText)findViewById(R.id.search);
        String location=location_textF.getText().toString();
        List<Address> addressList=null;
        if(location != null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latlng = new LatLng(address.getLatitude(), address.getLatitude());
            mMap.addMarker(new MarkerOptions().position(latlng));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
        }
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(27.746974, 85.301582);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Somewhere, IDK"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        */

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng point)
            {
                //If not null,  we remove the previous marker, so that there is only one marker at all times
                if (marker[0] != null)
                {
                    marker[0].remove();
                }

                //we add the marker to the map
                marker[0] = mMap.addMarker(new MarkerOptions().position(point));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(point));

                //we add text with lat and lng to the top bar
                EditText searchText = (EditText) findViewById(R.id.searchPos);
                lat = point.latitude;
                lng = point.longitude;
                searchText.setText(point.toString());
            }
        });
    }

}