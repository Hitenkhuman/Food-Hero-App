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
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAdminNgoBinding.inflate(LayoutInflater.from(getContext()),container,false);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        navController = Navigation.findNavController(getActivity(), R.id.admincontainer);
        list=new ArrayList<>();

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
    public void onNgoClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",list.get(position));
        navController.navigate(R.id.action_adminNgo_to_ngoDetails,bundle);
    }

    @Override
    public void onDelClick(int position) {
        apiInterface.updateRejectNgo(list.get(position).get_id()).enqueue(new Callback<GetNgoResponse>() {
            @Override
            public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
                try {
                if(response.body().getSuccess()){
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
            public void onFailure(Call<GetNgoResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getData(){
        binding.recycleradminngo.setVisibility(View.GONE);
        binding.shimmer.setVisibility(View.VISIBLE);
        binding.shimmer.startShimmer();
        apiInterface.getNgo().enqueue(new Callback<GetNgoResponse>() {
            @Override
            public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
                try {

                        if(response.body().getSuccess()){
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
        binding.shimmer.stopShimmer();
        binding.recycleradminngo.setVisibility(View.VISIBLE);
        binding.shimmer.hideShimmer();
    }
    private void setAdapter(ArrayList<Ngo> list){
        adapter=new NgoAdapter(list,getContext(),AdminNgo.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        binding.recycleradminngo.setLayoutManager(linearLayoutManager);
        binding.recycleradminngo.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}