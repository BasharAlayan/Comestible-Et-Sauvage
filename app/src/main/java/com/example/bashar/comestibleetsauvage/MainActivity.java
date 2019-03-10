package com.example.bashar.comestibleetsauvage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private Button ajouter;
    private Button rechercher;
    private Button syn;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar=getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Accueil");
        ajouter=(Button) findViewById(R.id.Ajouter);
        ajouter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_activity_ajouter();
            }
        });

        rechercher=(Button) findViewById(R.id.Rechercher);
        rechercher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_activity_Rechercher();
            }
        });


        syn=(Button) findViewById(R.id.Synchro);
        syn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            open_activity_Syn();
        }
    });
}

    public void open_activity_ajouter(){
        Intent intent = new Intent(this,Activity_Ajouter.class);
        startActivity(intent);
    }
    public void open_activity_Rechercher(){
        Intent intent = new Intent(this,Activity_rechercher.class);
        startActivity(intent);
    }
    public void open_activity_Syn(){
        Intent intent = new Intent(this,Synchronisation.class);
        startActivity(intent);
    }
}
