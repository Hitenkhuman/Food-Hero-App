package com.example.foodhero.Apis;

import com.example.foodhero.Models.FoodNormal;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.Models.NotificationSender;
import com.example.foodhero.Models.Request;
import com.example.foodhero.Models.RequestNormal;
import com.example.foodhero.Response.GetAdminResponse;
import com.example.foodhero.Response.GetFoodResponse;
import com.example.foodhero.Response.GetFoodResponseNormal;
import com.example.foodhero.Response.GetNgoResponse;
import com.example.foodhero.Response.GetNotificationResponse;
import com.example.foodhero.Response.GetRequestResponse;
import com.example.foodhero.Response.GetRequestResponseNormal;
import com.example.foodhero.Response.GetRestaurantResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {


    @GET("ngos/")
    Call<GetNgoResponse> getNgo();

    @GET("restaurants/")
    Call<GetRestaurantResponse> getRestaurant();


    @PUT("restaurants/reject/{id}")
    Call<GetRestaurantResponse> updateRejectRestaurants(@Path("id") String id);

    @Multipart
    @POST("restaurants/add")
    Call<GetRestaurantResponse> addRestaurant(@Part("name") RequestBody name,
                                              @Part("mobile") RequestBody mobile,
                                              @Part("email") RequestBody email,
                                              @Part("password") RequestBody password,
                                              @Part("opening_time") RequestBody opening_time,
                                              @Part("closing_time") RequestBody closing_time,
                                              @Part("state") RequestBody state,
                                              @Part("district") RequestBody district,
                                              @Part("address") RequestBody address,
                                              @Part("devicetoken") RequestBody devicetoken,
                                              @Part("authid") RequestBody authid,
                                              @Part("city") RequestBody city,
                                              @Part MultipartBody.Part img);


    @Multipart
    @POST("restaurants/edit")
    Call<GetRestaurantResponse> updateRestaurant(
                                                @Part("id") RequestBody id,
                                                @Part("name") RequestBody name,
                                              @Part("email") RequestBody email,
                                              @Part("opening_time") RequestBody opening_time,
                                              @Part("closing_time") RequestBody closing_time,
                                              @Part("state") RequestBody state,
                                              @Part("district") RequestBody district,
                                              @Part("address") RequestBody address,
                                              @Part("city") RequestBody city,
                                              @Part MultipartBody.Part img);

    @Multipart
    @POST("restaurants/changepassword")
    Call<GetRestaurantResponse> changePasswordRestaurant(@Part("id") RequestBody id,@Part("password") RequestBody password);

    @Multipart
    @POST("restaurants/login")
    Call<GetRestaurantResponse> checkLoginRestaurant(@Part("mobile") RequestBody mobile,@Part("password") RequestBody password);

    @GET("foods/history")
    Call<GetFoodResponse> getHistory();


    @GET("foods/history/ngo/{id}")
    Call<GetFoodResponse> getHistoryNgo(@Path("id") String id);

    @GET("foods/history/restaurant/{id}")
    Call<GetFoodResponse> getHistoryRestaurant(@Path("id") String id);

    @POST("foods/")
    Call<GetFoodResponseNormal> addFood(@Body FoodNormal foodNormal);

    @GET("foods/ngo/{id}/{city}")
    Call<GetFoodResponse> getAvailableFood(@Path("id") String id,@Path("city") String city);

    @GET("foods/restaurant/{id}")
    Call<GetFoodResponse> getAvailableFoodForRestaurant(@Path("id") String id);

    @PUT("foods/pickuptime/{id}")
    Call<GetFoodResponse> updateFoodPickup(@Path("id") String id);

    @GET("ngos/verification")
    Call<GetNgoResponse> getPendingList();


    @PUT("ngos/verify/{id}")
    Call<GetNgoResponse> updateVerifyNgo(@Path("id") String id);

    @PUT("ngos/reject/{id}")
    Call<GetNgoResponse> updateRejectNgo(@Path("id") String id);

    @Multipart
    @POST("ngos/add")
    Call<GetNgoResponse> addNgo(@Part("name") RequestBody name,
                                              @Part("mobile") RequestBody mobile,
                                              @Part("email") RequestBody email,
                                              @Part("certificate_number") RequestBody certificate_number,
                                              @Part("password") RequestBody password,
                                              @Part("opening_time") RequestBody opening_time,
                                              @Part("closing_time") RequestBody closing_time,
                                              @Part("state") RequestBody state,
                                              @Part("district") RequestBody district,
                                              @Part("address") RequestBody address,
                                              @Part("devicetoken") RequestBody devicetoken,
                                              @Part("authid") RequestBody authid,
                                              @Part("city") RequestBody city,
                                              @Part MultipartBody.Part img);



    @Multipart
    @POST("ngos/edit")
    Call<GetNgoResponse> updateNgo(
            @Part("id") RequestBody id,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("opening_time") RequestBody opening_time,
            @Part("closing_time") RequestBody closing_time,
            @Part("state") RequestBody state,
            @Part("district") RequestBody district,
            @Part("address") RequestBody address,
            @Part("city") RequestBody city,
            @Part MultipartBody.Part img);

    @Multipart
    @POST("ngos/login")
    Call<GetNgoResponse> checkLoginNgo(@Part("mobile") RequestBody mobile,@Part("password") RequestBody password);

    @Multipart
    @POST("ngos/changepassword")
    Call<GetNgoResponse> changePasswordNgo(@Part("id") RequestBody id,@Part("password") RequestBody password);

    @POST("requests/add")
    Call<GetRequestResponseNormal> addRequest(@Body RequestNormal request);

    @GET("requests/req/{id}")
    Call<GetRequestResponse> getRequest(@Path("id") String id);


    @GET("requests/all/{id}")
    Call<GetRequestResponse> getRequestForRestaurant(@Path("id") String id);

    @PUT("requests/edit/{id}")
    Call<GetRequestResponseNormal> updateRequest(@Path("id") String id);


    @Multipart
    @POST("admin/login")
    Call<GetAdminResponse> checkLoginAdmin(@Part("email") RequestBody userid, @Part("password") RequestBody password);

    @Multipart
    @POST("admin/changepassword")
    Call<GetAdminResponse> changePasswordAdmin(@Part("id") RequestBody id,@Part("password") RequestBody password);

}
