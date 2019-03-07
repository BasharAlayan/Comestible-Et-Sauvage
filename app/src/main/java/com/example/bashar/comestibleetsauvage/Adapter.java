package com.example.bashar.comestibleetsauvage;

import android.content.Context;
import android.os.Build;
import android.support.v4.widget.ViewDragHelper;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adapter extends ArrayAdapter<String> {


    String[] noms={};
    String[] libelles={};
    String[] statut={};
    Context c;
    LayoutInflater inflater;

    public Adapter(Context context,String [] noms,String [] libelles,String[] statut) {
        super(context, R.layout.model,noms);

        this.c=context;
        this.noms=noms;
        this.libelles=libelles;
        this.statut=statut;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
                    inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.model,null);

//LayoutInflater inflater = LayoutInflater.from()

        TextView nomV;
        TextView libelleV;
        TextView statutV;

        //Objet ViewHolder
        //ViewHolder holder=new ViewHolder();

        //Initializer les View
        nomV=convertView.findViewById(R.id.model_nom);
        libelleV=convertView.findViewById(R.id.model_libelle);
        statutV=convertView.findViewById(R.id.model_statut);

        //ASSIGN DATA
        nomV.setText(noms[position]);
        libelleV.setText(libelles[position]);
        statutV.setText(statut[position]);

    return convertView;
    }

    public class ViewHolder{
        TextView nomV;
        TextView libelleV;
        TextView statutV;
    }
}
