package com.example.foodhero.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodhero.NgoLoginFragment;
import com.example.foodhero.RestuarantLoginFragment;

public class LoginAllAdapter extends FragmentStateAdapter {
    public LoginAllAdapter( FragmentManager fragmentManager,  Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                RestuarantLoginFragment restuarantLoginFragment=new RestuarantLoginFragment();
                return restuarantLoginFragment;
            case 1:
                NgoLoginFragment ngoLoginFragment=new NgoLoginFragment();
                return ngoLoginFragment;
        }
        return new RestuarantLoginFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
