package com.example.foodhero.Fragment.Ngo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodhero.Fragment.Restaurant.UpdateMyProfileRestaurant;
import com.example.foodhero.LoginActivity;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentNgoMyProfileBinding;

public class NgoMyProfile extends Fragment {
    FragmentTransaction transaction;
   FragmentNgoMyProfileBinding binding;
    SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNgoMyProfileBinding.inflate(LayoutInflater.from(getContext()),container,false);
        preferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=preferences.edit();
        binding.address.setText(preferences.getString("address","NA"));
        binding.name.setText(preferences.getString("name","NA"));
        binding.mobile.setText(preferences.getString("mobile","NA"));
        binding.email.setText(preferences.getString("email","NA"));
        binding.profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.ngocontainer,new UpdateMyProfileNgo());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.clear();
                edit.apply();
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return binding.getRoot();
    }
}