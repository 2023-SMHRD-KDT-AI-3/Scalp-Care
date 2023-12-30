package com.example.applicationscalp_care;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.applicationscalp_care.databinding.ActivityTeamSogaeBinding;

public class TeamSogaeActivity extends AppCompatActivity {

    private ActivityTeamSogaeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeamSogaeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 뒤로가기
        binding.imgBack5.setOnClickListener(v ->{
            finish();
        });
        binding.tvBack5.setOnClickListener(v ->{
            finish();
        });

    }
}