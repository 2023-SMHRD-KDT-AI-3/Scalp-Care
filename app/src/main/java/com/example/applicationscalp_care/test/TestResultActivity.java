package com.example.applicationscalp_care.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

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

        Intent intent = getIntent();
        String response = intent.getStringExtra("response");
        String imgUri = intent.getStringExtra("img");

        // 이미지 적용
        Uri uri = Uri.parse(imgUri);
        binding.imgContent.setImageURI(uri);

        // 결과 가져오기
        response = response.substring(1, response.length()-2); // 대괄호 제거
        Log.d("String값 확인",response);
        String[] result = response.split(",");
        String[] resultText = {"양호","경증","중등도","중증"};

        // 결과 입력하기
        binding.result1.setText(resultText[Integer.parseInt(result[0])]);
        binding.result2.setText(resultText[Integer.parseInt(result[1])]);
        binding.result3.setText(resultText[Integer.parseInt(result[2])]);
        binding.result4.setText(resultText[Integer.parseInt(result[3])]);
        binding.result5.setText(resultText[Integer.parseInt(result[4])]);
        binding.result6.setText(resultText[Integer.parseInt(result[5])]);

        // 두피 상태 텍스트 누를 시, 각 두피 상세 페이지 이동
        binding.tvMisae.setOnClickListener(v -> {
            Intent intent1 = new Intent(TestResultActivity.this, MisaeActivity.class);
            startActivity(intent1);
        });
        binding.tvPGee.setOnClickListener(v -> {
            Intent intent2 = new Intent(TestResultActivity.this, PgeeActivity.class);
            startActivity(intent2);
        });
        binding.tvSiee.setOnClickListener(v -> {
            Intent intent3 = new Intent(TestResultActivity.this, SieeHongbanActivity.class);
            startActivity(intent3);
        });
        binding.tvNongpo.setOnClickListener(v -> {
            Intent intent4 = new Intent(TestResultActivity.this, HongbanNongActivity.class);
            startActivity(intent4);
        });
        binding.tvBedum.setOnClickListener(v -> {
            Intent intent5 = new Intent(TestResultActivity.this, BeDumActivity.class);
            startActivity(intent5);
        });
        binding.tvTalmo.setOnClickListener(v -> {
            Intent intent6 = new Intent(TestResultActivity.this, TalmoActivity.class);
            startActivity(intent6);
        });

        // 밑줄 긋기
        binding.tvMisae.setPaintFlags(binding.tvMisae.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvPGee.setPaintFlags(binding.tvPGee.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvSiee.setPaintFlags(binding.tvSiee.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvNongpo.setPaintFlags(binding.tvNongpo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvBedum.setPaintFlags(binding.tvBedum.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.tvTalmo.setPaintFlags(binding.tvTalmo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        binding.btnTestDone.setOnClickListener(v -> {

            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
            finish();

        });
    }
}