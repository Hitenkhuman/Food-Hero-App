package com.example.foodhero.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Fragment.Ngo.NgoHome;
import com.example.foodhero.Models.Request;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentRequestDetailsBinding;


public class RequestDetails extends Fragment {

    FragmentRequestDetailsBinding binding;
    private final String parentdir= ApiClient.BASE_URL+"profile_pic/";
    FragmentTransaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRequestDetailsBinding.inflate(LayoutInflater.from(getContext()),container,false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new NgoHome());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        Request food=(Request) getArguments().getSerializable("data");
        Glide.with(getContext()).load(parentdir+food.getNgo_id().getImgurl()).into(binding.profileImg);
        binding.name.setText(food.getNgo_id().getName());
        binding.mobile.setText(food.getNgo_id().getMobile());
        binding.emailid.setText(food.getNgo_id().getEmail());
        binding.state.setText(food.getNgo_id().getState());
        binding.district.setText(food.getNgo_id().getDistrict());
        binding.address.setText(food.getNgo_id().getAddress());
        binding.description.setText(food.getFood_id().getDescription());
        binding.date.setText(food.getDate().toString());
        binding.type.setText(food.getFood_id().getType());
        binding.noofdish.setText(Integer.toString(food.getFood_id().getNo_of_dishes()));
        binding.note.setText(food.getFood_id().getNote());
        binding.pickupaddress.setText(food.getFood_id().getPickup_address());
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