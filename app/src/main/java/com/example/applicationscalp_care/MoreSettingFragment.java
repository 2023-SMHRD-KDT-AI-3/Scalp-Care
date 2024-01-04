package com.example.applicationscalp_care;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.applicationscalp_care.databinding.FragmentMoreSettingBinding;
import com.example.applicationscalp_care.moresetting.TeamSogaeActivity;
import com.kakao.sdk.user.UserApiClient;

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

        // 로그아웃후 로그인 페이지로 이동
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(requireContext()).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 로그아웃 누를 시
                                Toast.makeText(getActivity().getApplicationContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();
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
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 취소 누를 시
                                Toast.makeText(getActivity().getApplicationContext(), "취소", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        // 팀원소개 클릭시 activity_team_sogae.xml 페이지 이동
        binding.btnTeamSogae.setOnClickListener(v ->{
            Intent intent = new Intent(getActivity(), TeamSogaeActivity.class);
            startActivity(intent);
        });

        // 로고 누를 시, 홈 페이지 이동
        binding.scalpLogo.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.fl, homeFragment);
            transaction.commit();
        });

        // 회원탈퇴 누를 시, 팝업창
        binding.btnTaltwe.setOnClickListener(v -> {

            new AlertDialog.Builder(requireContext()).setTitle("회원탈퇴").setMessage("회원탈퇴 하시겠습니까?")
                    .setPositiveButton("회원탈퇴", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 회원탈퇴 누를 시
                            Toast.makeText(getActivity().getApplicationContext(), "회원탈퇴 성공", Toast.LENGTH_SHORT).show();



                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 취소 누를 시
                            Toast.makeText(getActivity().getApplicationContext(), "취소", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        });

        // 본인 기록 누를 시, 관리 페이지 이동
        binding.btnGoCare.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            CareFragment careFragment = new CareFragment();
            transaction.replace(R.id.fl, careFragment);
            transaction.commit();
        });

        // AI 검사 누를 시, 검사 페이지 이동
        binding.btnGoTest.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            TestFragment testFragment = new TestFragment();
            transaction.replace(R.id.fl, testFragment);
            transaction.commit();
        });


        return binding.getRoot();
    }



}