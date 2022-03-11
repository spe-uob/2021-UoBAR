package com.example.uobar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private Button QRScanBtn, QRGenerateBtn, MapBtn;
    private ImageButton GoldneyInfoBtn, RoyalInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //these are just temporary buttons that allows to go to different classes with various features
        QRScanBtn = findViewById(R.id.QRScanBtn);
        QRScanBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QRScanner.class)));
        QRGenerateBtn = findViewById(R.id.QRGenerateBtn);
        QRGenerateBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QRGenerator.class)));
        GoldneyInfoBtn = findViewById(R.id.GoldneyID);
        GoldneyInfoBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), GoldneyButton.class)));
        RoyalInfoBtn = findViewById(R.id.RoyalID);
        RoyalInfoBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RoyalFortButton.class)));
        MapBtn = findViewById(R.id.MapBtn);
        MapBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MapsActivity.class)));

    }


}