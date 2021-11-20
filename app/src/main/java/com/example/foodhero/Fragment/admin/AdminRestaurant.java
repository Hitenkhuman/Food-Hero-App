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

import com.example.foodhero.Adapters.NgoAdapter;
import com.example.foodhero.Adapters.RestaurantAdapter;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.HistoryDetailsFragment;
import com.example.foodhero.Fragment.RestaurantDetails;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.Models.Restuarant;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetRestaurantResponse;
import com.example.foodhero.databinding.FragmentAdminRestaurantBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AdminRestaurant extends Fragment implements RestaurantAdapter.OnRestaurantListner{

    FragmentAdminRestaurantBinding binding;
    ArrayList<Restuarant> list;
    RestaurantAdapter adapter;
    ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminRestaurantBinding.inflate(LayoutInflater.from(getContext()),container,false);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        list=new ArrayList<>();
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));
//        list.add(new Restuarant("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.h1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","12-11-2020"));

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
        apiInterface.updateRejectRestaurants(list.get(position).get_id()).enqueue(new Callback<GetRestaurantResponse>() {
            @Override
            public void onResponse(Call<GetRestaurantResponse> call, Response<GetRestaurantResponse> response) {
                try {
                if(response.body().isSuccess()){
                    Toast.makeText(getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    adapter.notifyItemRemoved(position);

                }
                else {
                    Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                }

                }
                catch (Exception e){
                    Toast.makeText(getContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetRestaurantResponse> call, Throwable t) {
                Toast.makeText(getContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(){
        apiInterface.getRestaurant().enqueue(new Callback<GetRestaurantResponse>() {
            @Override
            public void onResponse(Call<GetRestaurantResponse> call, Response<GetRestaurantResponse> response) {
                try {
                    if(response!=null){
                        if(response.body().isSuccess()){
                            list=response.body().getData();
                            setAdapter(list);
                            Toast.makeText(getContext(), "get", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getContext(), "error 0", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "error 1", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<GetRestaurantResponse> call, Throwable t) {
                Log.e("err",t.getLocalizedMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setAdapter(ArrayList<Restuarant> list){
        adapter =new RestaurantAdapter(list,getContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recycleradminres.setLayoutManager(linearLayoutManager);
        binding.recycleradminres.setAdapter(adapter);


    }

}