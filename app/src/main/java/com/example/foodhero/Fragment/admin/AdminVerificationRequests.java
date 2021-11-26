package com.example.foodhero.Fragment.admin;

import android.content.Intent;
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
import com.example.foodhero.Adapters.VerificationListAdapter;
import com.example.foodhero.AdminChangePasswordActivity;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.AdminVerify;
import com.example.foodhero.Fragment.NgoDetails;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetNgoResponse;
import com.example.foodhero.databinding.FragmentAdminVerificationRequestsBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AdminVerificationRequests extends Fragment implements VerificationListAdapter.OnVerificationListListner{
    FragmentAdminVerificationRequestsBinding binding;
    ArrayList<Ngo> list;
    ApiInterface apiInterface;
    VerificationListAdapter adapter;
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminVerificationRequestsBinding.inflate(LayoutInflater.from(getContext()),container,false);
        list=new ArrayList<>();
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        navController = Navigation.findNavController(container);
       getData();
        binding.changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AdminChangePasswordActivity.class));
            }
        });
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
    public void onVerificationListClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        navController.navigate(R.id.action_adminVerificationRequests_to_adminVerify,bundle);

    }
    private void getData(){
        binding.recycleadminverification.setVisibility(View.GONE);
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.shimmer.startShimmer();
        binding.nodata.setVisibility(View.GONE);
    apiInterface.getPendingList().enqueue(new Callback<GetNgoResponse>() {
        @Override
        public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
            try {
                if(response!=null){
                    if(response.body().getSuccess() && response.body().getData().size()>0){
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
                        binding.recycleadminverification.setVisibility(View.GONE);
                        binding.nodata.setVisibility(View.VISIBLE);
                        binding.shimmer.setVisibility(View.GONE);
                    }
                }
            }
            catch (Exception e){
                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                binding.recycleadminverification.setVisibility(View.GONE);
                binding.nodata.setVisibility(View.VISIBLE);
                binding.shimmer.setVisibility(View.GONE);
            }
        }

        @Override
        public void onFailure(Call<GetNgoResponse> call, Throwable t) {
            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
            binding.recycleadminverification.setVisibility(View.GONE);
            binding.nodata.setVisibility(View.VISIBLE);
            binding.shimmer.setVisibility(View.GONE);
        }
    });


        binding.shimmer.stopShimmer();
        binding.recycleadminverification.setVisibility(View.VISIBLE);
        binding.shimmer.hideShimmer();
    }
    private void setAdapter(ArrayList<Ngo> list){
        adapter=new VerificationListAdapter(list,getContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recycleadminverification.setLayoutManager(linearLayoutManager);
        binding.recycleadminverification.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}