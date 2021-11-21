package com.example.foodhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreen extends AppCompatActivity {
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        preferences=getSharedPreferences("data",MODE_PRIVATE);
        if(preferences.getBoolean("login",false)){
            switch(preferences.getString("user","")){
                case "NGO":{
                    new Handler().postDelayed(() -> {

                        startActivity(new Intent(SplashScreen.this,NgoMain.class));
                        finish();
                    },500);
                    break;
                }
                case "NGOSIGNIN":{
                    new Handler().postDelayed(() -> {

                        startActivity(new Intent(SplashScreen.this,NgoGetInfoActivity.class));
                        finish();
                    },500);
                    break;
                }
                case "RESTAURANT":{
                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(SplashScreen.this,RestuarantMain.class));
                        finish();
                    },500);
                    break;
                }
                case "RESTAURANTSIGNIN":{
                    new Handler().postDelayed(() -> {
                        startActivity(new Intent(SplashScreen.this,RestaurantGetInfoActivity.class));
                        finish();
                    },500);
                    break;
                }

                case"ADMIN":{
                    new Handler().postDelayed(() -> {

                        startActivity(new Intent(SplashScreen.this,AdminMain.class));
                        finish();
                    },500);
                    break;
                }
                default:{
                    new Handler().postDelayed(() -> {

                        startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                        finish();
                    },500);
                    break;
                }
            }
        }
        else{
            new Handler().postDelayed(() -> {

                startActivity(new Intent(SplashScreen.this,LoginActivity.class));
                finish();
            },500);
        }

    }
}