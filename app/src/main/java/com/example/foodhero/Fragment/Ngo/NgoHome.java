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
    SharedPreferences preferences;
    NavController navController;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNgoHomeBinding.inflate(LayoutInflater.from(getContext()),container,false);
        list=new ArrayList<>();
        preferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        navController = Navigation.findNavController(container);
        context=getContext();
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
    public void OnFoodClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        navController.navigate(R.id.action_ngoHome_to_foodDetails,bundle);

    }

    @Override
    public void OnRequestClick(int position) {
    apiInterface.addRequest(new RequestNormal(preferences.getString("ngo_id",""),list.get(position).getRes_id().get_id(),list.get(position).get_id())).enqueue(new Callback<GetRequestResponseNormal>() {
        @Override
        public void onResponse(Call<GetRequestResponseNormal> call, Response<GetRequestResponseNormal> response) {
            if(response.body().isSuccess()){
                Toast.makeText(context, "Food Request Sent to Restaurant", Toast.LENGTH_SHORT).show();
                list.remove(position);
                adapter.notifyItemRemoved(position);
            }
            else {
                Log.d("abab",response.body().getMassage());
                Toast.makeText(context, response.body().getMassage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<GetRequestResponseNormal> call, Throwable t) {
            Log.d("abab",t.toString());
            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    });
    }

    private void getData(){
        binding.recyclerngohome.setVisibility(View.GONE);
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.shimmer.startShimmer();
        apiInterface.getAvailableFood(preferences.getString("ngo_id",""),preferences.getString("city","vadodara")).enqueue(new Callback<GetFoodResponse>() {
            @Override
            public void onResponse(Call<GetFoodResponse> call, Response<GetFoodResponse> response) {
                try {
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
                    Toast.makeText(context, "try again later", Toast.LENGTH_SHORT).show();
                }

                }
                catch (Exception e){
                    Toast.makeText(context, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetFoodResponse> call, Throwable t) {
                Toast.makeText(context, "SERVER ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        binding.shimmer.stopShimmer();
        binding.recyclerngohome.setVisibility(View.VISIBLE);
        binding.shimmer.hideShimmer();
    }

    private void setAdapter(ArrayList<Food> list){
        adapter=new FoodListAdapter(list,getContext(),this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recyclerngohome.setLayoutManager(linearLayoutManager);
        binding.recyclerngohome.setAdapter(adapter);

    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}