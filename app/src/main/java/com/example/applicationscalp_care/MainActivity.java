package com.example.applicationscalp_care;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // test
        binding.fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               StringRequest request = new StringRequest(
                       Request.Method.POST,
                       "http://172.30.1.54:8080/login",
                       new Response.Listener<String>() {
                           @Override
                           public void onResponse(String response) {
                               Log.d("testtest", response);
                           }
                       },
                       new Response.ErrorListener() {
                           @Override
                           public void onErrorResponse(VolleyError error) {

                           }
                       }
               ){
                   protected Map<String, String> getParams() throws AuthFailureError{
                       Map<String, String> params = new HashMap<>();
                       params.put("1","2");
                       params.put("1","2");

                       return params;

                   }
               };
                queue.add(request);
            }
        });

        // 실행 할 때 바로 홈 화면 나올 수 있게
        getSupportFragmentManager().beginTransaction().replace(R.id.fl, new HomeFragment()).commit();

        // bnv 바 아이콘 클릭 시
        binding.bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // 클릭한 item 정보(속성, id, title...)
                if(R.id.home == item.getItemId()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl, new HomeFragment()).commit();
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
    }
}