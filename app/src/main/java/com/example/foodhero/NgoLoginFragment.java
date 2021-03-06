package com.example.foodhero;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Response.GetNgoResponse;
import com.example.foodhero.databinding.FragmentNgoLoginBinding;
import com.google.firebase.messaging.FirebaseMessaging;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class NgoLoginFragment extends Fragment {
    FragmentNgoLoginBinding binding;
    ApiInterface apiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNgoLoginBinding.inflate(LayoutInflater.from(getContext()),container,false);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),SigninActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        binding.admingin.setOnClickListener(new View.OnClickListener() {
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
                if(mobile.length()>0 && password.length()>0) {
                    binding.progressbar.setVisibility(View.VISIBLE);
                    binding.login.setVisibility(View.INVISIBLE);
                    apiInterface.checkLoginNgo(createPartFromString(mobile),createPartFromString(password)).enqueue(new Callback<GetNgoResponse>() {
                        @Override
                        public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
                            try{
                                if(response.body().getSuccess()){
                                    binding.progressbar.setVisibility(View.GONE);
                                    binding.login.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(), "Login Successsfull", Toast.LENGTH_SHORT).show();
                                    FirebaseMessaging.getInstance().subscribeToTopic(response.body().getData().get(0).getCity());
                                    SharedPreferences preferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=preferences.edit();
                                    if(response.body().getData().get(0).getVerification_status().equals("Verified")){
                                        editor.putString("user","NGO");
                                        editor.putBoolean("login",true);
                                        editor.putString("ngo_id",response.body().getData().get(0).get_id());
                                        editor.putString("name",response.body().getData().get(0).getName());
                                        editor.putString("email",response.body().getData().get(0).getEmail());
                                        editor.putString("password",response.body().getData().get(0).getPassword());
                                        editor.putString("imgurl",response.body().getData().get(0).getImgurl());
                                        editor.putString("state",response.body().getData().get(0).getState());
                                        editor.putString("openingtime",response.body().getData().get(0).getOpening_time());
                                        editor.putString("closingtime",response.body().getData().get(0).getClosing_time());
                                        editor.putString("district",response.body().getData().get(0).getDistrict());
                                        editor.putString("city",response.body().getData().get(0).getCity());
                                        editor.putString("address",response.body().getData().get(0).getAddress());
                                        editor.putString("authid",response.body().getData().get(0).getAuthid());
                                        editor.putString("devicetoken",response.body().getData().get(0).getDevicetoken());
                                        editor.putString("joindate",response.body().getData().get(0).getJoindate().toString());
                                        editor.putString("mobile",response.body().getData().get(0).getMobile());
                                        editor.putString("cerificate",response.body().getData().get(0).getCertificate_number());
                                        Intent intent=new Intent(getContext(),NgoMain.class);
                                        editor.apply();
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                    else{
                                        startActivity(new Intent(getContext(),NgoWaitingActivity.class));
                                    }
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
                        public void onFailure(Call<GetNgoResponse> call, Throwable t) {
                            Toast.makeText(getContext(),"here"+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            binding.progressbar.setVisibility(View.GONE);
                            binding.login.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
        });
        return binding.getRoot();
    }
    private RequestBody createPartFromString(String data){
        return RequestBody.create(MultipartBody.FORM,data);
    }
}