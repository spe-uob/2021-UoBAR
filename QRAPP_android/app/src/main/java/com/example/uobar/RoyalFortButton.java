package com.example.uobar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RoyalFortButton extends AppCompatActivity {

    TextView royalInfo;
    Button royalBttn;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.garden_info);
        royalInfo =findViewById(R.id.gardenInfoID);
        royalBttn =findViewById(R.id.GoldneyID);


        royalInfo.setText("The text which contains information about the Royal garden");

    }
}
