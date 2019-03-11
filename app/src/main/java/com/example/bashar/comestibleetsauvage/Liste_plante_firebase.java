package com.example.bashar.comestibleetsauvage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Classe servant à récupérer des plantes depuis la BDD distante
 */
public class Liste_plante_firebase extends AppCompatActivity
{

    //les variables de classe
    private ListView listView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_plante_firebase);

        listView= findViewById(R.id.listviewFirebase);

        //FireBase
        initFirebase();
        addEventFirebaseListener();
    }

    private void addEventFirebaseListener()
    {

    }

    //Initialisation
    private void initFirebase()
    {
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("EDMT_FIREBASE");
    }
}
