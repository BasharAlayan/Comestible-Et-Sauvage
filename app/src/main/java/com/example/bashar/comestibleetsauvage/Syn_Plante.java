package com.example.bashar.comestibleetsauvage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bashar.comestibleetsauvage.model.Plante;

import java.util.ArrayList;

public class Syn_Plante extends AppCompatActivity {
    private App app;
    ArrayList<Plante> plantes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syn__plante);
        app = ((App)getApplication());
        plantes = Plante.getAllNonSyncedPlante();
    }
}
