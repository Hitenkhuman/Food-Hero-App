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
import com.example.foodhero.Fragment.HistoryDetailsFragment;
import com.example.foodhero.Fragment.NgoDetails;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.R;
import com.example.foodhero.databinding.FragmentAdminNgoBinding;

import java.util.ArrayList;

public class AdminNgo extends Fragment implements NgoAdapter.OnNgoListner{

    FragmentAdminNgoBinding binding;
    ArrayList<Ngo> list;
    NgoAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminNgoBinding.inflate(LayoutInflater.from(getContext()),container,false);
        list=new ArrayList<>();
        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));

         adapter=new NgoAdapter(list,getContext(),this);
        binding.recycleradminngo.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recycleradminngo.setLayoutManager(linearLayoutManager);
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
    public void onNgoClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        NgoDetails fragment=new NgoDetails();
        fragment.setArguments(bundle);
        transaction.replace(R.id.admincontainer,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDelClick(int position) {
        list.remove(position);
        adapter.notifyItemRemoved(position);
    }
}