package com.example.applicationscalp_care.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityPgeeBinding;

public class PgeeActivity extends AppCompatActivity {

    private ActivityPgeeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPgeeBinding.inflate(getLayoutInflater());
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