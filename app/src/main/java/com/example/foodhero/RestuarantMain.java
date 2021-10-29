package com.example.foodhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodhero.Fragment.Restaurant.HistoryFragment;
import com.example.foodhero.Fragment.Restaurant.HomeFragment;
import com.example.foodhero.Fragment.Restaurant.MyProfileFragment;
import com.example.foodhero.Fragment.Restaurant.RequestFragment;
import com.example.foodhero.databinding.ActivityRestuarantMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestuarantMain extends AppCompatActivity {
    FragmentTransaction transaction;
   ActivityRestuarantMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRestuarantMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();
        binding.bottomNavbar.setSelectedItemId(0);
        binding.bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.home:
                        transaction.replace(R.id.container,new HomeFragment());
                        break;
                    case R.id.request:
                        transaction.replace(R.id.container,new RequestFragment());
                        break;
                    case R.id.history:
                        transaction.replace(R.id.container,new HistoryFragment());

                        break;
                    case R.id.myprofile:
                        transaction.replace(R.id.container,new MyProfileFragment());
                        break;
                }
                transaction.commit();

                return true;
            }
        });

    }
}