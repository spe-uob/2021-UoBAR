package com.example.uobar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class Welcome extends AppCompatActivity {
    Button welcome = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcome = findViewById(R.id.Welcome);
        ActivityCompat.requestPermissions(Welcome.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        Toast.makeText(getApplicationContext(),"lat : " + location.getLatitude() + " long : " + location.getLongitude(), Toast.LENGTH_LONG).show();
                        if((location.getLatitude() < 51.45303) && (location.getLatitude() > 51.45134) && (location.getLongitude() > -2.61340) && (location.getLongitude() < -2.61484)){
                            welcome.setOnClickListener(view -> {
                                Intent intent = new Intent();
                                intent.setClass(Welcome.this, GoldneyButton.class);
                                startActivity(intent);
                            });
                        }else if((location.getLatitude() < 51.45930) && (location.getLatitude() > 51.45699) && (location.getLongitude() > -2.60283) && (location.getLongitude() < -2.60089)){
                            welcome.setOnClickListener(view -> {
                                Intent intent = new Intent();
                                intent.setClass(Welcome.this, RoyalFortButton.class);
                                startActivity(intent);
                            });
                        }else{
                            welcome.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));
                        }
                    });
        }else {
            Toast.makeText(getApplicationContext(),"Error fetching location", Toast.LENGTH_LONG).show();
            welcome.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));
        }
    }
}
