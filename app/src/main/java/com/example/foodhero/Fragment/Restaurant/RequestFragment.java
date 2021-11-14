package com.example.foodhero.Fragment.Restaurant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodhero.Adapters.HistoryAdapter;
import com.example.foodhero.Adapters.RequestAdapter;
import com.example.foodhero.Fragment.NgoDetails;
import com.example.foodhero.Fragment.RequestDetails;
import com.example.foodhero.Models.Food;
import com.example.foodhero.Models.Request;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentHistoryBinding;
import com.example.foodhero.databinding.FragmentRequestBinding;

import java.util.ArrayList;


public class RequestFragment extends Fragment implements RequestAdapter.OnRequestListner{
    FragmentRequestBinding binding;
    RequestAdapter adapter;
    ArrayList<Request> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRequestBinding.inflate(LayoutInflater.from(getContext()),container,false);
        list=new ArrayList<>();
        list.add(new Request(R.drawable.n1,"Aakriti djdjfk","Chill Palace limited"));
        list.add(new Request(R.drawable.n2,"Aakriti grgfgg","Chill Palace"));
        list.add(new Request(R.drawable.n1,"Aakriti rtgagdfg","Chill Palace"));
        list.add(new Request(R.drawable.n4,"Aakriti gfgfgfg","Chill Palace"));
        list.add(new Request(R.drawable.h1,"Aakritib rtrtrtr","Chill Palace"));
         adapter=new RequestAdapter(list,getContext(),this);
        binding.recyclerequest.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerequest.setLayoutManager(linearLayoutManager);
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
    public void onRequestClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        RequestDetails fragment=new RequestDetails();
        fragment.setArguments(bundle);
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onAcceptClick(int position) {
    list.remove(position);
    adapter.notifyItemRemoved(position);
    }
}