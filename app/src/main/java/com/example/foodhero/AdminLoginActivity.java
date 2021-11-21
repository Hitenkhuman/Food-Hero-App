package com.example.foodhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Response.GetAdminResponse;
import com.example.foodhero.Response.GetNgoResponse;
import com.example.foodhero.databinding.ActivityAdminLoginBinding;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminLoginActivity extends AppCompatActivity {
    ActivityAdminLoginBinding binding;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=binding.userId.getText().toString().trim();
                String password=binding.userpass.getText().toString().trim();
                if(userid.length()>0 && password.length()>0){
                    apiInterface.checkLoginAdmin(createPartFromString(userid),createPartFromString(password)).enqueue(new Callback<GetAdminResponse>() {
                        @Override
                        public void onResponse(Call<GetAdminResponse> call, Response<GetAdminResponse> response) {
                            try {
                                if(response.body().isSuccess()){
                                    Toast.makeText(getApplicationContext(), "Login Successsfull", Toast.LENGTH_SHORT).show();
                                    SharedPreferences preferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=preferences.edit();
                                    editor.clear();
                                    editor.apply();
                                    editor.putString("user","ADMIN");
                                    editor.putBoolean("login",true);
                                    editor.putString("admin_id",response.body().getData().get(0).get_id());
                                    editor.apply();
                                    startActivity(new Intent(getApplicationContext(),AdminMain.class));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                            catch (Exception e){
                                Toast.makeText(getApplicationContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<GetAdminResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });
    }

    private RequestBody createPartFromString(String data){
        return RequestBody.create(MultipartBody.FORM,data);
    }
}