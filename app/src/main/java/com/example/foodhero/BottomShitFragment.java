package com.example.foodhero;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Apis.ApiNotificationClient;
import com.example.foodhero.Apis.ApiNotificationInterface;
import com.example.foodhero.Fragment.Restaurant.HomeFragment;
import com.example.foodhero.Models.Data;
import com.example.foodhero.Models.FoodNormal;
import com.example.foodhero.Models.NotificationSender;
import com.example.foodhero.Response.GetFoodResponseNormal;
import com.example.foodhero.Response.GetNotificationResponse;
import com.example.foodhero.databinding.FragmentBottomShitBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class BottomShitFragment extends BottomSheetDialogFragment {


    FragmentBottomShitBinding binding;
    ApiInterface apiInterface;
    SharedPreferences preferences;
    Context context;
    String description,note,pickupadd,type;
    boolean isVeg;
    int noofdish;
    ApiNotificationInterface apiNotificationInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentBottomShitBinding.inflate(LayoutInflater.from(getContext()),container,false);
        context=getContext();
        preferences=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        Retrofit ref=ApiNotificationClient.getClientNotification();
        apiNotificationInterface= ref.create(ApiNotificationInterface.class);
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
      binding.countminus.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(binding.dishcount.getText().toString().equals("5")){
                  Toast.makeText(getContext(), "Minimum limit is 5", Toast.LENGTH_SHORT).show();
              }
              else{
                  int newcount=Integer.parseInt(binding.dishcount.getText().toString())-1;
                  binding.dishcount.setText(newcount+"");
              }
          }
      });

        binding.countplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int newcount=Integer.parseInt(binding.dishcount.getText().toString())+1;
                    binding.dishcount.setText(newcount+"");
            }
        });


       // sendNotifications(token,"login","hey");
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.dishcount.getText().toString().trim().length()!=0 && binding.description.getText().toString().trim().length()!=0 && binding.note.getText().toString().trim().length()!=0 && binding.pickupaddress.getText().toString().trim().length()!=0){
                    String description,note,pickupadd,type;
                    description=binding.description.getText().toString().trim();
                    note=binding.note.getText().toString().trim();
                    pickupadd=binding.pickupaddress.getText().toString().trim();
                    int noofdish=Integer.parseInt(binding.dishcount.getText().toString());
                    if(binding.veg.isChecked()) {
                        type="Veg";
                    }
                    else {
                        type="Nonveg";
                    }
                apiInterface.addFood(new FoodNormal(preferences.getString("res_id",""),description,type,noofdish,note,pickupadd,preferences.getString("city",""))).enqueue(new Callback<GetFoodResponseNormal>() {
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
                            Toast.makeText(context, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                            Log.d("notification", "onResponsecatch: "+e.getLocalizedMessage());

                        }
                    }

                    @Override
                    public void onFailure(Call<GetFoodResponseNormal> call, Throwable t) {
                        Log.d("FoodPost", "onFailure: "+t.fillInStackTrace());
                    }
                });

                }
                BottomShitFragment.this.dismiss();
                sendNotifications("/topics/"+preferences.getString("city","v"),preferences.getString("name",""),"Food is Available....Hurry up.....");
            }
        });
        return binding.getRoot();
    }
    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
       // Toast.makeText(getContext(), "heyy notification", Toast.LENGTH_LONG).show();
        apiNotificationInterface.sendNotification(sender).enqueue(new Callback<GetNotificationResponse>() {
            @Override
            public void onResponse(Call<GetNotificationResponse> call, Response<GetNotificationResponse> response) {
                try {
                    Log.d("notification", "onResponse: "+response.toString());
                    Log.d("notification", "onResponse: "+call.toString());
                    if (response.code() == 200) {
                        if (response.body().success != 1) {
                            Toast.makeText(context, "Failed ", Toast.LENGTH_LONG);
                        }
                    }else{
                        Log.d("notification", "onResponse: "+response.toString());

                    }
                }
                catch (Exception e){
                    Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    Log.d("notification", "onResponse: "+e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<GetNotificationResponse> call, Throwable t) {
                Log.d("notification", "onResponse: "+t.getLocalizedMessage());
            }
        });

    }
}