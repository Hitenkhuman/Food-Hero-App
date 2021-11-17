package com.example.foodhero.Apis;

import com.example.foodhero.Response.GetNgoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("ngos/verification")
    Call<GetNgoResponse> getNgo();

}
