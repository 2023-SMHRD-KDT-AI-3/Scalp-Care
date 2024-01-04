package com.example.applicationscalp_care;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationscalp_care.databinding.FragmentTestBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestFragment extends Fragment {

    private FragmentTestBinding binding;
    private RequestQueue queue;

    String modelURL = "http://192.168.219.56:5000/model";



    private ActivityResultLauncher<Intent> albumLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Log.d("런처 실행","런처실행");

                        // 기본 갤러리에서 선택한 이미지를 Uri값으로 가져온 후 Flask로 보내기
                        Intent data = result.getData();
                        Uri imgUri = data.getData();
                        Bitmap bitmap;
                        try {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContext().getContentResolver(), imgUri));
                            }else{
                                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imgUri);
                            }
                        }catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);  // PNG 형식으로 압축
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String base64_img = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        Log.d("base64변환은 성공",String.valueOf(base64_img.length()));


                        StringRequest request = new StringRequest(
                                Request.Method.POST,
                                modelURL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("responseCheck",response);

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("responseCheck",error.toString());

                                    }
                                }
                        ){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("img",base64_img);

                                return params;

                            }
                        };
                        queue.add(request);




                    }
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTestBinding.inflate(inflater, container, false);

        queue= Volley.newRequestQueue(getContext());


        // 앨범 및 카메라 띄우기
        binding.btnDoopiTest.setOnClickListener(v -> {
            // 권한 물어보기
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO}, 1);
            }else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                albumLauncher.launch(intent);
            }
        });



        return binding.getRoot();
    }



}