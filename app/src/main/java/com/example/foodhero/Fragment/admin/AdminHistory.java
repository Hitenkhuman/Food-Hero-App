package com.example.foodhero.Fragment.admin;

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

import com.example.foodhero.Adapters.HistoryAdapter;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.HistoryDetailsFragment;
import com.example.foodhero.Models.Food;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetFoodResponse;
import com.example.foodhero.databinding.FragmentAdminHistoryBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AdminHistory extends Fragment implements HistoryAdapter.OnHistoryListner{

   FragmentAdminHistoryBinding binding;
   ArrayList<Food> list;
    ApiInterface apiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminHistoryBinding.inflate(LayoutInflater.from(getContext()),container,false);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        list=new ArrayList<>();
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
    public void onHistoryClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        HistoryDetailsFragment fragment=new HistoryDetailsFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.admincontainer,fragment);
        transaction.commit();
    }
    private void getData(){
        apiInterface.getHistory().enqueue(new Callback<GetFoodResponse>() {
            @Override
            public void onResponse(Call<GetFoodResponse> call, Response<GetFoodResponse> response) {
                try {
                    if(response!=null){
                        if(response.body().isSuccess()){
                            list=response.body().getData();
                            setAdapter(response.body().getData());
                            Log.d("debug",""+response.body());
                            Toast.makeText(getContext(), "get"+response.body().getData().size(), Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetFoodResponse> call, Throwable t) {
                Log.e("err",t.getLocalizedMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setAdapter(ArrayList<Food> list){
        HistoryAdapter adapter=new HistoryAdapter(list,requireContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext());
        binding.recycleradminhistory.setLayoutManager(linearLayoutManager);
        binding.recycleradminhistory.setAdapter(adapter);

    }
}