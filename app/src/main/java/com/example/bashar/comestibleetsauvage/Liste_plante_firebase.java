package com.example.bashar.comestibleetsauvage;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
public class Liste_plante_firebase extends AppCompatActivity
{
    private ListView listView;
    private List<Plante> planteList;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_plante_firebase);
        listView = findViewById(R.id.listviewFirebase);
        planteList=new ArrayList<>();
        databaseReference=FirebaseDatabase.getInstance().getReference("Plantes");
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                planteList.clear();
                for(DataSnapshot planteSanpshot: dataSnapshot.getChildren()){
                    Plante plante=planteSanpshot.getValue(Plante.class);
                    planteList.add(plante);

                }

                PlantesList_Firebase adapter=new PlantesList_Firebase(Liste_plante_firebase.this,planteList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
