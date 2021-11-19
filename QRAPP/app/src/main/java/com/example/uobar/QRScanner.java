package com.example.uobar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Scanner;

public class QRScanner extends AppCompatActivity {

    CodeScanner mScanner;
    CodeScannerView scanner;
    TextView scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        scanner = findViewById(R.id.scanner);
        mScanner = new CodeScanner(this, scanner);
        scanResult = findViewById(R.id.scanResult);

        //decodes the qr code
        mScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                //when the qr code is decoded, it is stored in the result variable
                //uses threading to make it more efficient
                runOnUiThread(() -> scanResult.setText(result.getText()));
            }
        });
        scanner.setOnClickListener(v -> mScanner.startPreview());
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraRequest();
    }

    //sends the user permission to use the camera
    private void cameraRequest() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            //user accepts the camera perms
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                mScanner.startPreview();
            }
            //user declines camera perms
            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(QRScanner.this,
                        "We need to use the camera inorder to scan the qr code",
                        Toast.LENGTH_SHORT).show();
            }
            //keeps asking for the permission until it is accepted
            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    public void onPause() {
        mScanner.releaseResources();
        super.onPause();
    }

}