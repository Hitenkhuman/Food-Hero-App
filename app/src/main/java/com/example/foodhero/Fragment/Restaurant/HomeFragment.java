package com.example.foodhero.Fragment.Restaurant;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.foodhero.BottomShitFragment;
import com.example.foodhero.Fragment.HistoryDetailsFragment;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentHomeBinding;

import java.util.Locale;


@RequiresApi(api = Build.VERSION_CODES.N)
public class HomeFragment extends Fragment {
    FragmentTransaction transaction;
    private FragmentHomeBinding binding;
    SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        binding=FragmentHomeBinding.inflate(LayoutInflater.from(getContext()),container,false);
        binding.noofdish.setText("5");
        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description=binding.desc.getText().toString();
                String pickupadd=binding.pickupadd.getText().toString();
                int noofdish=Integer.parseInt(binding.noofdish.getText().toString());
                String note=binding.note.getText().toString();
                Bundle bundle=new Bundle();
                bundle.putString("description",description);
                bundle.putString("note",note);
                bundle.putString("pickupadd",pickupadd);
                bundle.putBoolean("isVeg",binding.veg.isChecked());
                bundle.putInt("noofdish",noofdish);

                BottomShitFragment fragment=new BottomShitFragment();
                fragment.setArguments(bundle);
                fragment.show(getActivity().getSupportFragmentManager(), fragment.getTag());
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