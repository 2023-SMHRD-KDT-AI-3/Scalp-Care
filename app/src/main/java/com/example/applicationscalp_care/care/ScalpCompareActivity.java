package com.example.applicationscalp_care.care;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationscalp_care.databinding.ActivityScalpCompareBinding;

import java.util.HashMap;
import java.util.Map;

public class ScalpCompareActivity extends AppCompatActivity {

    private ActivityScalpCompareBinding binding;
    private RequestQueue queue;
    String getImageURL = "http://192.168.219.57:8089/getImage";


    // 런처 1
    private ActivityResultLauncher<Intent> BoardRvLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    // 날짜
                    String indate = result.getData().getStringExtra("indate");
                    binding.indate1.setText(indate);

                    // 이미지
                    Long img = result.getData().getLongExtra("ucNum", 0);
                    getImage(img);

                }
            }
    );

    // 런처 2
    private ActivityResultLauncher<Intent> BoardRvLauncher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    // 날짜
                    String indate = result.getData().getStringExtra("indate");
                    binding.indate2.setText(indate);

                    // 이미지
                    Long img2 = result.getData().getLongExtra("ucNum", 0);
                    getImage2(img2);

                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScalpCompareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 초기화
        if (queue == null) {
            queue= Volley.newRequestQueue(this);
        }

        //getImage();

        SharedPreferences nowPage = getSharedPreferences("page", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = nowPage.edit();
        editor.putString("page","compare");
        editor.commit();


        // 뒤로가기
        binding.imgBack3.setOnClickListener(v -> {
            finish();
        });
        binding.tvBack3.setOnClickListener(v -> {
            finish();
        });

        // 첫번째 두피 사진 추가를 클릭 했을 때 -> BoardRvActivity.class로 이동
        binding.imgContent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScalpCompareActivity.this, BoardRvActivity.class);
                BoardRvLauncher.launch(intent);

            }
        });

        // 두번째 두피 사진 추가를 클릭 했을 때 -> BoardRvActivity.class로 이동
        binding.imgContent3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScalpCompareActivity.this, BoardRvActivity.class);
                BoardRvLauncher2.launch(intent);

            }
        });

    }

    // ucNum을 이용해 첫 번째 사진 불러오기
    public void getImage(long img){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                getImageURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("이미지 Base64",String.valueOf(response.length()));
                        byte[] decodedBytes = Base64.decode(response, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        binding.imgContent2.setImageBitmap(bitmap);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("이미지 불러오기","통신 실패!");
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Log.d("이미지불러오기 ucNum", String.valueOf(img));
                params.put("ucNum", String.valueOf(img));

                return params;
            }

        };
        queue.add(request);
    }

    // ucNum을 이용해 두 번째 사진 불러오기
    public void getImage2(long img2){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                getImageURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("이미지 Base64 22",String.valueOf(response.length()));
                        byte[] decodedBytes = Base64.decode(response, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        binding.imgContent3.setImageBitmap(bitmap);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("이미지 불러오기22","통신 실패!");
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Log.d("이미지불러오기 ucNum22", String.valueOf(img2));
                params.put("ucNum", String.valueOf(img2));

                return params;
            }

        };
        queue.add(request);
    }
}