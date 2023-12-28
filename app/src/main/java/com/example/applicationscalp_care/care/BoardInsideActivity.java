package com.example.applicationscalp_care.care;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityBoardInsideBinding;

public class BoardInsideActivity extends AppCompatActivity {

    private ActivityBoardInsideBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardInsideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        Intent data = getIntent();
//
//        String indate = data.getStringExtra("indate");
//        String content = data.getStringExtra("content");
//
//        binding.tvInsideTime.setText(indate);
//        binding.tvInsideContent.setText(content);
    }
}