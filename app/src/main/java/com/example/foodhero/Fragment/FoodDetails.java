package com.example.foodhero.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Fragment.Ngo.NgoHome;
import com.example.foodhero.Models.Food;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentFoodDetailsBinding;


public class FoodDetails extends Fragment {


    FragmentFoodDetailsBinding binding;
    FragmentTransaction transaction;
    NavController navController;

    private final String parentdir= ApiClient.BASE_URL+"profile_pic/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFoodDetailsBinding.inflate(LayoutInflater.from(getContext()),container,false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        navController = Navigation.findNavController(getActivity(), R.id.ngocontainer);
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               navController.navigate(R.id.action_foodDetails_to_ngoHome);
            }
        });

        Food food=(Food)getArguments().getSerializable("data");
        Glide.with(getContext()).load(parentdir+food.getRes_id().getImgurl()).into(binding.profileImg);
        binding.name.setText(food.getRes_id().getName());
        binding.resName.setText(food.getRes_id().getName());
        binding.mobile.setText(food.getRes_id().getMobile());
        binding.emailid.setText(food.getRes_id().getEmail());
        binding.state.setText(food.getRes_id().getState());
        binding.district.setText(food.getRes_id().getDistrict());
        binding.address.setText(food.getRes_id().getAddress());
        binding.description.setText(food.getDescription());
        binding.date.setText(food.getDate().toString());
        binding.type.setText(food.getType());
        binding.noofdish.setText(Integer.toString(food.getNo_of_dishes()));
        binding.note.setText(food.getNote());
        binding.pickupaddress.setText(food.getPickup_address());
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
    @Override
    public void onStop() {
        super.onStop();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}