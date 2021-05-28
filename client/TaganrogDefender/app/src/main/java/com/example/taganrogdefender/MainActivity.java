package com.example.taganrogdefender;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity  {

    Boolean have_passport = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if(have_passport) {
            findViewById(R.id.buy_passport_block).setVisibility(View.GONE);
            findViewById(R.id.passport_block).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.buy_passport_block).setVisibility(View.VISIBLE);
            findViewById(R.id.passport_block).setVisibility(View.GONE);
        }
    }
}