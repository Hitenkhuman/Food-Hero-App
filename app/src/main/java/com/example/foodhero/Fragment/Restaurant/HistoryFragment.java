package com.example.foodhero.Fragment.Restaurant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodhero.Adapters.HistoryAdapter;
import com.example.foodhero.Fragment.HistoryDetailsFragment;
import com.example.foodhero.Models.Food;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentHistoryBinding;
import com.example.foodhero.databinding.ItemHistoryLayoutBinding;

import java.io.Serializable;
import java.util.ArrayList;


public class HistoryFragment extends Fragment implements HistoryAdapter.OnHistoryListner {

   // private ItemHistoryLayoutBinding binding;
    private FragmentHistoryBinding binding;
    private ArrayList<Food> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // View view= inflater.inflate(R.layout.fragment_history, container, false);
        binding=FragmentHistoryBinding.inflate(LayoutInflater.from(getContext()),container,false);

        list=new ArrayList<>();
        list.add(new Food("1","11","21","Hotel Surya","Arpan",R.drawable.h1,R.drawable.n1,"21-12-2020","Dal rice","VEG",10,"Delivered"));
        list.add(new Food("2","12","22","Chili garlic","Sahyog",R.drawable.h2,R.drawable.n2,"11-11-2020","Paneer","VEG",12,"Delivered"));
        list.add(new Food("3","13","23","Hotel Seven","Soumya",R.drawable.h3,R.drawable.n3,"10-11-2020","Dal rice","VEG",8,"Delivered"));
        list.add(new Food("4","14","24","Chill Palace","Tyag",R.drawable.h4,R.drawable.n4,"15-10-2020","Pulav","VEG",15,"Delivered"));
        list.add(new Food("5","15","25","Green Hotel","Janta",R.drawable.h5,R.drawable.n5,"06-05-2020","Mix subji","VEG",40,"Delivered"));
        list.add(new Food("3","13","23","Hotel Seven","Soumya",R.drawable.h3,R.drawable.n3,"10-11-2020","Dal rice","VEG",8,"Delivered"));

        HistoryAdapter adapter=new HistoryAdapter(list,getContext(),this);
        binding.recyclerhistory.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerhistory.setLayoutManager(linearLayoutManager);


        return binding.getRoot();
    }

    @Override
    public void onHistoryClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        HistoryDetailsFragment fragment=new HistoryDetailsFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.container,fragment);
        transaction.commit();

    }
}