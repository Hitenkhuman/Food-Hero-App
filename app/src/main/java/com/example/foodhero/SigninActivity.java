package com.example.foodhero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.foodhero.Adapters.LoginAllAdapter;
import com.example.foodhero.Adapters.SigninAllAdapter;
import com.example.foodhero.databinding.ActivitySigninBinding;
import com.google.android.material.tabs.TabLayout;

public class SigninActivity extends AppCompatActivity {
    private ActivitySigninBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fm=this.getSupportFragmentManager();
        SigninAllAdapter adapter=new SigninAllAdapter(fm,getLifecycle());
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