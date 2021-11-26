package com.example.foodhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodhero.Adapters.LoginAllAdapter;
import com.example.foodhero.Apis.ApiNotificationClient;
import com.example.foodhero.Apis.ApiNotificationInterface;
import com.example.foodhero.Models.Data;
import com.example.foodhero.Models.NotificationSender;
import com.example.foodhero.Response.GetNotificationResponse;
import com.example.foodhero.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fm=this.getSupportFragmentManager();
        LoginAllAdapter adapter=new LoginAllAdapter(fm,getLifecycle());
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Restuarant"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("NGO"));
        binding.viewpager.setAdapter(adapter);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });




    }





}