package com.example.applicationscalp_care.care;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityScalpCompareBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class ScalpCompareActivity extends AppCompatActivity {

    private ActivityScalpCompareBinding binding;


    // 런처
    private ActivityResultLauncher<Intent> BoardRvLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    String indate = result.getData().getStringExtra("indate");
                    binding.indate1.setText(indate);

                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScalpCompareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences nowPage = getSharedPreferences("page", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = nowPage.edit();
        editor.putString("page","compare");
        editor.commit();


        // 뒤로가기
        binding.imgBack3.setOnClickListener(v -> {
            finish();
        });
        binding.tvBack3.setOnClickListener(v -> {
            finish();
        });

        // 첫번째 두피 사진 추가를 클릭 했을 때 -> BoardRvActivity.class로 이동
        binding.imgContent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("클릭이벤트 실행","실행확인");
                Intent intent = new Intent(ScalpCompareActivity.this, BoardRvActivity.class);
                BoardRvLauncher.launch(intent);

            }
        });


    }


}