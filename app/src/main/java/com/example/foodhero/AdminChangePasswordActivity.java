package com.example.foodhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.foodhero.databinding.ActivityAdminChangePasswordBinding;

public class AdminChangePasswordActivity extends AppCompatActivity {
    ActivityAdminChangePasswordBinding binding;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences=getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }
}