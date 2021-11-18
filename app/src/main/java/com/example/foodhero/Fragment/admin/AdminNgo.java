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
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.HistoryDetailsFragment;
import com.example.foodhero.Fragment.NgoDetails;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetNgoResponse;
import com.example.foodhero.databinding.FragmentAdminNgoBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminNgo extends Fragment implements NgoAdapter.OnNgoListner{

    FragmentAdminNgoBinding binding;
    ArrayList<Ngo> list;
    NgoAdapter adapter;
    ApiInterface apiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminNgoBinding.inflate(LayoutInflater.from(getContext()),container,false);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        list=new ArrayList<>();
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));
//        list.add(new Ngo("1","seva","123456789","seva@gmail.com","ddfehfef",R.drawable.n1,"12:00","5:30","gujarat","baroda","kalabhavan","gfauifg","hifoffofh","Verified"));

        getData();
//         adapter=new NgoAdapter(list,getContext(),this);
//        binding.recycleradminngo.setAdapter(adapter);


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
        apiInterface.updateRejectNgo(list.get(position).get_id()).enqueue(new Callback<GetNgoResponse>() {
            @Override
            public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
                if(response.body().getSuccess()){
                Toast.makeText(getContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                list.remove(position);
                adapter.notifyItemRemoved(position);

                }
                else {
                    Toast.makeText(getContext(), "try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetNgoResponse> call, Throwable t) {
                Toast.makeText(getContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getData(){
        apiInterface.getNgo().enqueue(new Callback<GetNgoResponse>() {
            @Override
            public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
                try {
                    if(response!=null){
                        if(response.body().getSuccess()){
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

                }
            }

            @Override
            public void onFailure(Call<GetNgoResponse> call, Throwable t) {
                Log.e("err",t.getLocalizedMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setAdapter(ArrayList<Ngo> list){
        adapter=new NgoAdapter(list,getContext(),AdminNgo.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recycleradminngo.setLayoutManager(linearLayoutManager);
        binding.recycleradminngo.setAdapter(adapter);
    }
}