package com.example.foodhero.Fragment.Restaurant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.foodhero.R;
import com.example.foodhero.Response.GetFoodResponse;
import com.example.foodhero.databinding.FragmentHistoryBinding;
import com.example.foodhero.databinding.ItemHistoryLayoutBinding;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HistoryFragment extends Fragment implements HistoryAdapter.OnHistoryListner {

   // private ItemHistoryLayoutBinding binding;
    private FragmentHistoryBinding binding;
    private ArrayList<Food> list;
    ApiInterface apiInterface;
    HistoryAdapter adapter;
    NavController navController;
    SharedPreferences preferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // View view= inflater.inflate(R.layout.fragment_history, container, false);
        binding=FragmentHistoryBinding.inflate(LayoutInflater.from(getContext()),container,false);
        preferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        list=new ArrayList<>();
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        navController = Navigation.findNavController(getActivity(), R.id.container);
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
        navController.navigate(R.id.action_historyFragment_to_historyDetailsFragment,bundle);


    }
    private void getData(){
        binding.recyclerhistory.setVisibility(View.GONE);
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.shimmer.startShimmer();
        apiInterface.getHistoryRestaurant(preferences.getString("res_id","")).enqueue(new Callback<GetFoodResponse>() {
            @Override
            public void onResponse(Call<GetFoodResponse> call, Response<GetFoodResponse> response) {
                try {
                    if(response!=null){
                        if(response.body().isSuccess()){
                            if(list.size()>0){
                                list=response.body().getData();
                                adapter.notifyDataSetChanged();
                                binding.shimmer.setVisibility(View.GONE);
                            }
                            else {
                                list=response.body().getData();
                                setAdapter(list);
                                binding.shimmer.setVisibility(View.GONE);
                            }

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
            public void onFailure(Call<GetFoodResponse> call, Throwable t) {
                Log.e("err",t.getLocalizedMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.shimmer.stopShimmer();
        binding.recyclerhistory.setVisibility(View.VISIBLE);
        binding.shimmer.hideShimmer();
    }
    private void setAdapter(ArrayList<Food> list){
        adapter=new HistoryAdapter(list,getContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerhistory.setLayoutManager(linearLayoutManager);
        binding.recyclerhistory.setAdapter(adapter);

    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}