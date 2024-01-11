package com.example.applicationscalp_care.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.applicationscalp_care.HomeFragment;
import com.example.applicationscalp_care.LoadingDialog;
import com.example.applicationscalp_care.LoginActivity;
import com.example.applicationscalp_care.MainActivity;
import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.TestFragment;
import com.example.applicationscalp_care.care.BoardWriteActivity;
import com.example.applicationscalp_care.databinding.ActivityTestResultBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TestResultActivity extends AppCompatActivity {

    private ActivityTestResultBinding binding;

    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // bnv 초기화
        bnv = TestResultActivity.this.findViewById(R.id.bnv);

        // 저장되어 있는 회원정보 접근
        SharedPreferences autoLogin = this.getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        String uid = autoLogin.getString("uid","null");


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


        // 밑줄 긋기
        binding.tvBedum.setPaintFlags(binding.tvBedum.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        // 밑줄 긋기
        binding.tvTalmo.setPaintFlags(binding.tvTalmo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // 두피 상태 텍스트 누를 시, 각 두피 상세 페이지 이동
        binding.tvMisae.setOnClickListener(v -> {
            // 밑줄 긋기
            binding.tvMisae.setPaintFlags(binding.tvMisae.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            Intent intent1 = new Intent(TestResultActivity.this, MisaeActivity.class);
            startActivity(intent1);
        });
        binding.tvPGee.setOnClickListener(v -> {
            // 밑줄 긋기
            binding.tvPGee.setPaintFlags(binding.tvPGee.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            Intent intent2 = new Intent(TestResultActivity.this, PgeeActivity.class);
            startActivity(intent2);
        });
        binding.tvSiee.setOnClickListener(v -> {
            // 밑줄 긋기
            binding.tvSiee.setPaintFlags(binding.tvSiee.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            Intent intent3 = new Intent(TestResultActivity.this, SieeHongbanActivity.class);
            startActivity(intent3);
        });
        binding.tvNongpo.setOnClickListener(v -> {
            // 밑줄 긋기
            binding.tvNongpo.setPaintFlags(binding.tvNongpo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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

        // 확인완료 누를 시  메인 페이지로 이동
        binding.btnTestDone.setOnClickListener(v -> {

            Intent testIntent = new Intent(TestResultActivity.this, MainActivity.class);
            testIntent.putExtra("moveFl","home");
            startActivity(testIntent);

            finish();

        });

        // 게시글 작성 누를 시 게시글 작성 페이지로 이동
        binding.btnTestGoBoardWrite.setOnClickListener(v -> {

            if(uid.equals("guest")){

                // 팝업창 or toast → 비회원은 작성할 수 없습니다!
                new AlertDialog.Builder(this).setTitle("죄송합니다.").setMessage("비회원은 게시글 작성이 불가합니다. \n 로그인 후 이용해 주시기 바랍니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 확인 누를 시
                                Toast.makeText(TestResultActivity.this, "확인", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 취소 누를 시
                                Toast.makeText(TestResultActivity.this, "취소", Toast.LENGTH_SHORT).show();
                            }
                        }).show();


            }else{
                Intent writeIntent = new Intent(this, BoardWriteActivity.class);
                writeIntent.putExtra("result",resultText[Integer.parseInt(result[5])]);
                writeIntent.putExtra("img",imgUri);
                startActivity(writeIntent);
                finish();
            }
        });

    }
}