package com.example.applicationscalp_care.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityInfoInsideBinding;

public class InfoInsideActivity extends AppCompatActivity {

    private ActivityInfoInsideBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 바인딩 초기화
        binding = ActivityInfoInsideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // intent 객체 불러오기
        Intent intent = getIntent();

        // Intent에 저장된 정보 페이지 정보들을 가져오기
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String views = intent.getStringExtra("views");
        String indate = intent.getStringExtra("indate");
        String img = intent.getStringExtra("img");

        // 뒤로가기
        binding.tvBack4.setOnClickListener(v ->{
            finish();
        });
        binding.imgBack4.setOnClickListener(v ->{
            finish();
        });

        // 날짜 및 조회수
        binding.infoInsideTime.setText(indate);
        binding.infoInsideViews.setText(views);
        // 제목 및 내용
        binding.infoInsideTitle.setText(title);
        binding.infoInsideContent.setText(content);
        // 이미지
        Glide.with(this)
                .load(img)
                .into(binding.infoInsideImg);
    }
}