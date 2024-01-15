package com.example.applicationscalp_care.care;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.applicationscalp_care.LoginActivity;
import com.example.applicationscalp_care.MainActivity;
import com.example.applicationscalp_care.SplashActivity;
import com.example.applicationscalp_care.databinding.ActivityBoardInsideBinding;
import com.kakao.sdk.user.UserApiClient;

import java.util.HashMap;
import java.util.Map;

public class BoardInsideActivity extends AppCompatActivity {

    private ActivityBoardInsideBinding binding;

    private RequestQueue queue;

    String getImgURL = "http://192.168.219.51:8089/getImage";

    String boardDeleteURL = "http://192.168.219.51:8089/boardDelete";

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
        String result = data.getStringExtra("result");

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
        binding.tvResult.setText(result);

        // 뒤로가기
        binding.btnAcceptBoard.setOnClickListener(v ->{
            finish();
        });

        binding.btnDelBoard.setOnClickListener(v ->{

            new AlertDialog.Builder(BoardInsideActivity.this).setTitle("게시글을 삭제하시겠습니까?")
                    .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StringRequest request1 = new StringRequest(
                                    Request.Method.POST,
                                    boardDeleteURL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.d("test","통신 성공임!");
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
                            queue.add(request1);

                            // RecyclerView 최신화를 위해 0.5초 딜레이 주기
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(BoardInsideActivity.this, MainActivity.class);
                                    intent.putExtra("moveFl","care");
                                    startActivity(intent);
                                }
                            }, 500);
                        }
                    }).show();

        });

        binding.btnReWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent re_intent = new Intent(BoardInsideActivity.this, ReBoardActivity.class);
                re_intent.putExtra("ucNum",ucNum);
                re_intent.putExtra("indate",indate);
                re_intent.putExtra("result",result);
                re_intent.putExtra("content",content);
                startActivity(re_intent);
            }
        });
    }
}

