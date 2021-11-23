package com.example.foodhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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
        NavController navController = Navigation.findNavController(this, R.id.ngocontainer);
        NavigationUI.setupWithNavController(binding.bottomNavbar, navController);

    }
}