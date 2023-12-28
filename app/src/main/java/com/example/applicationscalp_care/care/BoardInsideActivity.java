package com.example.applicationscalp_care.care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityBoardInsideBinding;

public class BoardInsideActivity extends AppCompatActivity {

    private ActivityBoardInsideBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardInsideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent data = getIntent();

        String indate = data.getStringExtra("indate");
        String content = data.getStringExtra("content");
        String img = data.getStringExtra("img");

        binding.tvInsideTime.setText(indate);
        binding.tvInsideContent.setText(content);
        // 이미지 로딩 라이브러리(Glide)를 사용하여 이미지 표시
        Glide.with(this)
                .load(img)
                .into(binding.imgContent);
    }
}