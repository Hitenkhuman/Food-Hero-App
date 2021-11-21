package com.example.foodhero.Fragment.Ngo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodhero.Apis.ApiClient;
import com.example.foodhero.Apis.ApiInterface;
import com.example.foodhero.NgoMain;
import com.example.foodhero.R;
import com.example.foodhero.Response.GetNgoResponse;
import com.example.foodhero.Response.GetRestaurantResponse;
import com.example.foodhero.RestuarantMain;
import com.example.foodhero.databinding.FragmentUpdateMyProfileNgoBinding;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UpdateMyProfileNgo extends Fragment {

  FragmentUpdateMyProfileNgoBinding binding;
    SharedPreferences preferences;
    ApiInterface apiInterface;
    String path="";
    private int STORAGE_PERMISSION_CODE = 1;
    private final String parentdir= ApiClient.BASE_URL+"profile_pic/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentUpdateMyProfileNgoBinding.inflate(LayoutInflater.from(getContext()),container,false);

        preferences= getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=preferences.edit();
        Retrofit retrofit= ApiClient.getClient();
        apiInterface=retrofit.create(ApiInterface.class);
        Glide.with(getContext()).load(parentdir+preferences.getString("imgurl","default.png")).into(binding.profileimg);
        binding.address.setText(preferences.getString("address","NA"));
        binding.openingtime.setText(preferences.getString("openingtime","NA"));
        binding.closingtime.setText(preferences.getString("closingtime","NA"));
        binding.name.setText(preferences.getString("name","NA"));
        binding.state.setText(preferences.getString("state","NA"));
        binding.district.setText(preferences.getString("district","NA"));
        binding.city.setText(preferences.getString("city","NA"));
        binding.email.setText(preferences.getString("email","NA"));


        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                path=uri.getLastPathSegment().substring(8);
                binding.profileimg.setImageURI(uri);
                Toast.makeText(getContext(), ""+uri.toString(), Toast.LENGTH_SHORT).show();
                Log.d("imgissue", "onActivityResult: "+path);
            }
        });

        binding.imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    mGetContent.launch("image/*");
                }
                else {
                    requestStoragePermission();
                }
            }
        });
        binding.updatepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password=binding.newpass.getText().toString().trim();
                String conpassword=binding.confirmpass.getText().toString().trim();
                if(password.length()>0 && conpassword.length()>0 && password.equals(conpassword)){
                    apiInterface.changePasswordNgo(createPartFromString(preferences.getString("ngo_id","")),
                            createPartFromString(password)).enqueue(new Callback<GetNgoResponse>() {
                        @Override
                        public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
                            try {
                                if(response.body().getSuccess()){
                                    Toast.makeText(getContext(), "Password Changed", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor=preferences.edit();
                                    editor.putString("password",response.body().getData().get(0).getPassword());
                                    editor.apply();
                                    Intent intent=new Intent(getContext(), NgoMain.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                                    Log.d("imgissue", "createFilePart: "+ response.body().getMassage());

                                }
                            }
                            catch (Exception e){
                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("imgissue", "createFilePart: "+ e.getLocalizedMessage());

                            }
                        }

                        @Override
                        public void onFailure(Call<GetNgoResponse> call, Throwable t) {
                            Toast.makeText(getContext(),"here"+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("imgissue", "createFilePart: "+t.fillInStackTrace());
                        }
                    });

                }
            }
        });


        binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(path.length()>0){
                    String name=binding.name.getText().toString().trim();
                    String openingtime=binding.openingtime.getText().toString().trim();
                    String closingtime=binding.closingtime.getText().toString().trim();
                    String state=binding.state.getText().toString().trim();
                    String district=binding.district.getText().toString().trim();
                    String city=binding.city.getText().toString().toLowerCase().trim();
                    String address=binding.address.getText().toString().trim();
                    String email=binding.email.getText().toString().trim();
                    File img=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),path);
                    apiInterface.updateNgo(createPartFromString(preferences.getString("ngo_id","")),
                            createPartFromString(name),
                            createPartFromString(email),
                            createPartFromString(openingtime),
                            createPartFromString(closingtime),
                            createPartFromString(state),
                            createPartFromString(district),
                            createPartFromString(address),
                            createPartFromString(city),
                            createFilePart("img",img)
                    ).enqueue(new Callback<GetNgoResponse>() {
                        @Override
                        public void onResponse(Call<GetNgoResponse> call, Response<GetNgoResponse> response) {
                            try {
                                if(response.body().getSuccess()){
                                    Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor=preferences.edit();
                                    editor.clear();
                                    editor.apply();

                                    editor.putString("user","NGO");
                                    editor.putBoolean("login",true);
                                    editor.putString("ngo_id",response.body().getData().get(0).get_id());
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
                                    Intent intent=new Intent(getContext(), NgoMain.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getContext(), response.body().getMassage(), Toast.LENGTH_SHORT).show();
                                    Log.d("imgissue", "createFilePart: "+ response.body().getMassage());

                                }
                            }
                            catch (Exception e){
                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("imgissue", "createFilePart: "+ e.getLocalizedMessage());

                            }
                        }

                        @Override
                        public void onFailure(Call<GetNgoResponse> call, Throwable t) {

                        }
                    });
                }
                else{
                    Toast.makeText(getContext(), "Please add image", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return binding.getRoot();
    }

    private void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(getActivity())
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for upload image")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
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
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private RequestBody createPartFromString(String data){
        return RequestBody.create(MultipartBody.FORM,data);
    }

    private MultipartBody.Part createFilePart(String partName, File img){

        Log.d("imgissue", "createFilePart: "+Uri.fromFile(img));
        RequestBody requestFile=RequestBody.create(MediaType.parse(path),img);
        return  MultipartBody.Part.createFormData(partName,img.getName(),requestFile);

    }
}