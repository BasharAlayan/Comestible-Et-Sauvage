package com.example.bashar.comestibleetsauvage;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Classe de synchronisation des fontaines entre les 2 BDD
 */
public class Synch_fontaine extends AppCompatActivity {

    private DataBase_Local_Fontaine db;
    private ListView mlistView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synch_fontaine);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Synchronisation une Fontaine");
        mlistView = findViewById(R.id.listviewFontaine);
        db = new DataBase_Local_Fontaine(this);
        listview_DB();
    }

    private void listview_DB() {

        int numberRow = db.CountFontaine();

        String[] nomTab = new String[numberRow];
        String[] idTab = new String[numberRow];
        String[] libelleTab = new String[numberRow];
        String[] statutTab = new String[numberRow];
        String[] latTab = new String[numberRow];
        String[] lngTab = new String[numberRow];

        Cursor id = db.id();
        Cursor nom = db.noms();
        Cursor libelle = db.libelles();
        Cursor statut = db.status();
        Cursor lat = db.lats();
        Cursor lng = db.lngs();

        int i = 0;
        while (nom.moveToNext()) {
            if (nom.getString(0) == "") {
                break;
            }
            nomTab[i] = nom.getString(0);
            i++;
        }

        int u = 0;
        while (id.moveToNext()) {
            if (id.getString(0) == "") {
                break;
            }
            idTab[u] = id.getString(0);
            u++;
        }

        int j = 0;
        while (libelle.moveToNext()) {
            if (libelle.getString(0) == "") {
                break;
            }
            libelleTab[j] = libelle.getString(0);
            j++;
        }


        int z = 0;
        while (statut.moveToNext()) {
            if (statut.getString(0) == "") {
                break;
            }
            statutTab[z] = statut.getString(0);
            z++;

        }

        z = 0;
        while (lat.moveToNext()) {
            if (lat.getString(0) == "") {
                break;
            }
            latTab[z] = lat.getString(0);
            z++;

        }

        z = 0;
        while (lng.moveToNext()) {
            if (lng.getString(0) == "") {
                break;
            }
            lngTab[z] = lng.getString(0);
            z++;
        }
        AdapteFontaine adapter = new AdapteFontaine(this, idTab, nomTab, libelleTab, statutTab, latTab, lngTab);
        mlistView.setAdapter(adapter);
    }
}