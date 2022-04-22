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
        GoldneyInfoBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), GoldneyButton.class)));
        RoyalInfoBtn = findViewById(R.id.RoyalID);
        RoyalInfoBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RoyalFortButton.class)));;
    }


}