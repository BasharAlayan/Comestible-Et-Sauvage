package com.example.bashar.comestibleetsauvage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PlantesList_Firebase extends ArrayAdapter<Plante> {

    private Activity context;
    private List<Plante> planteList;
    Database_Res database_res;



    public PlantesList_Firebase(Activity context, List<Plante> planteList) {
        super(context, R.layout.model_firebase_plante, planteList);
        this.context = context;
        this.planteList = planteList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflate = context.getLayoutInflater();
        View listViewitem = inflate.inflate(R.layout.model_firebase_plante, null, true);

        TextView nom = (TextView) listViewitem.findViewById(R.id.NomPlanteFirebase);
        TextView libelle = (TextView) listViewitem.findViewById(R.id.LibellePlanteFirebase);
        TextView statut = (TextView) listViewitem.findViewById(R.id.StatutPlanteFirebase);
        TextView id = (TextView) listViewitem.findViewById(R.id.IdPlanteFirebase);
        TextView lat = (TextView) listViewitem.findViewById(R.id.LatPlanteFirebase);
        TextView lng = (TextView) listViewitem.findViewById(R.id.LngPlanteFirebase);

        Plante plante = planteList.get(position);


        nom.setText(plante.getNom());
        libelle.setText(plante.getLibelle());
        statut.setText(plante.getStatut());
        id.setText(plante.getId());
        lat.setText(plante.getLat());
        lng.setText(plante.getLon());

        return listViewitem;
    }
}
