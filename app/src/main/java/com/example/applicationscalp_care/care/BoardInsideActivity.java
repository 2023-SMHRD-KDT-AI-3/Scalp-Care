package com.example.applicationscalp_care.care;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.applicationscalp_care.databinding.ActivityBoardInsideBinding;

import java.util.HashMap;
import java.util.Map;

public class BoardInsideActivity extends AppCompatActivity {

    private ActivityBoardInsideBinding binding;

    private RequestQueue queue;

    String getImgURL = "http://192.168.219.57:8089/getImage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 바인딩 초기화
        binding = ActivityBoardInsideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Volley
        queue= Volley.newRequestQueue(this);

        Log.d("BoardInsideActivity1","여기 안옴?");

        // intent 객체 불러오기
        Intent data = getIntent();

        // Intent에 저장된 정보 페이지 정보들을 가져오기
        String indate = data.getStringExtra("indate");
        String content = data.getStringExtra("content");
        Long ucNum = data.getLongExtra("ucNum",0);

        Log.d("BoardInsideActivity2",String.valueOf(ucNum));

        StringRequest request = new StringRequest(
                Request.Method.POST,
                getImgURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("testBase64",String.valueOf(response.length()));
                        byte[] decodedBytes = Base64.decode(response, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        binding.imgContent.setImageBitmap(bitmap);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test","통신 실패임?");
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ucNum", String.valueOf(ucNum));
                Log.d("ucNum", String.valueOf(ucNum));
                return params;
            }

        };
        queue.add(request);

        // 날짜 및 내용
        binding.tvInsideTime.setText(indate);
        binding.tvInsideContent.setText(content);

        // 뒤로가기
        binding.tvBack2.setOnClickListener(v ->{
            finish();
        });
        binding.imgBack2.setOnClickListener(v ->{
            finish();
        });
    }
}

