package com.example.bashar.comestibleetsauvage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FontainesList_Firebase extends ArrayAdapter<Fontaine> {

    private Activity context;
    private List<Fontaine> fontaineList;


    public FontainesList_Firebase(Activity context, List<Fontaine> fontaineList) {
        super(context, R.layout.model_firebase_fontaine, fontaineList);
        this.context = context;
        this.fontaineList = fontaineList;
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

        Fontaine fontaine = fontaineList.get(position);


        nom.setText(fontaine.getNom());
        libelle.setText(fontaine.getLibelle());
        statut.setText(fontaine.getStatut());
        id.setText(fontaine.getId());
        lat.setText(fontaine.getLat());
        lng.setText(fontaine.getLon());

        return listViewitem;
    }
}
