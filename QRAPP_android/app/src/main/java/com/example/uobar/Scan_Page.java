package com.example.uobar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class Scan_Page extends AppCompatActivity {

    private Button QRScanBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);

        QRScanBtn = findViewById(R.id.QRScanBtn);
        QRScanBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QRScanner.class)));
    }

}