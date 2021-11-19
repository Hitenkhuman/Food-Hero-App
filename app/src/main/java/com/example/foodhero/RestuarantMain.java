package com.example.foodhero;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Fragment.Restaurant.HistoryFragment;
import com.example.foodhero.Fragment.Restaurant.HomeFragment;
import com.example.foodhero.Fragment.Restaurant.MyProfileFragment;
import com.example.foodhero.Fragment.Restaurant.RequestFragment;
import com.example.foodhero.Models.Data;
import com.example.foodhero.Models.NotificationSender;
import com.example.foodhero.Response.GetNotificationResponse;
import com.example.foodhero.databinding.ActivityRestuarantMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestuarantMain extends AppCompatActivity {
    FragmentTransaction transaction;
   ActivityRestuarantMainBinding binding;
   ApiInterface apiInterface;
    String token;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRestuarantMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       apiInterface= ApiClient.getClientNotification().create(ApiInterface.class);


        transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new HomeFragment());
        transaction.commit();
        binding.bottomNavbar.setSelectedItemId(0);
        binding.bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction=getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()){
                    case R.id.home:
                        transaction.replace(R.id.container,new HomeFragment());
                        break;
                    case R.id.request:
                        transaction.replace(R.id.container,new RequestFragment());
                        break;
                    case R.id.history:
                        transaction.replace(R.id.container,new HistoryFragment());

                        break;
                    case R.id.myprofile:
                        transaction.replace(R.id.container,new MyProfileFragment());
                        break;
                }
                transaction.addToBackStack(null);
                transaction.commit();

                return true;
            }
        });

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        token = task.getResult();
//                     //   sendNotifications(token,"HEY","FOOD HERO");
//
//                      //  Toast.makeText(RestuarantMain.this, token, Toast.LENGTH_SHORT).show();
//                    }
//                });



    }

    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        Toast.makeText(getApplicationContext(), "heyy", Toast.LENGTH_LONG).show();
        apiInterface.sendNotifcation(sender).enqueue(new Callback<GetNotificationResponse>() {
            @Override
            public void onResponse(Call<GetNotificationResponse> call, Response<GetNotificationResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(RestuarantMain.this, "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNotificationResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), ""+t.fillInStackTrace(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}