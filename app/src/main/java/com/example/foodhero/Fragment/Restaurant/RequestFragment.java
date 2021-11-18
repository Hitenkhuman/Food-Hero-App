package com.example.foodhero.Fragment.Restaurant;

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
import com.example.foodhero.Adapters.RequestAdapter;
import com.example.foodhero.Adapters.RequestStatusAdapter;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.NgoDetails;
import com.example.foodhero.Fragment.RequestDetails;
import com.example.foodhero.Models.Food;
import com.example.foodhero.Models.Request;
import com.example.foodhero.Models.RequestNormal;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetFoodResponse;
import com.example.foodhero.Response.GetRequestResponse;
import com.example.foodhero.Response.GetRequestResponseNormal;
import com.example.foodhero.Response.GetRestaurantResponse;
import com.example.foodhero.databinding.FragmentHistoryBinding;
import com.example.foodhero.databinding.FragmentRequestBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RequestFragment extends Fragment implements RequestAdapter.OnRequestListner{
    FragmentRequestBinding binding;
    RequestAdapter adapter;
    ArrayList<Request> list;
    ApiInterface apiInterface;
    String foodid[];
    String RESID="6194dc3ac4587d44a06c1951";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRequestBinding.inflate(LayoutInflater.from(getContext()),container,false);
        list=new ArrayList<>();
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
//        list.add(new Request(R.drawable.n1,"Aakriti djdjfk","Chill Palace limited"));
//        list.add(new Request(R.drawable.n2,"Aakriti grgfgg","Chill Palace"));
//        list.add(new Request(R.drawable.n1,"Aakriti rtgagdfg","Chill Palace"));
//        list.add(new Request(R.drawable.n4,"Aakriti gfgfgfg","Chill Palace"));
//        list.add(new Request(R.drawable.h1,"Aakritib rtrtrtr","Chill Palace"));
//
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
        RequestDetails fragment=new RequestDetails();
        fragment.setArguments(bundle);
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onAcceptClick(int position) {
        apiInterface.updateRequest(list.get(position).getFood_id().get_id()).enqueue(new Callback<GetRequestResponseNormal>() {
            @Override
            public void onResponse(Call<GetRequestResponseNormal> call, Response<GetRequestResponseNormal> response) {
                if(response.body().isSuccess()){
                    list.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(getContext(), "Accepted Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetRequestResponseNormal> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("updatereq", "onFailure: "+t.fillInStackTrace());
            }
        });


    }
    private void getData(){
        apiInterface.getAvailableFoodForRestaurant(RESID).enqueue(new Callback<GetFoodResponse>() {
            @Override
            public void onResponse(Call<GetFoodResponse> call, Response<GetFoodResponse> response) {
                for (Food f:response.body().getData()
                     ) {
                        apiInterface.getRequestForRestaurant(f.get_id()).enqueue(new Callback<GetRequestResponse>() {
                            @Override
                            public void onResponse(Call<GetRequestResponse> call, Response<GetRequestResponse> response) {
                                if(response.body().isSuccess()){
                                    Log.d("food11", "onResponse: "+response.body().getData());
                                    list.addAll(response.body().getData());
                                    Log.d("food11", "onResponse: "+list.size());
                                    Log.d("food11", "onResponseexit: "+list.size());
                                    setAdapter(list);
                                }
                            }
                            @Override
                            public void onFailure(Call<GetRequestResponse> call, Throwable t) {
                                Log.d("food11", "onFailure: "+t.fillInStackTrace());
                            }
                        });
                    }
            }

            @Override
            public void onFailure(Call<GetFoodResponse> call, Throwable t) {
                Log.d("food11", "onFailureouter: "+t.fillInStackTrace());
            }

        });

    }
    private void setAdapter(ArrayList<Request> list){
        adapter=new RequestAdapter(list,getContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerequest.setLayoutManager(linearLayoutManager);
        binding.recyclerequest.setAdapter(adapter);

    }
}