package com.example.foodhero.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Fragment.Restaurant.HistoryFragment;
import com.example.foodhero.Models.Food;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentHistoryDetailsBinding;


public class HistoryDetailsFragment extends Fragment {

   FragmentHistoryDetailsBinding binding;
   SharedPreferences preferences;
    NavController navController;
    private final String parentdir= ApiClient.BASE_URL+"profile_pic/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHistoryDetailsBinding.inflate(LayoutInflater.from(getContext()),container,false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        preferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        Food food=(Food)getArguments().getSerializable("data");
        if(preferences.getString("user","").equals("NGO")){
            navController = Navigation.findNavController(getActivity(), R.id.ngocontainer);

        }
        else if(preferences.getString("user","").equals("ADMIN")){
            navController = Navigation.findNavController(getActivity(), R.id.admincontainer);

        }
        else{
            navController = Navigation.findNavController(getActivity(), R.id.container);
        }

        binding.date.setText(food.getDate().toString());
        binding.datedel.setText(food.getPickup_time());
        binding.deliverysts.setText(food.getFood_status());
        binding.foodid.setText(food.get_id());

        Glide.with(getContext()).load(parentdir+food.getNgo_id().getImgurl()).into(binding.ngoimage);
        Glide.with(getContext()).load(parentdir+food.getRes_id().getImgurl()).into(binding.imgrestuarant);

        binding.type.setText(food.getType());
        binding.description.setText(food.getDescription());
        binding.noofdishes.setText(Integer.toString(food.getNo_of_dishes()));
        binding.ngoname.setText(food.getNgo_id().getName());
        binding.restaurantname.setText(food.getRes_id().getName());
        binding.pickupadd.setText(food.getPickup_address());
        binding.note.setText(food.getNote());
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(preferences.getString("user","").equals("NGO")){
                    navController.navigate(R.id.action_historyDetailsFragment2_to_ngoHistory);
                }
                else if(preferences.getString("user","").equals("ADMIN")){
                    navController.navigate(R.id.action_historyDetailsFragment3_to_adminHistory);
                }
                else{
                    navController.navigate(R.id.action_historyDetailsFragment_to_historyFragment);
                }
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

    @Override
    public void onStop() {
        super.onStop();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}