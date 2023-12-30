package com.example.applicationscalp_care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 3초 동안 스플래시 화면을 표시한 후 메인 액티비티로 이동
        new Handler().postDelayed(new Runnable() {

            SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
            String uid = autoLogin.getString("uid","null");



            @Override
            public void run() {
                Log.d("SplashActivity",uid);
                Intent intent;
                // 로그인이 안되어 있으면 LoginActivity로 이동
                if(uid.equals("null")) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);

                // 로그인이 되어 있으면 MainActivity로 이동
                }else{
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}