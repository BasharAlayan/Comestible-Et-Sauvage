package com.example.bashar.comestibleetsauvage;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Synch_plante extends AppCompatActivity {

    //    Activity_ajouter_plante ajouter_plante;
    DataBase_Local dataBase_global;
    private ListView mlistView;
    private static final String TAG = "Synch_plante";
    private Button transmettre;
    private Button supprimer;
    private ActionBar actionBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_synch_plante);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Synchronisation une Plante");
        mlistView = (ListView) findViewById(R.id.listview);
        dataBase_global = new DataBase_Local(this);
        listview_DB();
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
        String[] latTab = new String[numberRow];
        String[] lngTab = new String[numberRow];

        Cursor id = dataBase_global.id();
        Cursor nom = dataBase_global.noms();
        Cursor libelle = dataBase_global.libelles();
        Cursor statut = dataBase_global.status();
        Cursor lat = dataBase_global.lats();
        Cursor lng = dataBase_global.lngs();

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
        Adapter adapter = new Adapter(this, idTab, nomTab,libelleTab, statutTab,latTab,lngTab);
        mlistView.setAdapter(adapter);

    }

}