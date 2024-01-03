package com.example.applicationscalp_care.information;

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
import com.bumptech.glide.Glide;
import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityInfoInsideBinding;

import java.util.HashMap;
import java.util.Map;

public class InfoInsideActivity extends AppCompatActivity {

    private ActivityInfoInsideBinding binding;

    private RequestQueue queue;

    String getImgURL2 = "http://192.168.219.52:8089/getImage2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 바인딩 초기화
        binding = ActivityInfoInsideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Volley
        queue= Volley.newRequestQueue(this);

        // intent 객체 불러오기
        Intent data = getIntent();

        // Intent에 저장된 정보 페이지 정보들을 가져오기
        String title = data.getStringExtra("title");
        String content = data.getStringExtra("content");
        String views = data.getStringExtra("views");
        String indate = data.getStringExtra("indate");
        String img = data.getStringExtra("img");
        Long acNum = data.getLongExtra("acNum",0);


        StringRequest request = new StringRequest(
                Request.Method.POST,
                getImgURL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("InfoInsideActivity_testBase64",String.valueOf(response.length()));
                        byte[] decodedBytes = Base64.decode(response, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        binding.infoInsideImg.setImageBitmap(bitmap);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("InfoInsideActivity_fail","통신 실패!");
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("acNum", String.valueOf(acNum));
                Log.d("acNum", String.valueOf(acNum));
                return params;
            }

        };
        queue.add(request);

        // 날짜 및 조회수
        binding.infoInsideTime.setText(indate);
        binding.infoInsideViews.setText(views);
        // 제목 및 내용
        binding.infoInsideTitle.setText(title);
        binding.infoInsideContent.setText(content);
        // 이미지
        Glide.with(this)
                .load(img)
                .into(binding.infoInsideImg);
        // 뒤로가기
        binding.tvBack4.setOnClickListener(v ->{
            finish();
        });
        binding.imgBack4.setOnClickListener(v ->{
            finish();
        });
    }
}