package com.example.bashar.comestibleetsauvage;

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

public class AdapteFontaine extends ArrayAdapter<String> {

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
    private Button transmettre;
    private int positionID;
    private DataBase_Local_Fontaine db;

    //Firebase
    DatabaseReference databaseReference;

    //Méthode Adapter, servant faire le lien entre les attributs des 2 tables
    public AdapteFontaine(Context context, String[] id, String[] noms, String[] libelles, String[] statut, String[] lat, String[] lng) {

        super(context, R.layout.model_fontaine, noms);

        this.c = context;
        this.id = id;
        this.noms = noms;
        this.libelles = libelles;
        this.statut = statut;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FirebaseApp.initializeApp(c);
        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.model_fontaine, null);
        }

        // TextView idV;
        final TextView nomV;
        TextView libelleV;
        TextView statutV;

        TextView latV;
        TextView lngV;


        //Initializer les View
        // idV = convertView.findViewById(R.id.id);
        //latV = convertView.findViewById(R.id.lat);
        //lngV = convertView.findViewById(R.id.lng);
        nomV = convertView.findViewById(R.id.model_nom_Fontaine);
        libelleV = convertView.findViewById(R.id.model_libelle_Fontaine);
        statutV = convertView.findViewById(R.id.model_statut_Fontaine);


        //ASSIGN DATA
        //idV.setText(id[position]);
        nomV.setText(noms[position]);
        libelleV.setText("La Libellé : " + libelles[position]);
        statutV.setText("Le Statut : " + statut[position]);
        //latV.setText(lat[position]);
        //lngV.setText(lng[position]);

        final int positionID = position;
        supprimer = convertView.findViewById(R.id.supprimer_Fontaine);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DataBase_Local_Fontaine(c);
                db.DeleteRow(id[positionID]);
                Toast.makeText(c, "Vous avez supprimé la plante " + noms[positionID], Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c, MainActivity.class);
                c.startActivity(intent);
            }
        });

        transmettre = convertView.findViewById(R.id.publier_Fontaine);
        transmettre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creation du child dans l'objet root
                databaseReference = FirebaseDatabase.getInstance().getReference("Fontaine");

                Fontaine fontaine = new Fontaine(id[positionID], noms[positionID], libelles[positionID], statut[positionID], null, lat[positionID], lng[positionID]);
                databaseReference.push().setValue(fontaine);

                db = new DataBase_Local_Fontaine(c);
                db.DeleteRow(id[positionID]);

                Toast.makeText(c, "Vous avez transmis la plante " + noms[positionID], Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c, MainActivity.class);
                c.startActivity(intent);
            }
        });
        return convertView;
    }
}
