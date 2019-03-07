package com.example.bashar.comestibleetsauvage;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Search_Location extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.cordonnes:
                EditText addressField = (EditText) findViewById(R.id.search);
                String address = addressField.getText().toString();
                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if(!TextUtils.isEmpty(address)){
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(address, 5);

                        if(addressList != null){

                            for(int i = 0; i < addressList.size(); i++){
                                Address userAddress = addressList.get(i);
                                LatLng coord = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                userMarkerOptions.position(coord);
                                //donner un titre
                                userMarkerOptions.title(address);
                                //changer la couleur
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                mMap.addMarker(userMarkerOptions);
                                //Caméra se déplacer vers l'endroit indiqué
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
                                //Zoom de caméra
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }

                        } else {
                            Toast.makeText(this, "Position non trouvée...", Toast.LENGTH_SHORT).show();
                        }

                    } catch(IOException e){
                        e.printStackTrace();
                    }

                } else {
                    //Si l'utilisateu clique sans indiquer l'endroit, il reçoit un pop-up
                    Toast.makeText(this, "Saisissez un endroit...", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(37.4219983333335, -122.08400000000002);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Kathmandu, Nepal"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
}