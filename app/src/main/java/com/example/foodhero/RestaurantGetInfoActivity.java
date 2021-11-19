package com.example.foodhero;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.foodhero.databinding.ActivityRestaurantGetInfoBinding;


public class RestaurantGetInfoActivity extends AppCompatActivity {
    ActivityRestaurantGetInfoBinding binding;
    ActivityResultLauncher<String> gallary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRestaurantGetInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle bundle=getIntent().getExtras();

        gallary = registerForActivityResult(new ActivityResultContracts.GetContent()
                , new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        binding.profileImg.setImageURI(uri);
                    }
                });

        binding.uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallary.launch("images/*");
            }
        });


    }
}