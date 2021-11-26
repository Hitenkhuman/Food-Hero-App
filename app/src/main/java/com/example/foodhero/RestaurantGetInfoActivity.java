package com.example.foodhero;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.Response.GetRestaurantResponse;
import com.example.foodhero.databinding.ActivityRestaurantGetInfoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RestaurantGetInfoActivity extends AppCompatActivity {
    ActivityRestaurantGetInfoBinding binding;
    String path;
    String token;
    ApiInterface apiInterface;
    SharedPreferences preferences;
    private int STORAGE_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRestaurantGetInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferences=getSharedPreferences("data",MODE_PRIVATE);
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                     //   sendNotifications(token,"HEY","FOOD HERO");

                      //  Toast.makeText(RestuarantMain.this, token, Toast.LENGTH_SHORT).show();
                    }
                });


        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                path=uri.getLastPathSegment().substring(8);
                binding.profileImg.setImageURI(uri);
                //Toast.makeText(getApplicationContext(), ""+uri.toString(), Toast.LENGTH_SHORT).show();
                Log.d("imgissue", "onActivityResult: "+path);
            }
        });

        binding.uploadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(RestaurantGetInfoActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    mGetContent.launch("image/*");
                }
                else {
                    requestStoragePermission();
                }
            }
        });

        binding.AddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(path.length()>0){
                    String name=binding.ResName.getText().toString().trim();
                    String openingtime=binding.ResOpenTime.getText().toString().trim();
                    String closingtime=binding.ResCloseTime.getText().toString().trim();
                    String state=binding.ResState.getText().toString().trim();
                    String district=binding.ResDistrict.getText().toString().trim();
                    String city=binding.ResCity.getText().toString().toLowerCase().trim();
                    String address=binding.ResAddress.getText().toString().trim();
                    String mobile=preferences.getString("mobile","NA");
                    String email=preferences.getString("email","NA");
                    String authid=preferences.getString("authid","NA");
                    String password=preferences.getString("password","NA");
                    String devicetoken=token;
                    File img=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),path);
                    //Toast.makeText(getApplicationContext(), "FILE NAME"+img.getName(), Toast.LENGTH_SHORT).show();
                    apiInterface.addRestaurant(createPartFromString(name),
                            createPartFromString(mobile),
                            createPartFromString(email),
                            createPartFromString(password),
                            createPartFromString(openingtime),
                            createPartFromString(closingtime),
                            createPartFromString(state),
                            createPartFromString(district),
                            createPartFromString(address),
                            createPartFromString(devicetoken),
                            createPartFromString(authid),
                            createPartFromString(city),
                            createFilePart("img",img))
                            .enqueue(new Callback<GetRestaurantResponse>() {
                                @Override
                                public void onResponse(Call<GetRestaurantResponse> call, Response<GetRestaurantResponse> response) {
                                    try {
                                        if(response.body().isSuccess()){
                                            Toast.makeText(getApplicationContext(), "Sigin Completed", Toast.LENGTH_SHORT).show();
                                            SharedPreferences preferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor=preferences.edit();

                                            editor.putString("user","RESTAURANT");
                                            editor.putBoolean("login",true);
                                            editor.putString("res_id",response.body().getData().get(0).get_id());
                                            editor.putString("name",response.body().getData().get(0).getName());
                                            editor.putString("email",response.body().getData().get(0).getEmail());
                                            editor.putString("password",response.body().getData().get(0).getPassword());
                                            editor.putString("openingtime",response.body().getData().get(0).getOpening_time());
                                            editor.putString("closingtime",response.body().getData().get(0).getClosing_time());
                                            editor.putString("imgurl",response.body().getData().get(0).getImgurl());
                                            editor.putString("state",response.body().getData().get(0).getState());
                                            editor.putString("district",response.body().getData().get(0).getDistrict());
                                            editor.putString("city",response.body().getData().get(0).getCity());
                                            editor.putString("address",response.body().getData().get(0).getAddress());
                                            editor.putString("authid",response.body().getData().get(0).getAuthid());
                                            editor.putString("devicetoken",response.body().getData().get(0).getDevicetoken());
                                            editor.putString("joindate",response.body().getData().get(0).getJoindate().toString());
                                            editor.putString("mobile",response.body().getData().get(0).getMobile());


                                            editor.apply();
                                            Intent intent=new Intent(getApplicationContext(),RestuarantMain.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Something wrong with details,please check it again", Toast.LENGTH_SHORT).show();
                                            Log.d("imgissue", "createFilePart: "+ response.body().getMassage());

                                        }
                                    }
                                    catch (Exception e){
                                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        Log.d("imgissue", "createFilePart: "+ e.getLocalizedMessage());

                                    }
                                }

                                @Override
                                public void onFailure(Call<GetRestaurantResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"here"+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("imgissue", "createFilePart: "+t.fillInStackTrace());

                                }
                            });





                }
                else{
                    Toast.makeText(getApplicationContext(), "Please add image", Toast.LENGTH_SHORT).show();
                }

            }
        });
    binding.cancle.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SharedPreferences.Editor editor=preferences.edit();
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

    private MultipartBody.Part createFilePart(String partName,File img){

        Log.d("imgissue", "createFilePart: "+Uri.fromFile(img));
        RequestBody requestFile=RequestBody.create(MediaType.parse(path),img);
        return  MultipartBody.Part.createFormData(partName,img.getName(),requestFile);

    }
    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for upload image")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(RestaurantGetInfoActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}