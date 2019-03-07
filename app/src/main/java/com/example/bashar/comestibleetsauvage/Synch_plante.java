package com.example.bashar.comestibleetsauvage;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Synch_plante extends AppCompatActivity {

    //    Activity_ajouter_plante ajouter_plante;
    DataBase_Local dataBase_global;
    private ListView mlistView;
    private static final String TAG = "Synch_plante";
    private Button transmettre;
    private Button supprimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synch_plante);
        mlistView = (ListView) findViewById(R.id.listview);
        dataBase_global = new DataBase_Local(this);
        listview_DB();
/*
        supprimer=(Button)findViewById(R.id.supprimer);
        supprimer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(Synch_plante.this, "veuillez remplir tous les champs",Toast.LENGTH_LONG).show();

            }
        });
*/
    }

    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private void listview_DB() {
        // Log.d(TAG,"Displaying data in the ListView");

        int numberRow = dataBase_global.CountPlantes();

        String[] nomTab = new String[numberRow];
        String[] idTab = new String[numberRow];
        String[] libelleTab = new String[numberRow];
        String[] statutTab = new String[numberRow];

        Cursor id = dataBase_global.id();
        Cursor nom = dataBase_global.a();
        Cursor libelle = dataBase_global.b();
        Cursor statut = dataBase_global.c();

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
        Adapter adapter = new Adapter(this, idTab, nomTab,libelleTab, statutTab);
        mlistView.setAdapter(adapter);

    }

}