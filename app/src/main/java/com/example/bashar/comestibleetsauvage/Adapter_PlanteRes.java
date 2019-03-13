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

public class Adapter_PlanteRes extends ArrayAdapter<String>
{

    //Les variables
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


    private int positionID;
    Database_Res db;

    //Firebase
    DatabaseReference databaseReference;

    //Méthode Adapter, servant faire le lien entre les attributs des 2 tables
    public Adapter_PlanteRes(Context context, String[] id, String[] noms, String[] libelles, String[] statut,String[] lat ,String[] lng)
    {
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        FirebaseApp.initializeApp(c);
        if (convertView == null)
        {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_search__location, null);
        }

        // TextView idV;
        final TextView nomV;
        TextView libelleV;
        TextView statutV;

        TextView latV;
        TextView lngV;


        //Initializer les View
        // idV = convertView.findViewById(R.id.id);
        /*
        nomV = convertView.findViewById(R.id.model_nom);
        libelleV = convertView.findViewById(R.id.model_libelle);
        statutV = convertView.findViewById(R.id.model_statut);
        latV = convertView.findViewById(R.id.lat);
        lngV = convertView.findViewById(R.id.lng);

        //ASSIGN DATA
        //idV.setText(id[position]);
        nomV.setText(noms[position]);
        libelleV.setText(libelles[position]);
        statutV.setText(statut[position]);
        latV.setText(lat[position]);
        lngV.setText(lng[position]);
*/
        final int positionID = position;
        /*
        supprimer = convertView.findViewById(R.id.supprimer);
        supprimer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db = new Database_Res(c);
                db.DeleteRow(id[positionID]);
                Toast.makeText(c, "Vous avez supprimé la plante "+noms[positionID] ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c,MainActivity.class);
                c.startActivity(intent);
            }
        });
        */

        return convertView;
    }
}


