package com.example.bashar.comestibleetsauvage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6AAAFF")));
        actionBar.setTitle("Bienvenue ...");
        EasySplashScreen config = new EasySplashScreen(SplashScreen.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#D8F1FF"))
                .withLogo(R.drawable.logoplantes)
                .withFooterText("Copyright 2019")
                .withAfterLogoText("Comestible et Sauvage");

        //set text color
        config.getAfterLogoTextView().setTextColor(Color.parseColor("#3F51B5"));
        config.getAfterLogoTextView().setTextColor(Color.parseColor("#3F51B5"));
        config.getFooterTextView().setTextColor(Color.parseColor("#3F51B5"));

        config.getLogo().setPadding(120, 120, 120, 120);
        config.getAfterLogoTextView().setTextSize(30);

        //set to view
        View view = config.create();

        //set view to content view
        setContentView(view);

    }
}
