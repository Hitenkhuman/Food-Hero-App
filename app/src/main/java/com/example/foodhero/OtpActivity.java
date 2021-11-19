package com.example.foodhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.foodhero.databinding.ActivityOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    ActivityOtpBinding binding;
    private FirebaseAuth mAuth;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOtpBinding.inflate(LayoutInflater.from(getApplicationContext()),null,false);
        setContentView(binding.getRoot());
        bundle=getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        binding.getotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.inputmobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(OtpActivity.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                String MOBILENUMBER="+91"+binding.inputmobile.getText().toString();
                binding.progressbar.setVisibility(View.VISIBLE);
                binding.getotpbtn.setVisibility(View.INVISIBLE);
                startPhoneNumberVerification(MOBILENUMBER);

            }
        });
    }
    private void startPhoneNumberVerification(String phoneNumber) {
       PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phoneNumber).setTimeout(60L,TimeUnit.SECONDS).setActivity(OtpActivity.this)
               .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                   @Override
                   public void onVerificationCompleted(PhoneAuthCredential credential) {
                       binding.progressbar.setVisibility(View.GONE);
                       binding.getotpbtn.setVisibility(View.VISIBLE);
                   }

                   @Override
                   public void onVerificationFailed(FirebaseException e) {
                       binding.progressbar.setVisibility(View.GONE);
                       binding.getotpbtn.setVisibility(View.VISIBLE);
                       Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                       }

                   @Override
                   public void onCodeSent(@NonNull String verificationId,
                                          @NonNull PhoneAuthProvider.ForceResendingToken token) {
                       binding.progressbar.setVisibility(View.GONE);
                       binding.getotpbtn.setVisibility(View.VISIBLE);
                       Intent intent=new Intent(getApplicationContext(),OtpVerificationActivity.class);
                       bundle.putString("mobile",binding.inputmobile.getText().toString());
                       bundle.putString("authid",verificationId);
                       intent.putExtras(bundle);
                       startActivity(intent);
                   }
    }).build();
       PhoneAuthProvider.verifyPhoneNumber(options);
    }


}
