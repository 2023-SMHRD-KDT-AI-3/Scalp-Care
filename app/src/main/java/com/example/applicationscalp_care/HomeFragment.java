package com.example.applicationscalp_care;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applicationscalp_care.databinding.FragmentHomeBinding;
import com.example.applicationscalp_care.home.HospitalActivity;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container,false);

        // 근처 병원 찾기 버튼
        binding.btnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HospitalActivity.class);
                startActivity(intent);
            }
        });
        // 두피 버튼 누를 시 검사 페이지 이동
        binding.goTest1.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            TestFragment testFragment = new TestFragment();
            transaction.replace(R.id.fl, testFragment);
            transaction.commit();
        });
        // 헤어 버튼 누를 시 검사 페이지 이동
        binding.goTest2.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            TestFragment testFragment = new TestFragment();
            transaction.replace(R.id.fl, testFragment);
            transaction.commit();
        });
        // 더보기 버튼 누를 시 정보 페이지 이동
        binding.goInfo.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            InformationFragment informationFragment = new InformationFragment();
            transaction.replace(R.id.fl, informationFragment);
            transaction.commit();
        });
        // 게시글 누를 시 관리 페이지 이동
        binding.goMyBoard.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            CareFragment careFragment = new CareFragment();
            transaction.replace(R.id.fl, careFragment);
            transaction.commit();
        });
        // 로고 누를 시, 홈 페이지 이동
        binding.scalpLogo7.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.fl, homeFragment);
            transaction.commit();
        });


        return binding.getRoot();
    }
}