package com.example.uob_ar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class GoldneyIntro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goldney_intro);

        View v = findViewById(R.id.goldney_intro);//找到你要设透明背景的layout 的id
        v.getBackground().setAlpha(0);//0~255透明度值 ，0为完全透明，255为不透明
    }
}