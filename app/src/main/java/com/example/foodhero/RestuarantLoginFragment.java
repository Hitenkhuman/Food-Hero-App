package com.example.foodhero;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Apis.ApiNotificationClient;
import com.example.foodhero.Apis.ApiNotificationInterface;
import com.example.foodhero.Models.Data;
import com.example.foodhero.Models.NotificationSender;
import com.example.foodhero.Response.GetNotificationResponse;
import com.example.foodhero.Response.GetRestaurantResponse;
import com.example.foodhero.databinding.FragmentRestuarantLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RestuarantLoginFragment extends Fragment {

    FragmentRestuarantLoginBinding binding;
    ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding=FragmentRestuarantLoginBinding.inflate(LayoutInflater.from(getContext()),container,false);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        binding.adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),AdminLoginActivity.class);
                startActivity(intent);
            }
        });


        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile=binding.userId.getText().toString().trim();
                String password=binding.userpass.getText().toString().trim();
                if(mobile.length()>0 && password.length()>0){
                    binding.progressbar.setVisibility(View.VISIBLE);
                    binding.login.setVisibility(View.INVISIBLE);
                    apiInterface.checkLoginRestaurant(createPartFromString(mobile),createPartFromString(password)).enqueue(new Callback<GetRestaurantResponse>() {
                        @Override
                        public void onResponse(Call<GetRestaurantResponse> call, Response<GetRestaurantResponse> response) {
                            try{
                                if(response.body().isSuccess()){
                                    binding.progressbar.setVisibility(View.GONE);
                                    binding.login.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(), "Login Successsfull", Toast.LENGTH_SHORT).show();
                                    SharedPreferences preferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=preferences.edit();

                                    editor.putString("user","RESTAURANT");
                                    editor.putBoolean("login",true);
                                    editor.putString("res_id",response.body().getData().get(0).get_id());
                                    editor.putString("name",response.body().getData().get(0).getName());
                                    editor.putString("email",response.body().getData().get(0).getEmail());
                                    editor.putString("password",response.body().getData().get(0).getPassword());
                                    editor.putString("openingtime",response.body().getData().get(0).getOpening_time());
                                    editor.putString("closingtime",response.body().getData().get(0).getClosing_time());
                                    editor.putString("imgurl",response.body().getData().get(0).getImgurl());
                                    editor.putString("state",response.body().getData().get(0).getState());
                                    editor.putString("district",response.body().getData().get(0).getDistrict());
                                    editor.putString("city",response.body().getData().get(0).getCity());
                                    editor.putString("address",response.body().getData().get(0).getAddress());
                                    editor.putString("authid",response.body().getData().get(0).getAuthid());
                                    editor.putString("devicetoken",response.body().getData().get(0).getDevicetoken());
                                    editor.putString("joindate",response.body().getData().get(0).getJoindate().toString());
                                    editor.putString("mobile",response.body().getData().get(0).getMobile());
                                    Intent intent=new Intent(getContext(),RestuarantMain.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    editor.apply();
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                                    binding.progressbar.setVisibility(View.GONE);
                                    binding.login.setVisibility(View.VISIBLE);

                                }
                            }
                            catch (Exception e){
                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                binding.progressbar.setVisibility(View.GONE);
                                binding.login.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<GetRestaurantResponse> call, Throwable t) {
                            Toast.makeText(getContext(),"here"+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            binding.progressbar.setVisibility(View.GONE);
                            binding.login.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SigninActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
    private RequestBody createPartFromString(String data){
        return RequestBody.create(MultipartBody.FORM,data);
    }


}