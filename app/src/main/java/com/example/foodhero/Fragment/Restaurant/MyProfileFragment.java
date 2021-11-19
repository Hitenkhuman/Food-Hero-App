package com.example.foodhero.Fragment.Restaurant;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodhero.LoginActivity;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentMyProfileBinding;


public class MyProfileFragment extends Fragment {

    FragmentMyProfileBinding binding;
    FragmentTransaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentMyProfileBinding.inflate(LayoutInflater.from(getContext()),container,false);
        binding.profileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new UpdateMyProfileRestaurant());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return binding.getRoot();
    }
}