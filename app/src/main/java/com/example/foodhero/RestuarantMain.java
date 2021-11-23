package com.example.foodhero;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.foodhero.Apis.ApiNotificationInterface;
import com.example.foodhero.Fragment.Restaurant.HistoryFragment;
import com.example.foodhero.Fragment.Restaurant.HomeFragment;
import com.example.foodhero.Fragment.Restaurant.MyProfileFragment;
import com.example.foodhero.Fragment.Restaurant.RequestFragment;
import com.example.foodhero.databinding.ActivityRestuarantMainBinding;

public class RestuarantMain extends AppCompatActivity {
    FragmentTransaction transaction;

    ActivityRestuarantMainBinding binding;
   ApiNotificationInterface apiNotificationInterface;
   SharedPreferences preferences;
   HomeFragment home;
   RequestFragment request;
   HistoryFragment history;
   MyProfileFragment myprofile;
    String token;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRestuarantMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences=getSharedPreferences("data",MODE_PRIVATE);
      //  apiNotificationInterface= ApiNotificationClient.getClientNotification().create(ApiNotificationInterface.class);

        NavController navController = Navigation.findNavController(this, R.id.container);

        NavigationUI.setupWithNavController(binding.bottomNavbar, navController);


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

//    public void sendNotifications(String usertoken, String title, String message) {
//        Data data = new Data(title, message);
//        NotificationSender sender = new NotificationSender(data, usertoken);
//        Toast.makeText(getApplicationContext(), "heyy", Toast.LENGTH_LONG).show();
//        apiNotificationInterface.sendNotifcation(sender).enqueue(new Callback<GetNotificationResponse>() {
//            @Override
//            public void onResponse(Call<GetNotificationResponse> call, Response<GetNotificationResponse> response) {
//                if (response.code() == 200) {
//                    if (response.body().success != 1) {
//                        Toast.makeText(RestuarantMain.this, "Failed ", Toast.LENGTH_LONG);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetNotificationResponse> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), ""+t.fillInStackTrace(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//

}