package com.example.applicationscalp_care.information;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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
import com.bumptech.glide.Glide;
import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityInfoInsideBinding;

import java.util.HashMap;
import java.util.Map;

public class InfoInsideActivity extends AppCompatActivity {

    private ActivityInfoInsideBinding binding;

    private RequestQueue queue;

    String getImgURL2 = "http://192.168.219.52:8089/getImage2";
    String likeInsertURL =  "http://192.168.219.52:8089/likeInsert";
    String hateInsertURL =  "http://192.168.219.52:8089/hateInsert";
    String likeViewURL =  "http://192.168.219.52:8089/likeView";
    String hateViewURL =  "http://192.168.219.52:8089/hateView";
    String likeCheckIconURL =  "http://192.168.219.52:8089/likeCheckIcon";
    String hateCheckIconURL =  "http://192.168.219.52:8089/hateCheckIcon";

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

        // 좋아요 갯수 출력
        likeView();
        // 싫어요 갯수 출력
        hateView();
        // 좋아요 그림 체크
        likeCheckIcon();
        // 싫어요 그림 체크
        hateCheckIcon();


        // acNum을 이용해 사진 불러오기
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


        // 좋아요를 클릭했을때
        binding.btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        likeInsertURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("like","좋아요 성공!");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("like","다시 도전!");

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        // autoLogin 내의 name 갖고옴
                        SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                        String uid = autoLogin.getString("uid","guest");

                        // Intent로 acNum 갖고옴
                        Intent data = getIntent();
                        Long acNum = data.getLongExtra("acNum",0);

                        Log.d("likeAcNum",String.valueOf(acNum));
                        Log.d("likeUid",uid);

                        params.put("likeAcNum", String.valueOf(acNum));
                        params.put("likeUid",uid);
                        return params;

                    }
                };
                queue.add(request);

                // 좋아요 버튼 클릭 후 딜레이
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        likeView();
                        hateView();
                        likeCheckIcon();;
                        hateCheckIcon();
                    }
                }, 1000); // 1000 밀리초 (1초) 딜레이

            }
        });

        // 싫어요를 클릭했을때
        binding.btnhate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        hateInsertURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("like_hate","싫어요 성공!");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("like_hate","다시 도전!");

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        // autoLogin 내의 name 갖고옴
                        SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                        String uid = autoLogin.getString("uid","guest");

                        // Intent로 acNum 갖고옴
                        Intent data = getIntent();
                        Long acNum = data.getLongExtra("acNum",0);

                        Log.d("likeAcNum2",String.valueOf(acNum));
                        Log.d("likeUid2",uid);

                        params.put("likeAcNum", String.valueOf(acNum));
                        params.put("likeUid",uid);
                        return params;

                    }
                };
                queue.add(request);

                // 싫어요 버튼 클릭 후 딜레이
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        likeView();
                        hateView();
                        likeCheckIcon();;
                        hateCheckIcon();
                    }
                }, 1000); // 1000 밀리초 (1초) 딜레이

            }
        });

    }

    // 좋아요 갯수 출력
    public void likeView(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                likeViewURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("likeView","Like Count: " + response);
                        binding.likeCount.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("likeView","다시 도전!");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String uid = autoLogin.getString("uid","guest");

                Intent data = getIntent();
                Long acNum = data.getLongExtra("acNum",0);

                params.put("likeAcNum", String.valueOf(acNum));
                params.put("likeUid",uid);

                return params;

            }
        };
        queue.add(request);

    }

    // 싫어요 갯수 출력
    public void hateView(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                hateViewURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hateView","hate Count: " + response);
                        binding.hateCount.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("hateView","다시 도전!");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String uid = autoLogin.getString("uid","guest");

                Intent data = getIntent();
                Long acNum = data.getLongExtra("acNum",0);

                params.put("likeAcNum", String.valueOf(acNum));
                params.put("likeUid",uid);

                return params;

            }
        };
        queue.add(request);

    }

    // 좋아요 사진 출력
    public void likeCheckIcon(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                likeCheckIconURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            binding.btnlike.setImageResource(R.drawable.thumbs_up_on);
                        }else {
                            binding.btnlike.setImageResource(R.drawable.thumbs_up_off);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("likeCheckIcon","다시 도전!");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String uid = autoLogin.getString("uid","guest");

                Intent data = getIntent();
                Long acNum = data.getLongExtra("acNum",0);

                params.put("likeAcNum", String.valueOf(acNum));
                params.put("likeUid",uid);

                return params;

            }
        };
        queue.add(request);

    }

    // 싫어요 사진 출력
    public void hateCheckIcon(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                hateCheckIconURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            binding.btnhate.setImageResource(R.drawable.thumbs_down_on);
                        }else {
                            binding.btnhate.setImageResource(R.drawable.thumbs_down_off);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("hateCheckIcon","다시 도전!");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String uid = autoLogin.getString("uid","guest");

                Intent data = getIntent();
                Long acNum = data.getLongExtra("acNum",0);

                params.put("likeAcNum", String.valueOf(acNum));
                params.put("likeUid",uid);

                return params;

            }
        };
        queue.add(request);

    }

}