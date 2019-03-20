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

/**
 * Classe Adapter, servant à faire le lien entre SQLlite et firebase
 */
public class Adapter extends ArrayAdapter<String>
{

    //Les variables
    private String[] id = {};
    private String[] noms = {};
    private String[] libelles = {};
    private String[] statut = {};
    private String[] lat = {};
    private String[] lng = {};
    private TextView test;
    private Context c;
    private Adapter adapter;

    private LayoutInflater inflater;
    private Button supprimer;
    private Button transmettre;
    private int positionID;
    private DataBase_Local db;

    //Firebase
    DatabaseReference databaseReference;

    //Méthode Adapter, servant faire le lien entre les attributs des 2 tables
    public Adapter(Context context, String[] id, String[] noms, String[] libelles, String[] statut,String[] lat ,String[] lng)
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        FirebaseApp.initializeApp(c);
        if (convertView == null)
        {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.model, null);
        }

        // TextView idV;
        final TextView nomV;
        TextView libelleV;
        TextView statutV;

        nomV = convertView.findViewById(R.id.model_nom);
        libelleV = convertView.findViewById(R.id.model_libelle);
        statutV = convertView.findViewById(R.id.model_statut);

        //ASSIGN DATA
        nomV.setText(noms[position]);
        libelleV.setText("La Libellé : "+libelles[position]);
        statutV.setText("Le Statut : "+statut[position]);

        final int positionID = position;
        supprimer = convertView.findViewById(R.id.supprimer);
        supprimer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db = new DataBase_Local(c);
                db.DeleteRow(id[positionID]);
                Toast.makeText(c, "Vous avez supprimé la plante "+noms[positionID] ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c,MainActivity.class);
                c.startActivity(intent);
            }
        });

        transmettre = convertView.findViewById(R.id.publier);
        transmettre.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //creation du child dans l'objet root
                databaseReference=FirebaseDatabase.getInstance().getReference("Plantes");

                Plante plante =new Plante(id[positionID],noms[positionID],libelles[positionID],statut[positionID],null,lat[positionID],lng[positionID]);
                databaseReference.push().setValue(plante);

                db = new DataBase_Local(c);

                db.DeleteRow(id[positionID]);

                Toast.makeText(c, "Vous avez transmis la plante "+noms[positionID] ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c,MainActivity.class);
                c.startActivity(intent);
            }
        });
        return convertView;
    }
}
