package com.example.uobar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button QRScanBtn, QRGenerateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QRScanBtn = findViewById(R.id.QRScanBtn);
        QRScanBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QRScanner.class)));
        QRGenerateBtn = findViewById(R.id.QRGenerateBtn);
        QRGenerateBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QRGenerator.class)));
    }


}