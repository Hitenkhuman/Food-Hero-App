package com.example.foodhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foodhero.databinding.ActivityNgoWaitingBinding;

public class NgoWaitingActivity extends AppCompatActivity {

    ActivityNgoWaitingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNgoWaitingBinding.inflate(getLayoutInflater());
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        setContentView(binding.getRoot());
    }
}