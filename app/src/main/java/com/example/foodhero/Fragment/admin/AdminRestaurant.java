package com.example.foodhero.Fragment.admin;

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
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminRestaurantBinding.inflate(LayoutInflater.from(getContext()),container,false);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        list=new ArrayList<>();
        navController = Navigation.findNavController(getActivity(), R.id.admincontainer);

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
    public void onRestaurantClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        navController.navigate(R.id.action_adminRestaurant_to_restaurantDetails,bundle);
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
        binding.recycleradminres.setVisibility(View.GONE);
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.shimmer.startShimmer();
        binding.nodata.setVisibility(View.GONE);
        apiInterface.getRestaurant().enqueue(new Callback<GetRestaurantResponse>() {
            @Override
            public void onResponse(Call<GetRestaurantResponse> call, Response<GetRestaurantResponse> response) {
                try {
                    if(response!=null){
                        if(response.body().isSuccess() && response.body().getData().size()>0){
                            if(list.size()>0){
                                list=response.body().getData();
                                adapter.notifyDataSetChanged();
                                binding.nodata.setVisibility(View.GONE);
                                binding.shimmer.setVisibility(View.GONE);
                            }
                            else {
                                list=response.body().getData();
                                binding.nodata.setVisibility(View.GONE);
                                binding.shimmer.setVisibility(View.GONE);
                                setAdapter(list);
                            }
                        }
                        else {
                            Toast.makeText(getContext(), "Try again later", Toast.LENGTH_SHORT).show();
                            binding.recycleradminres.setVisibility(View.GONE);
                            binding.nodata.setVisibility(View.VISIBLE);
                            binding.shimmer.setVisibility(View.GONE);
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    binding.recycleradminres.setVisibility(View.GONE);
                    binding.nodata.setVisibility(View.VISIBLE);
                    binding.shimmer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetRestaurantResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                binding.recycleradminres.setVisibility(View.GONE);
                binding.nodata.setVisibility(View.VISIBLE);
                binding.shimmer.setVisibility(View.GONE);
            }
        });
        binding.shimmer.stopShimmer();
        binding.recycleradminres.setVisibility(View.VISIBLE);
        binding.shimmer.hideShimmer();
    }
    private void setAdapter(ArrayList<Restuarant> list){
        adapter =new RestaurantAdapter(list,getContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recycleradminres.setLayoutManager(linearLayoutManager);
        binding.recycleradminres.setAdapter(adapter);


    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

}