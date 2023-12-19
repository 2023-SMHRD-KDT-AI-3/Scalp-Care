package com.example.applicationscalp_care;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.android.volley.RequestQueue;
import com.example.applicationscalp_care.databinding.ActivityMainBinding;

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

    }
}