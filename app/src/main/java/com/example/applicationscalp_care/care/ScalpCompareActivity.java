package com.example.applicationscalp_care.care;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityScalpCompareBinding;

public class ScalpCompareActivity extends AppCompatActivity {

    private ActivityScalpCompareBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScalpCompareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 뒤로가기
        binding.imgBack3.setOnClickListener(v -> {
            finish();
        });
        binding.tvBack3.setOnClickListener(v -> {
            finish();
        });







    }
}