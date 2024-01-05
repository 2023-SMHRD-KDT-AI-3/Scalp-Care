package com.example.applicationscalp_care.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityTalmoBinding;

public class TalmoActivity extends AppCompatActivity {

    private ActivityTalmoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTalmoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 뒤로 가기
        binding.tvBack9.setOnClickListener(v -> {
            finish();
        });
        binding.imgBack6.setOnClickListener(v -> {
            finish();
        });





    }
}