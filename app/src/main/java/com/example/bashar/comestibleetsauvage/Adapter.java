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

public class Adapter extends ArrayAdapter<String> {

    String[] id = {};
    String[] noms = {};
    String[] libelles = {};
    String[] statut = {};
    TextView test;
    Context c;
    Adapter adapter;

    LayoutInflater inflater;
    Button supprimer;
    int positionID;
    DataBase_Local db;

    public Adapter(Context context, String[] id, String[] noms, String[] libelles, String[] statut) {
        super(context, R.layout.model, noms);

        this.c = context;

        this.id = id;
        this.noms = noms;
        this.libelles = libelles;
        this.statut = statut;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = inflater.inflate(R.layout.model, null);

            convertView=LayoutInflater.from(getContext()).inflate(R.layout.model,parent,false);



        }
        //TextView idV;
        TextView nomV;
        TextView libelleV;
        TextView statutV;

        //Initializer les View
        //idV = convertView.findViewById(R.id.id);
        nomV = convertView.findViewById(R.id.model_nom);
        libelleV = convertView.findViewById(R.id.model_libelle);
        statutV = convertView.findViewById(R.id.model_statut);


       //ASSIGN DATA
       //idV.setText(id[position]);
        nomV.setText(noms[position]);
        libelleV.setText(libelles[position]);
        statutV.setText(statut[position]);

        positionID = position;
        supprimer = (Button) convertView.findViewById(R.id.supprimer);
        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DataBase_Local(c);
                db.DeleteRow(id[positionID]);
                Toast.makeText(c, "vous avez supprimer la plante "+noms[positionID] ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c,Synch_plante.class);
                c.startActivity(intent);
            }
        });
        return convertView;
    }
}
