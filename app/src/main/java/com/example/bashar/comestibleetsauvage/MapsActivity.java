package com.example.bashar.comestibleetsauvage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
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
    protected double myLatitude, myLongitude;
    private Database_Res database_res;

    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private double latti;
    private double longi;

    private int numberRow;
    private String[] nomTab;
    private String[] idTab;
    private String[] libelleTab;
    private String[] statutTab;
    private String[] latTab;
    private String[] lngTab;

    Button valider;

    //Lors de la création de la map
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_my_location);
        database_res = new Database_Res(this);
        valider = (Button) findViewById(R.id.ResultPlante);
        listview_DB();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result();
            }
        });
    }

    public void Result() {
        Intent intent = new Intent(this, choosePlante.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //rien de plus n'est nécéssaire ici dans notre cas
            return;
        }

        for (int i = 0; i < numberRow; i++) {
            double latDouble = Double.parseDouble(latTab[i]);
            double lngDouble = Double.parseDouble(lngTab[i]);

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latDouble, lngDouble))
                    .title(nomTab[i])
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            googleMap.addMarker(markerOptions);

            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(new LatLng(latti, longi));
            circleOptions.radius(700);
            circleOptions.fillColor(0x110000FF);
            circleOptions.strokeWidth(6);
            googleMap.addCircle(circleOptions);

        }


        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        myLatitude = location.getLatitude();
                        myLongitude = location.getLongitude();

                        lastLocation = location;

                        if (currentLocationMarker != null) {
                            currentLocationMarker.remove();
                        }

                        LatLng latlng = new LatLng(myLatitude, myLongitude);
                        MarkerOptions markerOption = new MarkerOptions();
                        markerOption.position(latlng);
                        markerOption.title("Ma Position");


                        currentLocationMarker = mMap.addMarker(markerOption);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        //Inutilisé ici, mais présence nécéssaire
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        //Inutilisé ici, mais présence nécéssaire
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        //Inutilisé ici, mais présence nécéssaire
                    }
                }
        );

        mMap.animateCamera(CameraUpdateFactory.zoomBy(1));
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result();
            }
        });
    }

    private void listview_DB() {
        numberRow = database_res.CountPlantes();
        nomTab = new String[numberRow];
        idTab = new String[numberRow];
        libelleTab = new String[numberRow];
        statutTab = new String[numberRow];
        latTab = new String[numberRow];
        lngTab = new String[numberRow];

        Cursor id = database_res.id();
        Cursor nom = database_res.noms();
        Cursor libelle = database_res.libelles();
        Cursor statut = database_res.status();
        Cursor lat = database_res.lats();
        Cursor lng = database_res.lngs();

        int i = 0;
        while (nom.moveToNext()) {
            if (nom.getString(0).equals("")) {
                break;
            }
            nomTab[i] = nom.getString(0);
            i++;
        }

        int u = 0;
        while (id.moveToNext()) {
            if (id.getString(0).equals("")) {
                break;
            }
            idTab[u] = id.getString(0);
            u++;
        }

        int j = 0;
        while (libelle.moveToNext()) {
            if (libelle.getString(0).equals("")) {
                break;
            }
            libelleTab[j] = libelle.getString(0);
            j++;
        }


        int z = 0;
        while (statut.moveToNext()) {
            if (statut.getString(0).equals("")) {
                break;
            }
            statutTab[z] = statut.getString(0);
            z++;

        }

        z = 0;
        while (lat.moveToNext()) {
            if (lat.getString(0).equals("")) {
                break;
            }
            latTab[z] = lat.getString(0);
            z++;

        }

        z = 0;
        while (lng.moveToNext()) {
            if (lng.getString(0).equals("")) {
                break;
            }
            lngTab[z] = lng.getString(0);
            z++;

        }
        Adapter adapter = new Adapter(this, idTab, nomTab, libelleTab, statutTab, latTab, lngTab);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                latti = location.getLatitude();
                longi = location.getLongitude();

                // textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitud + "\n" + "Longitude = " + longitude);

            } else if (location1 != null) {
                latti = location1.getLatitude();
                longi = location1.getLongitude();


                // textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitud+"\n" + "Longitude = " + longitude);


            } else if (location2 != null) {
                latti = location2.getLatitude();
                longi = location2.getLongitude();
                //lattitude = String.valueOf(latti);
                //longitude = String.valueOf(longi);


                //textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude + "\n" + "Longitude = " + longitude);

            } else {

                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show();

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