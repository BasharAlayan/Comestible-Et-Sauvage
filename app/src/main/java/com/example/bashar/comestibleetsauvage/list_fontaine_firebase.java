package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class list_fontaine_firebase extends AppCompatActivity {

    private ListView listView;
    private List<Fontaine> fontaineList;
    private DatabaseReference databaseReference;
    private ActionBar actionBar;
    private Button ma_position;
    private Button recherche_position;
    private Database_Res_Fon database_res_fon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fontaine_firebase);
        listView = findViewById(R.id.listviewFirebase);
        fontaineList = new ArrayList<>();
        database_res_fon = new Database_Res_Fon(list_fontaine_firebase.this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Fontaine");
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
        Intent intent = new Intent(this, MapsActivityFon.class);
        startActivity(intent);
    }

    public void open_activity_Maps2() {
        Intent intent = new Intent(this, Search_LocationFon.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                fontaineList.clear();
                database_res_fon.deleteData();
                for (DataSnapshot FontaineSanpshot : dataSnapshot.getChildren()) {
                    Fontaine fontaine = FontaineSanpshot.getValue(Fontaine.class);
                    database_res_fon = new Database_Res_Fon(list_fontaine_firebase.this);
                    database_res_fon.createTable();
                    database_res_fon.InsertData(fontaine.getId(), fontaine.getNom(), fontaine.getLibelle(), fontaine.getStatut(), null, fontaine.getLat(), fontaine.getLon());
                    fontaineList.add(fontaine);

                }
                FontainesList_Firebase adapter = new FontainesList_Firebase(list_fontaine_firebase.this, fontaineList);
                //listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

