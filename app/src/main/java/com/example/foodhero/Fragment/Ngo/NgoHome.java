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

import com.example.foodhero.Adapters.FoodListAdapter;
import com.example.foodhero.Adapters.RequestStatusAdapter;
import com.example.foodhero.Adapters.HistoryAdapter;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.FoodDetails;
import com.example.foodhero.Fragment.NgoDetails;
import com.example.foodhero.Models.Food;
import com.example.foodhero.Models.Request;
import com.example.foodhero.Models.RequestNormal;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetFoodResponse;
import com.example.foodhero.Response.GetRequestResponse;
import com.example.foodhero.Response.GetRequestResponseNormal;
import com.example.foodhero.databinding.FragmentNgoHomeBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class NgoHome extends Fragment implements FoodListAdapter.OnFoodListListner{

    FragmentNgoHomeBinding binding;
    ArrayList<Food> list;
    FoodListAdapter adapter;
    ApiInterface apiInterface;
    FragmentTransaction transaction;
    String city="Vadodara";
    String NGOID="6194e089efb9d82888bd94ef";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNgoHomeBinding.inflate(LayoutInflater.from(getContext()),container,false);
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
    public void OnFoodClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        transaction=getActivity().getSupportFragmentManager().beginTransaction();
        FoodDetails fragment=new FoodDetails();
        fragment.setArguments(bundle);
        transaction.replace(R.id.ngocontainer,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void OnRequestClick(int position) {
    apiInterface.addRequest(new RequestNormal(NGOID,list.get(position).getRes_id().get_id(),list.get(position).get_id())).enqueue(new Callback<GetRequestResponseNormal>() {
        @Override
        public void onResponse(Call<GetRequestResponseNormal> call, Response<GetRequestResponseNormal> response) {
            if(response.body().isSuccess()){
                Toast.makeText(getContext(), "Food Request Sent to Restaurant", Toast.LENGTH_SHORT).show();
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }
            else {
                Log.d("abab",response.body().getMassage());
                Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<GetRequestResponseNormal> call, Throwable t) {
            Log.d("abab",t.toString());
            Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    });
    }

    private void getData(){
        apiInterface.getAvailableFood(city).enqueue(new Callback<GetFoodResponse>() {
            @Override
            public void onResponse(Call<GetFoodResponse> call, Response<GetFoodResponse> response) {
                if(response.body().isSuccess()){
                    list=response.body().getData();
                    setAdapter(list);
                }
                else {
                    Toast.makeText(getContext(), "try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFoodResponse> call, Throwable t) {
                Toast.makeText(getContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter(ArrayList<Food> list){
        adapter=new FoodListAdapter(list,getContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerngohome.setLayoutManager(linearLayoutManager);
        binding.recyclerngohome.setAdapter(adapter);

    }
}