package com.example.applicationscalp_care.care;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.applicationscalp_care.CareFragment;
import com.example.applicationscalp_care.MainActivity;
import com.example.applicationscalp_care.databinding.ActivityBoardWriteBinding;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class BoardWriteActivity extends AppCompatActivity {

    private ActivityBoardWriteBinding binding;
    private RequestQueue queue;

    String writeURL="http://192.168.219.56:8089/Boardsave";

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

        // 게시글 추가페이지에 현재 일자 가져옴
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY년 MM월 dd일 HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);
        String currentTime = dateFormat.format(currentDate);
        binding.tvBoardTime.setText(currentTime);


        binding.tvBack.setOnClickListener(v -> {
            finish();
        });
        binding.imgBack.setOnClickListener(v -> {
            finish();
        });

        queue= Volley.newRequestQueue(this);

        // 저장하기 버튼을 누를시 작동
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ClickEvent","클릭 확인됨");
                Log.d("Base64",Base64.class.getPackage().getImplementationVersion());

                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String ucUid = autoLogin.getString("uid","null");

                // img → bitmap → base64(String)
                String content = binding.edtTvContent.getText().toString();

                BitmapDrawable drawable =(BitmapDrawable)binding.imgContent.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);  // PNG 형식으로 압축
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                String base64_img = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.d("BoardWriteActivity",String.valueOf(base64_img.length()));


                // 작성 내용 DB저장
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        writeURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("responseCheck",response);
                                finish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("responseCheck","errrrrrrrrrrrrrrrrrrror");

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("content",content);
                        params.put("img",base64_img);
                        params.put("ucUid",ucUid);
                        params.put("indate",currentTime);

                        return params;

                    }
                };
                queue.add(request);

            }
        });

        // activity_board_write.xml에 있는 플러스 이미지 클릭시, 앨범을 띄우는 기능
        binding.imgContent.setOnClickListener(v -> {
            // 권한 물어보기
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_VIDEO}, 1);

            }else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                albumLauncher.launch(intent);
            }
        });

        }

}