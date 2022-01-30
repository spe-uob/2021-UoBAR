package com.example.uobar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRGenerator extends AppCompatActivity {

    private EditText QRInputText;
    private Button QRGeneratorBtn, generatorBackBtn;
    private ImageButton saveQRBtn;
    private ImageView QRImage;
    private Bitmap QRMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgenerator);

        QRInputText = findViewById(R.id.QRInputText);
        QRGeneratorBtn = findViewById(R.id.QRGeneratorBtn);
        generatorBackBtn = findViewById(R.id.generatorBackBtn);
        QRImage = findViewById(R.id.QRImage);
        saveQRBtn = findViewById(R.id.saveQRBtn);

        QRGeneratorBtn.setOnClickListener(v -> {
            String text = QRInputText.getText().toString();
            if (text.isEmpty()){
                Toast.makeText(QRGenerator.this, "Input is required", Toast.LENGTH_SHORT).show();
            } else {
                QRGEncoder qrgEncoder = new QRGEncoder(text, null, QRGContents.Type.TEXT, 500);
                QRMap = qrgEncoder.getBitmap();
                QRImage.setImageBitmap(QRMap);
            }
        });

        ActivityCompat.requestPermissions(QRGenerator.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(QRGenerator.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);


        saveQRBtn.setOnClickListener(v -> {

            try {
                File savDir = new File(Environment.getExternalStorageDirectory().toString() + File.separator + "generatedQR" + File.separator);
                savDir.mkdirs();
                File file = new File(savDir, QRInputText.getText().toString()+".jpg");
                FileOutputStream fOut = new FileOutputStream(file);
                QRMap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                fOut.flush();
                fOut.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        });

        generatorBackBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MainActivity.class)));
    }
}