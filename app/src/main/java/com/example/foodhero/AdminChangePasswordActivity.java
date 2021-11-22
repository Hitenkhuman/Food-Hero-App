package com.example.foodhero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Response.GetAdminResponse;
import com.example.foodhero.databinding.ActivityAdminChangePasswordBinding;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AdminChangePasswordActivity extends AppCompatActivity {
    ActivityAdminChangePasswordBinding binding;
    SharedPreferences preferences;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminChangePasswordBinding.inflate(getLayoutInflater());
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        setContentView(binding.getRoot());
        preferences=getSharedPreferences("data",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        binding.conform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password=binding.oldPass.getText().toString().trim();
                String newpass=binding.newPass.getText().toString().trim();
                String conpass=binding.conformPass.getText().toString().trim();
                if(password.length()>0 && newpass.length()>0 && newpass.equals(conpass)){
                    apiInterface.changePasswordAdmin(createPartFromString(preferences.getString("admin_id","")),
                            createPartFromString(newpass)).enqueue(new Callback<GetAdminResponse>() {
                        @Override
                        public void onResponse(Call<GetAdminResponse> call, Response<GetAdminResponse> response) {
                            try {
                                if(response.body().isSuccess()){
                                    Toast.makeText(getApplicationContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor=preferences.edit();
                                    editor.putString("password",response.body().getData().get(0).getPassword());
                                    editor.apply();
                                    Intent intent=new Intent(getApplicationContext(), AdminMain.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                                    Log.d("imgissue", "createFilePart: "+ response.body().getMassage());

                                }
                            }
                            catch (Exception e){
                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("imgissue", "createFilePart: "+ e.getLocalizedMessage());

                            }
                        }

                        @Override
                        public void onFailure(Call<GetAdminResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),"here"+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("imgissue", "createFilePart: "+t.fillInStackTrace());
                        }
                    });

                }
                else{
                    Toast.makeText(getApplicationContext(), "Please fill details properly", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
    }

    private RequestBody createPartFromString(String data){
        return RequestBody.create(MultipartBody.FORM,data);
    }
}