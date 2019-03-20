package com.example.bashar.comestibleetsauvage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Adapter_FontaineRes extends ArrayAdapter<String> {

    //Les variables
    private String[] id = {};
    private String[] noms = {};
    private String[] libelles = {};
    private String[] statut = {};
    private String[] lat = {};
    private String[] lng = {};
    private Context c;

    private LayoutInflater inflater;
    private Button supprimer;

    //Méthode Adapter, servant faire le lien entre les attributs des 2 tables
    public Adapter_FontaineRes(Context context, String[] id, String[] noms, String[] libelles, String[] statut, String[] lat, String[] lng) {
        super(context, R.layout.resultplante, noms);

        this.c = context;
        this.id = id;
        this.noms = noms;
        this.libelles = libelles;
        this.statut = statut;

        this.lat = lat;
        this.lng = lng;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FirebaseApp.initializeApp(c);
        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.resultplante, null);
        }

        // TextView idV;
        final TextView nomV;
        TextView libelleV;
        TextView statutV;

        nomV = convertView.findViewById(R.id.model_nom_Res);
        libelleV = convertView.findViewById(R.id.model_libelle_Res);
        statutV = convertView.findViewById(R.id.model_statut_Res);

        final int positionID = position;
        supprimer = convertView.findViewById(R.id.supprimer_Res);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Plantes");

                databaseReference.child(id[positionID]).removeValue();
                Toast.makeText(c, "Vous avez supprimé la plante ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c, MainActivity.class);

                c.startActivity(intent);
            }
        });


        //ASSIGN DATA
        nomV.setText(noms[position]);
        libelleV.setText("La Libellé : " + libelles[position]);
        statutV.setText("Le Statut : " + statut[position]);

        return convertView;
    }
}