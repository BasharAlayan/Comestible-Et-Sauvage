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

public class ChooseFontaine extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private double latti;
    private double longi;
    private Database_Res_Fon database_res_fon;

    MapsActivity mapsActivity;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_fontaine);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Detail de la Fontaine");
        mapsActivity = new MapsActivity();
        database_res_fon = new Database_Res_Fon(this);
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
        if (ActivityCompat.checkSelfPermission(ChooseFontaine.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (ChooseFontaine.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ChooseFontaine.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

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

    private void listview_DB() {
        int numberRow = database_res_fon.CountFontaines();

        String[] nomTab = new String[numberRow];
        String[] idTab = new String[numberRow];
        String[] libelleTab = new String[numberRow];
        String[] statutTab = new String[numberRow];
        String[] latTab = new String[numberRow];
        String[] lngTab = new String[numberRow];

        Cursor res = database_res_fon.listRes("" + latti, "" + longi);

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
        Adapter_FontaineRes adapter = new Adapter_FontaineRes(this, idTab, nomTab, libelleTab, statutTab, latTab, lngTab);
        listView.setAdapter(adapter);
    }


}
