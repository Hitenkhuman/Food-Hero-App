package com.example.foodhero.Fragment.Ngo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    Uri uriimg;
    Context context;
    ContentResolver contentResolver;
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
        contentResolver=getActivity().getContentResolver();
        apiInterface=retrofit.create(ApiInterface.class);
        context=getContext();
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
                uriimg=uri;
                path=uri.getLastPathSegment();
                binding.profileimg.setImageURI(uri);
               // Toast.makeText(getContext(), ""+uri.toString(), Toast.LENGTH_SHORT).show();
                Log.d("imgissue", "onActivityResult file path: "+path);
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


                                }
                            }
                            catch (Exception e){
                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                            }
                        }

                        @Override
                        public void onFailure(Call<GetNgoResponse> call, Throwable t) {
                            Toast.makeText(getContext(),"here"+ t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else{
                    Toast.makeText(getContext(), "Please fill details properly", Toast.LENGTH_SHORT).show();
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
                    File img=new File(getFullPathFromContentUri(context,uriimg));
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

        Log.d("imgissue", "createFilePart url at bottom: "+Uri.fromFile(img));
        RequestBody requestFile=RequestBody.create(MediaType.parse(Uri.fromFile(img).toString()),img);
        return  MultipartBody.Part.createFormData(partName,img.getName(),requestFile);

    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

//    public String getImageFilePath(Uri uri) {
//        String path = null, image_id = null;
//
//        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            image_id = cursor.getString(0);
//            image_id = image_id.substring(image_id.lastIndexOf(":") + 1);
//            cursor.close();
//        }
//
//        Cursor cursor1 = getActivity().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
//        if (cursor1!=null) {
//            cursor1.moveToFirst();
//            path = cursor1.getString(cursor1.getColumnIndex(MediaStore.Images.Media.DATA));
//            cursor1.close();
//        }
//        return path;
//    }


//    @SuppressLint("ObsoleteSdkInt")
//    public String getPathFromURI(Uri uri){
//        String realPath="";
//// SDK < API11
//        if (Build.VERSION.SDK_INT < 11) {
//            String[] proj = { MediaStore.Images.Media.DATA };
//            @SuppressLint("Recycle") Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
//            int column_index = 0;
//            String result="";
//            if (cursor != null) {
//                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                realPath=cursor.getString(column_index);
//            }
//        }
//        // SDK >= 11 && SDK < 19
//        else if (Build.VERSION.SDK_INT < 19){
//            String[] proj = { MediaStore.Images.Media.DATA };
//            CursorLoader cursorLoader = new CursorLoader(getContext(), uri, proj, null, null, null);
//            Cursor cursor = cursorLoader.loadInBackground();
//            if(cursor != null){
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                realPath = cursor.getString(column_index);
//            }
//        }
//        // SDK > 19 (Android 4.4)
//        else{
//            String wholeID = DocumentsContract.getDocumentId(uri);
//            // Split at colon, use second item in the array
//            String id = wholeID.split(":")[1];
//            String[] column = { MediaStore.Images.Media.DATA };
//            // where id is equal to
//            String sel = MediaStore.Images.Media._ID + "=?";
//            Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{ id }, null);
//            int columnIndex = 0;
//            if (cursor != null) {
//                columnIndex = cursor.getColumnIndex(column[0]);
//                if (cursor.moveToFirst()) {
//                    realPath = cursor.getString(columnIndex);
//                }
//                cursor.close();
//            }
//        }
//        return realPath;
//    }
public static String getFullPathFromContentUri(final Context context, final Uri uri) {

    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    String filePath="";
    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        // ExternalStorageProvider
        if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }//non-primary e.g sd card
            else {

                if (Build.VERSION.SDK_INT > 20) {
                    //getExternalMediaDirs() added in API 21
                    File extenal[] = context.getExternalMediaDirs();
                    for (File f : extenal) {
                        filePath = f.getAbsolutePath();
                        if (filePath.contains(type)) {
                            int endIndex = filePath.indexOf("Android");
                            filePath = filePath.substring(0, endIndex) + split[1];
                        }
                    }
                }else{
                    filePath = "/storage/" + type + "/" + split[1];
                }
                return filePath;
            }
        }
        // DownloadsProvider
        else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {

            final String id = DocumentsContract.getDocumentId(uri);
            final Uri contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

            return getDataColumn(context, contentUri, null, null);
        }
        // MediaProvider
        else if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            final String type = split[0];

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{
                    split[1]
            };

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {
                    column
            };

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }
    }
    // MediaStore (and general)
    else if ("content".equalsIgnoreCase(uri.getScheme())) {
        return getDataColumn(context, uri, null, null);
    }
    // File
    else if ("file".equalsIgnoreCase(uri.getScheme())) {
        return uri.getPath();
    }

    return null;
}

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


}