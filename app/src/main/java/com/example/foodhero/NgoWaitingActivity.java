package com.example.foodhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.foodhero.databinding.ActivityNgoWaitingBinding;

public class NgoWaitingActivity extends AppCompatActivity {

    ActivityNgoWaitingBinding binding;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNgoWaitingBinding.inflate(getLayoutInflater());
        preferences=getSharedPreferences("data",MODE_PRIVATE);
        editor=preferences.edit();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                editor.clear();
                editor.apply();
                finish();
            }
        });
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.clear();
        editor.apply();
    }
}