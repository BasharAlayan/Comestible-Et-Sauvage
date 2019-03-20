package com.example.bashar.comestibleetsauvage;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ResultPlanteSearch extends AppCompatActivity {
    private Database_Res dataBase;
    private ListView mlistView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_plante_search);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Detail des Plantes");
        mlistView = findViewById(R.id.listviewResultSearch);
        dataBase = new Database_Res(this);
        listview_DB();
    }

    private void listview_DB() {
        int numberRow = dataBase.CountPlantes();

        String[] nomTab = new String[numberRow];
        String[] idTab = new String[numberRow];
        String[] libelleTab = new String[numberRow];
        String[] statutTab = new String[numberRow];
        String[] latTab = new String[numberRow];
        String[] lngTab = new String[numberRow];

        Cursor id = dataBase.id();
        Cursor nom = dataBase.noms();
        Cursor libelle = dataBase.libelles();
        Cursor statut = dataBase.status();
        Cursor lat = dataBase.lats();
        Cursor lng = dataBase.lngs();

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
        Adapter_PlanteRes adapter = new Adapter_PlanteRes(this, idTab, nomTab, libelleTab, statutTab, latTab, lngTab);
        mlistView.setAdapter(adapter);

    }

}
