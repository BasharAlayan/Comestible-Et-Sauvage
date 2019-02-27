package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Synchronisation extends AppCompatActivity {

    private Button Plante;
    private Button Fonatine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronisation);
        Plante=(Button) findViewById(R.id.Syn_Plante);
        Plante.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_activity_Syn_P();
            }
        });

        Fonatine=(Button) findViewById(R.id.Syn_Fontaine);
        Fonatine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_activity_Syn_F();
            }
        });
    }

    public void open_activity_Syn_P(){
        Intent intent = new Intent(this,Synch_plante.class);
        startActivity(intent);
    }
    public void open_activity_Syn_F(){
        Intent intent = new Intent(this,Synch_fontaine.class);
        startActivity(intent);
    }
}

