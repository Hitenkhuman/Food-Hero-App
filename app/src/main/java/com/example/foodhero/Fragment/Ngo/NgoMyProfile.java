package com.example.foodhero.Fragment.Ngo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Fragment.Restaurant.UpdateMyProfileRestaurant;
import com.example.foodhero.LoginActivity;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentNgoMyProfileBinding;
import com.google.firebase.messaging.FirebaseMessaging;

public class NgoMyProfile extends Fragment {
    FragmentTransaction transaction;
   FragmentNgoMyProfileBinding binding;
    SharedPreferences preferences;
    NavController navController;
    String city;
    private final String parentdir= ApiClient.BASE_URL+"profile_pic/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNgoMyProfileBinding.inflate(LayoutInflater.from(getContext()),container,false);

        preferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        city=preferences.getString("city","");
        SharedPreferences.Editor edit=preferences.edit();
        navController = Navigation.findNavController(getActivity(), R.id.ngocontainer);
        Glide.with(getContext()).load(parentdir+preferences.getString("imgurl","default.png")).into(binding.img);
        binding.address.setText(preferences.getString("address","NA"));
        binding.openingtime.setText(preferences.getString("openingtime","NA"));
        binding.closingtime.setText(preferences.getString("closingtime","NA"));
        binding.name.setText(preferences.getString("name","NA"));
        binding.namebig.setText(preferences.getString("name","NA"));
        binding.mobile.setText(preferences.getString("mobile","NA"));
        binding.email.setText(preferences.getString("email","NA"));
        binding.profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_ngoMyProfile_to_updateMyProfileNgo);

            }
        });

        binding.profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.clear();
                edit.apply();
                FirebaseMessaging.getInstance().unsubscribeFromTopic(city);
                getActivity().finish();
                Intent intent=new Intent(getContext(),LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}