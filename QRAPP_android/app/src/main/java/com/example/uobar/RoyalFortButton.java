package com.example.uobar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RoyalFortButton extends AppCompatActivity {

    TextView royalInfo;
    ImageButton royalBttn, next;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.garden_info);
        royalInfo =findViewById(R.id.gardenInfoID);
        royalBttn =findViewById(R.id.GoldneyID);


        royalInfo.setText("The text which contains information about the Royal garden");

        next = findViewById(R.id.Next_scan);
        next.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),scan_page.class)));

    }
}
