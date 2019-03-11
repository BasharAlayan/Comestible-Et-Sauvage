package com.example.bashar.comestibleetsauvage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Controlleur de la map, qui sert à l'initaliser
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker currentLocationMarker;
    private Location lastLocation;
    private double myLatitude,myLongitude;

    private ActionBar actionBar;

    //Lors de la création de la map
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_my_location);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //rien de plus n'est nécéssaire ici dans notre cas
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0, new android.location.LocationListener()
                {
                    @Override
                    public void onLocationChanged(Location location)
                    {
                        myLatitude=location.getLatitude();
                        myLongitude=location.getLongitude();

                        lastLocation=location;

                        if(currentLocationMarker != null)
                        {
                            currentLocationMarker.remove();
                        }

                        LatLng latlng=new LatLng(myLatitude,myLongitude);
                        MarkerOptions markerOption=new MarkerOptions();
                        markerOption.position(latlng);
                        markerOption.title("Ma Position");

                        currentLocationMarker=mMap.addMarker(markerOption);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras)
                    {
                        //Inutilisé ici, mais présence nécéssaire
                    }

                    @Override
                    public void onProviderEnabled(String provider)
                    {
                        //Inutilisé ici, mais présence nécéssaire
                    }

                    @Override
                    public void onProviderDisabled(String provider)
                    {
                        //Inutilisé ici, mais présence nécéssaire
                    }
                }
        );
        mMap.animateCamera(CameraUpdateFactory.zoomBy(2));
    }
}