package com.example.bashar.comestibleetsauvage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Activity_rechercher extends AppCompatActivity {

    private Button ma_position;
    private Button recherche_position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher);
        ma_position=(Button) findViewById(R.id.Ma_Position);
        ma_position.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_activity_Maps();
            }
        });
        recherche_position=(Button) findViewById(R.id.Choisir_Lieu);
        recherche_position.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_activity_Maps2();
            }
        });
    }

    public void open_activity_Maps(){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }
    public void open_activity_Maps2(){
        Intent intent = new Intent(this,Search_Location.class);
        startActivity(intent);

    }
}