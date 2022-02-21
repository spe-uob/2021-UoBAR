package com.example.uobar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GoldneyButton extends AppCompatActivity {

   TextView goldneyInfo;
   Button  goldneyBttn;
   @Override
    protected  void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       setContentView(R.layout.garden_info);
       goldneyInfo =findViewById(R.id.gardenInfoID);
       goldneyBttn =findViewById(R.id.RoyalID);


       goldneyInfo.setText("The text which contains information about the Goldney garden");

   }


}

