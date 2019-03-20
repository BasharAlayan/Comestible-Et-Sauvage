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
import android.support.v7.app.ActionBar;
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

public class MapsActivityFon extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker currentLocationMarker;
    private Location lastLocation;
    private double myLatitude, myLongitude;
    private Database_Res_Fon database_res_fon;

    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    double latti;
    double longi;

    private int numberRowFON;
    private String[] nomFon;
    private String[] idFon;
    private String[] libelleFon;
    private String[] statutFon;
    private String[] latFon;
    private String[] lngFon;

    Button valider;
    private ActionBar actionBar;

    //Lors de la création de la map
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_fon);
        database_res_fon = new Database_Res_Fon(this);
        listview_DB();
        valider = (Button) findViewById(R.id.ResultPlante);
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
        Intent intent = new Intent(this, ChooseFontaine.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //rien de plus n'est nécéssaire ici dans notre cas
            return;
        }
        for (int i = 0; i < numberRowFON; i++) {
            double latDouble = Double.parseDouble(latFon[i]);
            double lngDouble = Double.parseDouble(lngFon[i]);

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latDouble, lngDouble))
                    .title(nomFon[i])
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.logoapp));
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
    }

    private void listview_DB() {
        numberRowFON = database_res_fon.CountFontaines();
        nomFon = new String[numberRowFON];
        idFon = new String[numberRowFON];
        libelleFon = new String[numberRowFON];
        statutFon = new String[numberRowFON];
        latFon = new String[numberRowFON];
        lngFon = new String[numberRowFON];

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
        AdapteFontaine adapter = new AdapteFontaine(this, idFon, nomFon, libelleFon, statutFon, latFon, lngFon);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivityFon.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MapsActivityFon.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivityFon.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

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