package com.example.foodhero.Fragment.Ngo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodhero.Adapters.RequestStatusAdapter;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.FoodDetails;
import com.example.foodhero.Fragment.FoodPickupDetails;
import com.example.foodhero.Models.Food;
import com.example.foodhero.Models.Request;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetFoodResponse;
import com.example.foodhero.Response.GetRequestResponse;
import com.example.foodhero.Response.GetRestaurantResponse;
import com.example.foodhero.databinding.FragmentNgoRequestBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class NgoRequest extends Fragment implements RequestStatusAdapter.OnRequestListner{
    ArrayList<Request> list;
   FragmentNgoRequestBinding binding;
    RequestStatusAdapter adapter;
    ApiInterface apiInterface;
    String NGOID="6194e089efb9d82888bd94ef";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNgoRequestBinding.inflate(LayoutInflater.from(getContext()),container,false);
        list=new ArrayList<>();
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
//        list.add(new Food("1","11","21","Hotel Surya","Arpan",R.drawable.h1,R.drawable.n1,"21-12-2020","Dal rice","VEG",10,"Delivered"));
//        list.add(new Food("2","12","22","Chili garlic","Sahyog",R.drawable.h2,R.drawable.n2,"11-11-2020","Paneer","VEG",12,"Delivered"));
//        list.add(new Food("3","13","23","Hotel Seven","Soumya",R.drawable.h3,R.drawable.n3,"10-11-2020","Dal rice","VEG",8,"Delivered"));
//        list.add(new Food("4","14","24","Chill Palace","Tyag",R.drawable.h4,R.drawable.n4,"15-10-2020","Pulav","VEG",15,"Delivered"));
//        list.add(new Food("5","15","25","Green Hotel","Janta",R.drawable.h5,R.drawable.n5,"06-05-2020","Mix subji","VEG",40,"Delivered"));
//        list.add(new Food("3","13","23","Hotel Seven","Soumya",R.drawable.h3,R.drawable.n3,"10-11-2020","Dal rice","VEG",8,"Delivered"));

        getData();

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
        FoodPickupDetails fragment=new FoodPickupDetails();
        fragment.setArguments(bundle);
        transaction.replace(R.id.ngocontainer,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPickupClick(int position) {
        apiInterface.updateFoodPickup(list.get(position).getFood_id().get_id()).enqueue(new Callback<GetFoodResponse>() {
            @Override
            public void onResponse(Call<GetFoodResponse> call, Response<GetFoodResponse> response) {
                if(response.body().isSuccess()){
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(getContext(), "Food Pickedup", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFoodResponse> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.fillInStackTrace(), Toast.LENGTH_SHORT).show();
                Log.d("pickup", "onFailure: "+t.fillInStackTrace());
            }
        });

    }

    private void getData(){
        apiInterface.getRequest(NGOID).enqueue(new Callback<GetRequestResponse>() {
            @Override
            public void onResponse(Call<GetRequestResponse> call, Response<GetRequestResponse> response) {
                if(response.body().isSuccess()){
                    list=response.body().getData();
                    setAdapter(list);
                    Log.d("tett", "onResponse: "+list.size());
                }
                else {
                    Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    Log.d("tett", "onResponse: "+response.body().getMassage());
                }
            }

            @Override
            public void onFailure(Call<GetRequestResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("tett", "onFailure: "+t.fillInStackTrace());

            }
        });
    }
    private void setAdapter(ArrayList<Request> list){
        adapter=new RequestStatusAdapter(list,getContext(),this);
        binding.recyclerngorequest.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerngorequest.setLayoutManager(linearLayoutManager);
    }
}