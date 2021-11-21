package com.example.foodhero;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodhero.databinding.FragmentRestuarantSigninBinding;


public class RestuarantSigninFragment extends Fragment {

    FragmentRestuarantSigninBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding=FragmentRestuarantSigninBinding.inflate(LayoutInflater.from(getContext()),container,false);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),LoginActivity.class));
            }
        });
        binding.signinres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;
                String password;
                String confirmpassword;
                email=binding.userId.getText().toString();
                password=binding.userpass.getText().toString();
                confirmpassword=binding.cnfpass.getText().toString();
                if(email.length()!=0 && password.length()!=0 && confirmpassword.length()!=0 ){
                    Intent intent=new Intent(getContext(),OtpActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("user","RESTAURANT");
                    bundle.putString("email",email);
                    bundle.putString("password",password);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), "Please fill details properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }
}