package com.example.applicationscalp_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.android.volley.RequestQueue;
import com.example.applicationscalp_care.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 실행 할 때 바로 홈 화면 나올 수 있게
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new HomeFragment()).commit();

        // bnv 바 아이콘 클릭 시
        binding.bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // 클릭한 item 정보(속성, id, title...)
                if(R.id.home == item.getItemId()) {
                    getSupportFragmentManager().beginTransaction().replace(
                            // 1) 어디에
                            R.id.fl,
                            // 2) 어떤 프래그먼트
                            new HomeFragment()
                    ).commit();
                }else if(R.id.gwanlee == item.getItemId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl, new CareFragment()).commit();
                }else if(R.id.gumsa == item.getItemId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl, new TestFragment()).commit();
                }else if(R.id.jungbo == item.getItemId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl, new InformationFragment()).commit();
                }else if(R.id.duhbogi == item.getItemId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl, new MoreSettingFragment()).commit();
                }
                return true;
            }
        });

        // 로고 누를 시 홈으로 이동
        String moveFl = getIntent().getStringExtra("moveFl");
        if(moveFl.equals("home")){
            getSupportFragmentManager().beginTransaction().replace(
                    // 1) 어디에
                    R.id.fl,
                    // 2) 어떤 프래그먼트
                    new HomeFragment()
            ).commit();
        } else if (moveFl.equals("care")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new CareFragment()).commit();
        } else if (moveFl.equals("test")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new HomeFragment()).commit();
        } else if (moveFl.equals("info")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new HomeFragment()).commit();
        } else if (moveFl.equals("moreset")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, new HomeFragment()).commit();
        }


    }
}