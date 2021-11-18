package com.example.foodhero.Fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.admin.AdminNgo;
import com.example.foodhero.Fragment.admin.AdminVerificationRequests;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetNgoResponse;
import com.example.foodhero.databinding.FragmentAdminVerifyBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AdminVerify extends Fragment {

    FragmentAdminVerifyBinding binding;
    ApiInterface apiInterface;
    private final String parentdir= ApiClient.BASE_URL+"profile_pic/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentAdminVerifyBinding.inflate(LayoutInflater.from(getContext()),container,false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        Ngo res= (Ngo) getArguments().getSerializable("data");
        Glide.with(getContext()).load(parentdir+res.getImgurl()).into(binding.profileImg);
        binding.name.setText(res.getName());
        binding.ngoName.setText(res.getName());
        binding.address.setText(res.getAddress());
        binding.mobile.setText(res.getMobile());
        binding.emailid.setText(res.getEmail());
        binding.openingtime.setText(res.getOpening_time());
        binding.closingtime.setText(res.getClosing_time());
        binding.state.setText(res.getState());
        binding.district.setText(res.getDistrict());
        binding.address.setText(res.getAddress());
        binding.status.setText(res.getVerification_status());
        binding.password.setText(res.getPassword());
        binding.id.setText(res.get_id());
        binding.authid.setText(res.getAuthid());
        binding.devicetoken.setText(res.getDevicetoken());
        binding.registernumber.setText(res.getCertificate_number());
        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.admincontainer,new AdminVerificationRequests());
                transaction.commit();
            }
        });
        binding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.updateVerifyNgo(res.get_id()).enqueue(new Callback<GetNgoResponse>() {
                    @Override
                    public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
                        if(response.body().getSuccess()){
                            Toast.makeText(getContext(), "Verified Successfully", Toast.LENGTH_SHORT).show();
                            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.admincontainer,new AdminVerificationRequests());
                            transaction.commit();
                        }
                        else {
                            Toast.makeText(getContext(), "Issue with server try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNgoResponse> call, Throwable t) {
                        //Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("teststring",t.toString());
                    }
                });
            }
        });
        binding.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.updateRejectNgo(res.get_id()).enqueue(new Callback<GetNgoResponse>() {
                    @Override
                    public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
                        if(response.body().getSuccess()){
                            Toast.makeText(getContext(), "Rejected Successfully", Toast.LENGTH_SHORT).show();
                            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.admincontainer,new AdminVerificationRequests());
                            transaction.commit();
                        }
                        else {
                            Toast.makeText(getContext(), "Issue with server try again later!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetNgoResponse> call, Throwable t) {
                        Log.d("teststring",t.toString());
                    }
                });
            }
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}