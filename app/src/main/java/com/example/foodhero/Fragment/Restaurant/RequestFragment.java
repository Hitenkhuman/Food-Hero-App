package com.example.foodhero.Fragment.Restaurant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodhero.Adapters.HistoryAdapter;
import com.example.foodhero.Adapters.RequestAdapter;
import com.example.foodhero.Models.Food;
import com.example.foodhero.Models.Request;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentHistoryBinding;
import com.example.foodhero.databinding.FragmentRequestBinding;

import java.util.ArrayList;


public class RequestFragment extends Fragment {
    FragmentRequestBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRequestBinding.inflate(LayoutInflater.from(getContext()),container,false);
        ArrayList<Request> list=new ArrayList<>();
        list.add(new Request(R.drawable.n1,"Aakriti","Chill Palace"));
        list.add(new Request(R.drawable.n2,"Aakriti","Chill Palace"));
        list.add(new Request(R.drawable.n1,"Aakriti","Chill Palace"));
        list.add(new Request(R.drawable.n4,"Aakriti","Chill Palace"));
        list.add(new Request(R.drawable.h1,"Aakriti","Chill Palace"));
        RequestAdapter adapter=new RequestAdapter(list,getContext());
        binding.recyclerequest.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerequest.setLayoutManager(linearLayoutManager);

        return binding.getRoot();
    }
}