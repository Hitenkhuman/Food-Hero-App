package com.example.foodhero.Apis;

import com.example.foodhero.Models.FoodNormal;
import com.example.foodhero.Models.Ngo;
import com.example.foodhero.Models.NotificationSender;
import com.example.foodhero.Models.Request;
import com.example.foodhero.Models.RequestNormal;
import com.example.foodhero.Response.GetFoodResponse;
import com.example.foodhero.Response.GetFoodResponseNormal;
import com.example.foodhero.Response.GetNgoResponse;
import com.example.foodhero.Response.GetNotificationResponse;
import com.example.foodhero.Response.GetRequestResponse;
import com.example.foodhero.Response.GetRequestResponseNormal;
import com.example.foodhero.Response.GetRestaurantResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA4oOsLWU:APA91bFNfRCSEuNEKp2rt1UT-78MfdeR3rADzhDSonjA1Z4KNsMWbWHXiexH6PnvstFiBWCYnwKEESZMjV3tPvjS6q48gNjhEoVZMsr6wsslNvvWa3QWwCjE6fJHfuox48bCCpEECD3r" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<GetNotificationResponse> sendNotifcation(@Body NotificationSender body);
    @GET("ngos/")
    Call<GetNgoResponse> getNgo();

    @GET("restaurants/")
    Call<GetRestaurantResponse> getRestaurant();


    @PUT("restaurants/reject/{id}")
    Call<GetRestaurantResponse> updateRejectRestaurants(@Path("id") String id);

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


    @POST("requests/add")
    Call<GetRequestResponseNormal> addRequest(@Body RequestNormal request);

    @GET("requests/req/{id}")
    Call<GetRequestResponse> getRequest(@Path("id") String id);


    @GET("requests/all/{id}")
    Call<GetRequestResponse> getRequestForRestaurant(@Path("id") String id);

    @PUT("requests/edit/{id}")
    Call<GetRequestResponseNormal> updateRequest(@Path("id") String id);



}
