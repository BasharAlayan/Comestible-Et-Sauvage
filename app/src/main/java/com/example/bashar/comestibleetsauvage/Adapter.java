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

public class Adapter extends ArrayAdapter<String> {

    String[] id = {};
    String[] noms = {};
    String[] libelles = {};
    String[] statut = {};
    String[] lat = {};
    String[] lng = {};
    TextView test;
    Context c;
    Adapter adapter;

    LayoutInflater inflater;
    Button supprimer;
    Button transmettre;
    private int positionID;
    DataBase_Local db;

    //firebase

    DatabaseReference databaseReference;

    public Adapter(Context context, String[] id, String[] noms, String[] libelles, String[] statut,String[] lat ,String[] lng) {
        super(context, R.layout.model, noms);

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
            convertView = inflater.inflate(R.layout.model, null);
        }
        // TextView idV;
        final TextView nomV;
        TextView libelleV;
        TextView statutV;

        TextView latV;
        TextView lngV;


        //Initializer les View
        // idV = convertView.findViewById(R.id.id);
        nomV = convertView.findViewById(R.id.model_nom);
        libelleV = convertView.findViewById(R.id.model_libelle);
        statutV = convertView.findViewById(R.id.model_statut);
        latV = convertView.findViewById(R.id.lat);
        lngV = convertView.findViewById(R.id.lng);



        //ASSIGN DATA
        //  idV.setText(id[position]);
        nomV.setText(noms[position]);
        libelleV.setText(libelles[position]);
        statutV.setText(statut[position]);
        latV.setText(lat[position]);
        lngV.setText(lng[position]);


        final int positionID = position;
        supprimer = (Button) convertView.findViewById(R.id.supprimer);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DataBase_Local(c);
                db.DeleteRow(id[positionID]);
                Toast.makeText(c, "Vous avez supprimé la plante "+noms[positionID] ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c,MainActivity.class);
                c.startActivity(intent);
            }
        });

        transmettre = (Button) convertView.findViewById(R.id.publier);
        transmettre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//create child in root object
                databaseReference=FirebaseDatabase.getInstance().getReference("Plantes/" +id[positionID]);
//assigne values to the child object
                databaseReference.child("idPlante").setValue(id[positionID]);
                databaseReference.child("nomsPlante").setValue(noms[positionID]);
                databaseReference.child("libellePlante").setValue(libelles[positionID]);
                databaseReference.child("statutPlante").setValue(statut[positionID]);
                databaseReference.child("imagePlante").setValue(null);
                databaseReference.child("latPlante").setValue(lat[positionID]);
                databaseReference.child("lngPlante").setValue(lng[positionID]);

                db = new DataBase_Local(c);
                db.DeleteRow(id[positionID]);

                Toast.makeText(c, "Vous avez transmis la plante "+noms[positionID] ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c,MainActivity.class);
                c.startActivity(intent);

                //Plante plante=new Plante(noms[positionID],libelles[positionID],statut[positionID],null,lat[positionID],lng[positionID]);

                //Use this method to create unique id of plante
                //databaseReference.push().setValue(plante);


            }
        });
        return convertView;
    }
}
