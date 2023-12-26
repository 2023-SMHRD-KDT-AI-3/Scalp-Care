package com.example.applicationscalp_care;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.bumptech.glide.Glide;
import com.example.applicationscalp_care.databinding.FragmentCareBinding;
import com.example.applicationscalp_care.databinding.FragmentMoreSettingBinding;

import java.util.HashMap;
import java.util.Map;

public class MoreSettingFragment extends Fragment {

    private FragmentMoreSettingBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreSettingBinding.inflate(inflater, container, false);

        // SharedPreferences에 저장된 회원정보 접근
        SharedPreferences autoLogin = getActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String img = autoLogin.getString("img","null");
        String name = autoLogin.getString("name","guest");
        Glide.with(binding.imvCircularWithStroke).load(img).circleCrop().into(binding.imvCircularWithStroke);
        binding.userName.setText(name);




        return binding.getRoot();
    }



}