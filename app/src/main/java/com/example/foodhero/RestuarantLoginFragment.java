package com.example.foodhero;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodhero.databinding.FragmentRestuarantLoginBinding;


public class RestuarantLoginFragment extends Fragment {

    FragmentRestuarantLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRestuarantLoginBinding.inflate(LayoutInflater.from(getContext()),container,false);
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),SigninActivity.class));
            }
        });
        return binding.getRoot();
    }
}