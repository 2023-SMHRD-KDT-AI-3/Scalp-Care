package com.example.applicationscalp_care.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.applicationscalp_care.MainActivity;
import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityTestResultBinding;

public class TestResultActivity extends AppCompatActivity {

    private ActivityTestResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnTestDone.setOnClickListener(v -> {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        });
    }
}