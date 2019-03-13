package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe servant à récupérer des plantes depuis la BDD distante
 */
public class Liste_plante_firebase extends AppCompatActivity {

    private ListView listView;
    private List<Plante> planteList;
    private DatabaseReference databaseReference;
    private ActionBar actionBar;
    private Button ma_position;
    private Button recherche_position;
    private Database_Res database_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_plante_firebase);
        listView = findViewById(R.id.listviewFirebase);
        planteList = new ArrayList<>();
        database_res = new Database_Res(Liste_plante_firebase.this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Plantes");
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Rechercher");

        ma_position = findViewById(R.id.Ma_Position);
        //Gestion du bouton de l'un des choix de l'utilisateur
        ma_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_activity_Maps();
            }
        });

        recherche_position = findViewById(R.id.Choisir_Lieu);
        //Gestion du bouton de l'un des choix de l'utilisateur
        recherche_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_activity_Maps2();
            }
        });
    }

    //Fonctions servant à rediriger vers la bonne activité selon le choix de l'utilisateur
    public void open_activity_Maps() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void open_activity_Maps2() {
        Intent intent = new Intent(this, Search_Location.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                planteList.clear();
                database_res.deleteData();
                for (DataSnapshot planteSanpshot : dataSnapshot.getChildren()) {
                    Plante plante = planteSanpshot.getValue(Plante.class);
                    database_res = new Database_Res(Liste_plante_firebase.this);
                    database_res.createTable();
                    database_res.InsertData(plante.getId(), plante.getNom(), plante.getLibelle(), plante.getStatut(), null, plante.getLat(), plante.getLon());
                    planteList.add(plante);

                }

                PlantesList_Firebase adapter = new PlantesList_Firebase(Liste_plante_firebase.this, planteList);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
