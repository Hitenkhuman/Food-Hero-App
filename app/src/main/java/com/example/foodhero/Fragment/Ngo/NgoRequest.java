package com.example.foodhero.Fragment.Ngo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
   SharedPreferences preferences;
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNgoRequestBinding.inflate(LayoutInflater.from(getContext()),container,false);
        list=new ArrayList<>();
        preferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        navController = Navigation.findNavController(getActivity(), R.id.ngocontainer);
        getData();

        binding.swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                binding.swiper.setRefreshing(false);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onRequestClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        navController.navigate(R.id.action_ngoRequest_to_foodPickupDetails,bundle);

    }

    @Override
    public void onPickupClick(int position) {
        apiInterface.updateFoodPickup(list.get(position).getFood_id().get_id()).enqueue(new Callback<GetFoodResponse>() {
            @Override
            public void onResponse(Call<GetFoodResponse> call, Response<GetFoodResponse> response) {
                try {
                if(response.body().isSuccess()){
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(getContext(), "Food Pickedup", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<GetFoodResponse> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.fillInStackTrace(), Toast.LENGTH_SHORT).show();
                Log.d("pickup", "onFailure: "+t.fillInStackTrace());
            }
        });

    }

    private void getData(){
        binding.recyclerngorequest.setVisibility(View.GONE);
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.shimmer.startShimmer();
        binding.nodata.setVisibility(View.GONE);
        apiInterface.getRequest(preferences.getString("ngo_id","")).enqueue(new Callback<GetRequestResponse>() {
            @Override
            public void onResponse(Call<GetRequestResponse> call, Response<GetRequestResponse> response) {
                try {
                    if(response.body().isSuccess() && response.body().getData().size()>0){
                        if(list.size()>0){
                            list=response.body().getData();
                            adapter.notifyDataSetChanged();
                            binding.nodata.setVisibility(View.GONE);
                            binding.shimmer.setVisibility(View.GONE);
                        }
                        else {
                            list=response.body().getData();
                            setAdapter(list);
                            binding.nodata.setVisibility(View.GONE);
                            binding.shimmer.setVisibility(View.GONE);
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Try again later", Toast.LENGTH_SHORT).show();
                        binding.recyclerngorequest.setVisibility(View.GONE);
                        binding.nodata.setVisibility(View.VISIBLE);
                        binding.shimmer.setVisibility(View.GONE);
                    }
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    binding.recyclerngorequest.setVisibility(View.GONE);
                    binding.nodata.setVisibility(View.VISIBLE);
                    binding.shimmer.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<GetRequestResponse> call, Throwable t) {
                Toast.makeText(getContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();
                binding.recyclerngorequest.setVisibility(View.GONE);
                binding.nodata.setVisibility(View.VISIBLE);
                binding.shimmer.setVisibility(View.GONE);
            }
        });
        binding.shimmer.stopShimmer();
        binding.recyclerngorequest.setVisibility(View.VISIBLE);
        binding.shimmer.hideShimmer();
    }
    private void setAdapter(ArrayList<Request> list){
        adapter=new RequestStatusAdapter(list,getContext(),this);
        binding.recyclerngorequest.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerngorequest.setLayoutManager(linearLayoutManager);
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}