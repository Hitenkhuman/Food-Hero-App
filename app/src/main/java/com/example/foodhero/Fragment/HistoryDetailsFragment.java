package com.example.foodhero.Fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.foodhero.Fragment.Restaurant.HistoryFragment;
import com.example.foodhero.Models.Food;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentHistoryDetailsBinding;


public class HistoryDetailsFragment extends Fragment {

   FragmentHistoryDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHistoryDetailsBinding.inflate(LayoutInflater.from(getContext()),container,false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        Food food=(Food)getArguments().getSerializable("data");
        binding.date.setText(food.getDate());
        binding.datedel.setText(food.getDate());
        binding.deliverysts.setText(food.getStatus());
        binding.foodid.setText(food.getFoodid());
        binding.ngoimage.setImageResource(food.getNgoIImgUrl());
        binding.imgrestuarant.setImageResource(food.getResImgUrl());
        binding.type.setText(food.getType());
        binding.description.setText(food.getDiscription());
        binding.noofdishes.setText(Integer.toString(food.getNoOfDishes()));
        binding.ngoname.setText(food.getNgoName());
        binding.restaurantname.setText(food.getResName());
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new HistoryFragment());
                transaction.commit();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}