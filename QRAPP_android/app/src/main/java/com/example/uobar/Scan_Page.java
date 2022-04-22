package com.example.uobar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class Scan_Page extends AppCompatActivity {

    private Button QRScanBtn;
    ImageButton back_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_page);

        back_info = findViewById(R.id.scan_back);
        back_info.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));

        QRScanBtn = findViewById(R.id.QRScanBtn);
        QRScanBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QRScanner.class)));
    }

}