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

import com.example.foodhero.Fragment.admin.AdminNgo;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentNgoDetailsBinding;

public class NgoDetails extends Fragment {

    FragmentNgoDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentNgoDetailsBinding.inflate(LayoutInflater.from(getContext()),container,false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        Ngo res= (Ngo) getArguments().getSerializable("data");
        binding.name.setText(res.getName());
        binding.ngoName.setText(res.getName());
        binding.address.setText(res.getAddress());
        binding.mobile.setText(res.getMobile());
        binding.emailid.setText(res.getEmail());
        binding.openingtime.setText(res.getOpening_time());
        binding.closingtime.setText(res.getClosing_time());
        binding.state.setText(res.getState());
        binding.district.setText(res.getDistrict());
        binding.address.setText(res.getAddress());
        binding.status.setText(res.getVerification_status());
        binding.password.setText(res.getPassword());
        binding.id.setText(res.get_id());
        binding.authid.setText(res.getAuthid());
        binding.devicetoken.setText(res.getDevicetoken());
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.admincontainer,new AdminNgo());
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