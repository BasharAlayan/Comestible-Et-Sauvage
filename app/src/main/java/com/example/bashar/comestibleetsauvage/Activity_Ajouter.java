package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_Ajouter extends AppCompatActivity {
    private Button ajouter_Plante;
    private Button ajouter_Fonatine;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__ajouter);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Ajouter une Fontaine ou une Plante");
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

