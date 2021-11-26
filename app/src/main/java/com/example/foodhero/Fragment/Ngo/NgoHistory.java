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

import com.example.foodhero.Adapters.HistoryAdapter;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.HistoryDetailsFragment;
import com.example.foodhero.Models.Food;
import com.example.foodhero.Models.Request;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetFoodResponse;
import com.example.foodhero.databinding.FragmentNgoHistoryBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class NgoHistory extends Fragment implements HistoryAdapter.OnHistoryListner{

    FragmentNgoHistoryBinding binding;
    ArrayList<Food> list;
    HistoryAdapter adapter;
    ApiInterface apiInterface;
    SharedPreferences preferences;
    NavController navController;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNgoHistoryBinding.inflate(LayoutInflater.from(getContext()),container,false);
        context=getContext();
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
    public void onHistoryClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        navController.navigate(R.id.action_ngoHistory_to_historyDetailsFragment2,bundle);

    }
    private void getData(){
        binding.recyclerngohistory.setVisibility(View.GONE);
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.shimmer.startShimmer();
        binding.nodata.setVisibility(View.GONE);
        apiInterface.getHistoryNgo(preferences.getString("ngo_id","")).enqueue(new Callback<GetFoodResponse>() {
            @Override
            public void onResponse(Call<GetFoodResponse> call, Response<GetFoodResponse> response) {
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
                                setAdapter(list);
                                binding.nodata.setVisibility(View.GONE);
                                binding.shimmer.setVisibility(View.GONE);
                            }

                        }
                        else {
                            Toast.makeText(getContext(), "Try again later", Toast.LENGTH_SHORT).show();
                            binding.recyclerngohistory.setVisibility(View.GONE);
                            binding.nodata.setVisibility(View.VISIBLE);
                            binding.shimmer.setVisibility(View.GONE);
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(context, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                    binding.recyclerngohistory.setVisibility(View.GONE);
                    binding.nodata.setVisibility(View.VISIBLE);
                    binding.shimmer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<GetFoodResponse> call, Throwable t) {
                Toast.makeText(context, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                binding.recyclerngohistory.setVisibility(View.GONE);
                binding.nodata.setVisibility(View.VISIBLE);
                binding.shimmer.setVisibility(View.GONE);
            }
        });
        binding.shimmer.stopShimmer();
        binding.recyclerngohistory.setVisibility(View.VISIBLE);
        binding.shimmer.hideShimmer();
    }
    private void setAdapter(ArrayList<Food> list){
        adapter=new HistoryAdapter(list,getContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerngohistory.setLayoutManager(linearLayoutManager);
        binding.recyclerngohistory.setAdapter(adapter);

    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}