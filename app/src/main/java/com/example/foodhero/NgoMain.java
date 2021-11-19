package com.example.foodhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodhero.Fragment.Ngo.NgoHistory;
import com.example.foodhero.Fragment.Ngo.NgoHome;
import com.example.foodhero.Fragment.Ngo.NgoMyProfile;
import com.example.foodhero.Fragment.Ngo.NgoRequest;
import com.example.foodhero.Fragment.Restaurant.HistoryFragment;
import com.example.foodhero.Fragment.Restaurant.HomeFragment;
import com.example.foodhero.Fragment.Restaurant.MyProfileFragment;
import com.example.foodhero.Fragment.Restaurant.RequestFragment;
import com.example.foodhero.databinding.ActivityNgoMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NgoMain extends AppCompatActivity {
    FragmentTransaction transaction;
    ActivityNgoMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNgoMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ngocontainer,new NgoHome());
        transaction.commit();
        binding.bottomNavbar.setSelectedItemId(0);
        binding.bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.home:
                        transaction.replace(R.id.ngocontainer,new NgoHome());
                        break;
                    case R.id.request:
                        transaction.replace(R.id.ngocontainer,new NgoRequest());
                        break;
                    case R.id.history:
                        transaction.replace(R.id.ngocontainer,new NgoHistory());

                        break;
                    case R.id.myprofile:
                        transaction.replace(R.id.ngocontainer,new NgoMyProfile());
                        break;
                }
                transaction.commit();

                return true;
            }
        });
    }
}