package com.example.foodhero.Fragment.admin;

import android.content.Intent;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminVerificationRequestsBinding.inflate(LayoutInflater.from(getContext()),container,false);
        list=new ArrayList<>();
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));

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
    public void onVerificationListClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
        AdminVerify fragment=new AdminVerify();
        fragment.setArguments(bundle);
        transaction.replace(R.id.admincontainer,fragment);
        transaction.commit();
    }
    private void getData(){
    apiInterface.getPendingList().enqueue(new Callback<GetNgoResponse>() {
        @Override
        public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
            try {
                if(response!=null){
                    if(response.body().getSuccess()){
                        list=response.body().getData();
                        setAdapter(list);
                    }
                    else {
                        Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception e){
                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<GetNgoResponse> call, Throwable t) {
            Log.e("err",t.getLocalizedMessage());
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });

    binding.changepassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(getContext(), AdminChangePasswordActivity.class));
        }
    });
    }
    private void setAdapter(ArrayList<Ngo> list){
        adapter=new VerificationListAdapter(list,getContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recycleadminverification.setLayoutManager(linearLayoutManager);
        binding.recycleadminverification.setAdapter(adapter);

    }
}