package com.example.foodhero.Fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodhero.Adapters.NgoAdapter;
import com.example.foodhero.Adapters.RestaurantAdapter;
import com.example.foodhero.Fragment.HistoryDetailsFragment;
import com.example.foodhero.Fragment.RestaurantDetails;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.Models.Restuarant;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentAdminRestaurantBinding;

import java.util.ArrayList;


public class AdminRestaurant extends Fragment implements RestaurantAdapter.OnRestaurantListner{

    FragmentAdminRestaurantBinding binding;
    ArrayList<Restuarant> list;
    RestaurantAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminRestaurantBinding.inflate(LayoutInflater.from(getContext()),container,false);
        list=new ArrayList<>();
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));

        adapter =new RestaurantAdapter(list,getContext(),this);
        binding.recycleradminres.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recycleradminres.setLayoutManager(linearLayoutManager);
        binding.swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "swiped", Toast.LENGTH_SHORT).show();
                binding.swiper.setRefreshing(false);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onRestaurantClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        RestaurantDetails fragment=new RestaurantDetails();
        fragment.setArguments(bundle);
        transaction.replace(R.id.admincontainer,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDeleteClick(int position) {
        list.remove(position);
        adapter.notifyItemRemoved(position);
    }
}