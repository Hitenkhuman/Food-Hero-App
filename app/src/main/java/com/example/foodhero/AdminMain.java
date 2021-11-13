package com.example.foodhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodhero.Fragment.Ngo.NgoHistory;
import com.example.foodhero.Fragment.Ngo.NgoHome;
import com.example.foodhero.Fragment.Ngo.NgoMyProfile;
import com.example.foodhero.Fragment.Ngo.NgoRequest;
import com.example.foodhero.Fragment.admin.AdminHistory;
import com.example.foodhero.Fragment.admin.AdminNgo;
import com.example.foodhero.Fragment.admin.AdminRestaurant;
import com.example.foodhero.Fragment.admin.AdminVerificationRequests;
import com.example.foodhero.databinding.ActivityAdminMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMain extends AppCompatActivity {
    ActivityAdminMainBinding binding;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.admincontainer,new AdminVerificationRequests());
        transaction.commit();
        binding.bottomNavbar.setSelectedItemId(0);
        binding.bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.home:
                        transaction.replace(R.id.admincontainer,new AdminVerificationRequests());
                        break;
                    case R.id.history:
                        transaction.replace(R.id.admincontainer,new AdminHistory());
                        break;
                    case R.id.ngo:
                        transaction.replace(R.id.admincontainer,new AdminNgo());

                        break;
                    case R.id.restaurant:
                        transaction.replace(R.id.admincontainer,new AdminRestaurant());
                        break;
                }
                transaction.commit();

                return true;
            }
        });
    }
}