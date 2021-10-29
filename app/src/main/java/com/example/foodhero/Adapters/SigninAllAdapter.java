package com.example.foodhero.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodhero.NgoLoginFragment;
import com.example.foodhero.NgoSigninFragment;
import com.example.foodhero.RestuarantLoginFragment;
import com.example.foodhero.RestuarantSigninFragment;

public class SigninAllAdapter extends FragmentStateAdapter {
    public SigninAllAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RestuarantSigninFragment();
            case 1:
               return new NgoSigninFragment();
        }
        return new RestuarantSigninFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
