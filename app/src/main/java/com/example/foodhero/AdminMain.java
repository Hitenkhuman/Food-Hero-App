package com.example.foodhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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
        NavController navController = Navigation.findNavController(AdminMain.this, R.id.admincontainer);

        NavigationUI.setupWithNavController(binding.bottomNavbar, navController);

    }
}