package com.example.uobar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private ImageButton GoldneyInfoBtn, RoyalInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoldneyInfoBtn = findViewById(R.id.GoldneyID);
        GoldneyInfoBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MapsActivity.class);
            intent.putExtra("area", "Goldeney");
            startActivity(intent);
        });
        RoyalInfoBtn = findViewById(R.id.RoyalID);
        RoyalInfoBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MapsActivity.class);
            intent.putExtra("area", "RylFort");
            startActivity(intent);
        });
    }


}