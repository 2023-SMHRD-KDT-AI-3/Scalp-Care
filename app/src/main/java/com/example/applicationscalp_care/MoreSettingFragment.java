package com.example.applicationscalp_care;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.applicationscalp_care.databinding.ActivityTeamSogaeBinding;
import com.example.applicationscalp_care.databinding.FragmentCareBinding;
import com.example.applicationscalp_care.databinding.FragmentMoreSettingBinding;
import com.kakao.sdk.user.UserApiClient;

import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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

        // 회원 이름 및 이미지 세팅
        Glide.with(binding.imvCircularWithStroke).load(img).circleCrop().into(binding.imvCircularWithStroke);
        binding.userName.setText(name);

        binding.test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        SharedPreferences autoLogin = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLoginEdit = autoLogin.edit();
                        autoLoginEdit.clear();
                        autoLoginEdit.commit();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        return null;
                    }
                });
            }
        });

        // 팀원소개 클릭시 activity_team_sogae.xml 페이지 이동
        binding.btnTeamSogae.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), ActivityTeamSogaeBinding.class);
            startActivity(intent);
        });



        return binding.getRoot();
    }



}