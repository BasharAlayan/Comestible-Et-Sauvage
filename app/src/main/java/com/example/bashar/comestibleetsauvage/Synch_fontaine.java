package com.example.bashar.comestibleetsauvage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bashar.comestibleetsauvage.model.Fontaine;
import com.example.bashar.comestibleetsauvage.model.Plante;

import java.util.ArrayList;

public class Synch_fontaine extends AppCompatActivity {
    private App app;
    ArrayList<Fontaine> fontaines = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synch_fontaine);
        app = ((App)getApplication());
        fontaines = Fontaine.getAllNonSyncedFontaine();
    }
}
