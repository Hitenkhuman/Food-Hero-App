package com.example.foodhero;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.Restaurant.HomeFragment;
import com.example.foodhero.Models.FoodNormal;
import com.example.foodhero.Response.GetFoodResponseNormal;
import com.example.foodhero.databinding.FragmentBottomShitBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class BottomShitFragment extends BottomSheetDialogFragment {


    FragmentBottomShitBinding binding;
    ApiInterface apiInterface;
    Context context;
    String description,note,pickupadd,type;
    String RESID="6194dc3ac4587d44a06c1951";
    String city="vadodara";
    boolean isVeg;
    int noofdish;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentBottomShitBinding.inflate(LayoutInflater.from(getContext()),container,false);
        context=getContext();
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        Bundle bundle=this.getArguments();
        if(bundle!=null){
            description=bundle.getString("description");
            noofdish=bundle.getInt("noofdish");
            note=bundle.getString("note");
            pickupadd=bundle.getString("pickupadd");
            isVeg=bundle.getBoolean("isVeg");
            binding.description.setText(description);
           binding.dishcount.setText(noofdish+"");
            binding.note.setText(note+"");
            binding.pickupaddress.setText(pickupadd);
          if(bundle.getBoolean("isVeg")) {
              binding.veg.setChecked(true);
              type="Veg";
          }
          else {
              binding.nonveg.setChecked(true);
              type="Nonveg";
          }

        }
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.addFood(new FoodNormal(RESID,description,type,noofdish,note,pickupadd,city)).enqueue(new Callback<GetFoodResponseNormal>() {
                    @Override
                    public void onResponse(Call<GetFoodResponseNormal> call, Response<GetFoodResponseNormal> response) {
                        try {
                        if(response.body().isSuccess()){
                            Toast.makeText(context, "Food Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, response.body().getMassage(), Toast.LENGTH_SHORT).show();

                        }

                        }
                        catch (Exception e){
                            Toast.makeText(getContext(), "SERVER ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetFoodResponseNormal> call, Throwable t) {
                        Log.d("FoodPost", "onFailure: "+t.fillInStackTrace());
                    }
                });
                BottomShitFragment.this.dismiss();
            }
        });
        return binding.getRoot();
    }
}