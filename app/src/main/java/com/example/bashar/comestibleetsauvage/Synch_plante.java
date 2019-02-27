package com.example.bashar.comestibleetsauvage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Synch_plante extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synch_plante);

        Activity_ajouter_plante ajouter_plante = new Activity_ajouter_plante();
        /*
        ajouter_plante.getNomPlante();
        ajouter_plante.getLibellePlante();
        ajouter_plante.getStatutPlante();

        tv = (TextView) findViewById(R.id.T1);

        tv.setText(ajouter_plante.getNomPlante());
*/

    }
}