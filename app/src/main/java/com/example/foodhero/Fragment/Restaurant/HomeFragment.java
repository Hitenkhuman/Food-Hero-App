package com.example.foodhero.Fragment.Restaurant;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.foodhero.BottomShitFragment;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentHomeBinding;

import java.util.Locale;


@RequiresApi(api = Build.VERSION_CODES.N)
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(LayoutInflater.from(getContext()),container,false);




        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomShitFragment fragment=new BottomShitFragment();
                fragment.show(getActivity().getSupportFragmentManager(), fragment.getTag());
            }
        });
        return binding.getRoot();
    }


}