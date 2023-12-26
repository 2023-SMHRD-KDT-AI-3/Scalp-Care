package com.example.applicationscalp_care;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationscalp_care.databinding.FragmentCareBinding;
import com.example.applicationscalp_care.databinding.FragmentMoreSettingBinding;

import java.util.HashMap;
import java.util.Map;

public class MoreSettingFragment extends Fragment {

    private FragmentMoreSettingBinding binding;

    private RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreSettingBinding.inflate(inflater, container, false);
        queue=Volley.newRequestQueue(getContext());

        // 회원가입 정보 보내기
        binding.test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ClickEvent","클릭 확인됨");
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        "http://192.168.219.52:8089/join",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("responseCheck",response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("responseCheck","errrrrrrrrrrrrrrrrrrror");

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("m_uid","test");
                        params.put("m_name","test_name");
                        params.put("m_class","kakao");
                        params.put("m_email","test@test.com");

                        return params;

                    }
                };
                queue.add(request);
            }
        });

        return binding.getRoot();
    }



}