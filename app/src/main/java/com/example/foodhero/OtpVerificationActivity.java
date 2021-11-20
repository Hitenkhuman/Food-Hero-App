package com.example.foodhero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.foodhero.databinding.ActivityOtpVerificationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends AppCompatActivity {
    ActivityOtpVerificationBinding binding;
    private String verificationID;
    private FirebaseAuth mAuth;
    private String AUTHID;
    PhoneAuthCredential phoneAuthCredential;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(LayoutInflater.from(getApplicationContext()), null, false);
        setContentView(binding.getRoot());
        bundle=getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        binding.textmobile.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));

        setupOTPInputs();
        verificationID=bundle.getString("authid");
        binding.verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.code1.getText().toString().trim().isEmpty()
                        || binding.code2.getText().toString().trim().isEmpty()
                        || binding.code3.getText().toString().trim().isEmpty()
                        || binding.code4.getText().toString().trim().isEmpty()
                        || binding.code5.getText().toString().trim().isEmpty()
                        || binding.code6.getText().toString().trim().isEmpty()){
                    Toast.makeText(OtpVerificationActivity.this, "Please Enter Valid code", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code=binding.code1.getText().toString() +
                        binding.code2.getText().toString() +
                        binding.code3.getText().toString() +
                        binding.code4.getText().toString() +
                        binding.code5.getText().toString() +
                        binding.code6.getText().toString();

                if (verificationID != null) {
                    AUTHID=verificationID;
                    binding.progressbar.setVisibility(View.VISIBLE);
                    binding.verifyotp.setVisibility(View.INVISIBLE);
                     phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationID,
                            code
                    );

                }
                signInWithCredentials();

            }
        });

        binding.resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPhoneNumberVerification("+91" + getIntent().getStringExtra("mobile"));
            }
        });

    }

    public void setupOTPInputs(){
        binding.code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    binding.code2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    binding.code3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    binding.code4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    binding.code5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        binding.code5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    binding.code6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phoneNumber).setTimeout(60L,TimeUnit.SECONDS).setActivity(OtpVerificationActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        Toast.makeText(OtpVerificationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        verificationID = verificationId;
                        Toast.makeText(OtpVerificationActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();

                    }
                }).build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithCredentials(){
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        binding.progressbar.setVisibility(View.GONE);
                        binding.verifyotp.setVisibility(View.VISIBLE);
                        if(task.isSuccessful()){
                            FirebaseUser user = task.getResult().getUser();
                            Log.d("TAG", "onComplete: "+user.getDisplayName());
                            SharedPreferences preferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            switch (bundle.getString("user")){
                                case "NGO":{
                                    editor.putString("user","NGOSIGNIN");
                                    editor.putBoolean("login",true);
                                    editor.putString("mobile",bundle.getString("mobile"));
                                    editor.putString("authid",bundle.getString("authid"));
                                    editor.putString("email",bundle.getString("emailid"));
                                    editor.putString("password",bundle.getString("password"));
                                    editor.apply();
                                    Intent intent=new Intent(getApplicationContext(),NgoGetInfoActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    break;
                                }
                                case "RESTAURANT":{
                                    editor.putString("user","RESTAURANTSIGNIN");
                                    editor.putBoolean("login",true);
                                    editor.putString("mobile",bundle.getString("mobile"));
                                    editor.putString("authid",bundle.getString("authid"));
                                    editor.putString("email",bundle.getString("emailid"));
                                    editor.putString("password",bundle.getString("password"));
                                    editor.apply();
                                    Intent intent=new Intent(getApplicationContext(),RestaurantGetInfoActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    break;

                                }
                            }
                        }
                        else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                            Log.d("TAG", "onComplete: "+task.getException());
                            Toast.makeText(OtpVerificationActivity.this,"Invalid OTP\n Please Click on RESEND OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
