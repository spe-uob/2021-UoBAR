package com.example.qrdecoder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button QRGenerateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QRGenerateBtn = findViewById(R.id.QRGenerateBtn);
        QRGenerateBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), QRGenerator.class)));
    }
}