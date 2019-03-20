package com.example.bashar.comestibleetsauvage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


public class choosePlante extends AppCompatActivity {
    private ActionBar actionBar;
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    double latti;
    double longi;
    Database_Res database_res;

    //The vars we need to store globally, lat and lng are sent to the next activity with Intent


    MapsActivity mapsActivity;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_plante);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Detail de la Plante");
        mapsActivity = new MapsActivity();
        database_res = new Database_Res(this);
        listView = (ListView) findViewById(R.id.listViewRes);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

        listview_DB();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(choosePlante.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (choosePlante.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(choosePlante.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                latti = location.getLatitude();
                longi = location.getLongitude();

            } else if (location1 != null) {
                latti = location1.getLatitude();
                longi = location1.getLongitude();

            } else if (location2 != null) {
                latti = location2.getLatitude();
                longi = location2.getLongitude();
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

    private void listview_DB() {

        int numberRow;
        String[] nomTab;
        String[] idTab;
        String[] libelleTab;
        String[] statutTab;
        String[] latTab;
        String[] lngTab;

        numberRow = database_res.CountPlantes();

        nomTab = new String[numberRow];
        idTab = new String[numberRow];
        libelleTab = new String[numberRow];
        statutTab = new String[numberRow];
        latTab = new String[numberRow];
        lngTab = new String[numberRow];


        Cursor res = database_res.listRes("" + latti, "" + longi);

        Log.e("latti", "latti est" + latti);
        Log.e("lng", "lng est" + longi);
        int i = 0;
        while (res.moveToNext()) {
            if (res.getString(2) == null) {
                break;
            }
            idTab[i] = res.getString(0);
            nomTab[i] = res.getString(1);
            libelleTab[i] = res.getString(2);
            statutTab[i] = res.getString(3);
            latTab[i] = res.getString(5);
            lngTab[i] = res.getString(6);

            i++;
        }
        Adapter_PlanteRes adapter = new Adapter_PlanteRes(this, idTab, nomTab, libelleTab, statutTab, latTab, lngTab);
        listView.setAdapter(adapter);
    }


}

