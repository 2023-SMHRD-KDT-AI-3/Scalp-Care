package com.example.applicationscalp_care.care;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityBoardWriteBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardWriteActivity extends AppCompatActivity {

    private ActivityBoardWriteBinding binding;

    // 연결 해야함



    // 앨범 런처
    private ActivityResultLauncher<Intent> albumLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        // 기본 갤러리에서 선택한 이미지를 Uri값으로 가져온 후 ImageView 초기화
                        Intent data = result.getData();
                        Uri imgUri = data.getData();
                        binding.imgContent.setImageURI(imgUri);
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                // 캡쳐한 이미지 저장 공간을 접근 후 가져오기
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bundle bundle = data.getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        binding.imgContent.setImageBitmap(bitmap);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardWriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvBack.setOnClickListener(v -> {
            finish();
        });
        binding.imgBack.setOnClickListener(v -> {
            finish();
        });


        // 관리 페이지 플러스 이미지 누를 시 작동
        binding.btnAdd.setOnClickListener(v -> {

            SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd HH:mm");
            long now = System.currentTimeMillis();
            Date today = new Date(now);

            // 날짜(시간), 내용, Uid(사용자 구별 값)
            String time = format.format(today);
            String content = binding.edtTvContent.getText().toString();
            // Uid 추가해야함

            // 이미지 저장

            // 종료
            finish();
        });

        // activity_board_write.xml에 있는 플러스 이미지 클릭시, 앨범을 띄우는 기능
        binding.imgContent.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
            albumLauncher.launch(intent);
        });

    }
}