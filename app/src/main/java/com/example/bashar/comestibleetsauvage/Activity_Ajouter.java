package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_Ajouter extends AppCompatActivity {
    private Button ajouter_Plante;
    private Button ajouter_Fonatine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__ajouter);
        ajouter_Plante=(Button) findViewById(R.id.Aj_Plante);
        ajouter_Plante.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_activity_ajouter_P();
            }
        });
        ajouter_Fonatine=(Button) findViewById(R.id.Aj_Fontaine);
        ajouter_Fonatine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_activity_ajouter_F();
            }
        });
    }

    public void open_activity_ajouter_P(){
        Intent intent = new Intent(this,Activity_ajouter_plante.class);
        startActivity(intent);
    }
    public void open_activity_ajouter_F(){
        Intent intent = new Intent(this,activity_ajouter_fontaine.class);
        startActivity(intent);
    }
}

