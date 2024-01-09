package com.example.applicationscalp_care;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationscalp_care.databinding.FragmentTestBinding;
import com.example.applicationscalp_care.test.TestResultActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestFragment extends Fragment {

    private FragmentTestBinding binding;
    private RequestQueue queue;

    private BottomNavigationView bnv;

    String modelURL = "http://192.168.219.57:5000/model";


    private ActivityResultLauncher<Intent> albumLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d("런처 실행", "런처실행");

                        // 기본 갤러리에서 선택한 이미지를 Uri값으로 가져온 후 Flask로 보내기
                        Intent data = result.getData();
                        Uri imgUri = data.getData();

                        predict(imgUri);
                    }
                }
            }
    );

    //카메라 런처
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

                        // 형변환을 위한 임시 파일 저장
                        File cacheDir = getContext().getExternalCacheDir();
                        File tempFile = new File(cacheDir, "temp_image.jpg");

                        try {
                            FileOutputStream out = new FileOutputStream(tempFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                        Uri imgUri = Uri.fromFile(tempFile);
                        predict(imgUri);

                    }
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTestBinding.inflate(inflater, container, false);
        queue = Volley.newRequestQueue(getContext());

        // 로딩 화면 초기화
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 백그라운드를 투명하게
        loadingDialog.setCancelable(false); // 다이얼로그 외부 클릭으로 종료되지 않게
        if (loadingDialog.isShowing()) {

        }
        // bnv 초기화
        bnv = getActivity().findViewById(R.id.bnv);

        // 로고 누를 시, 홈 페이지 이동
        binding.scalpLogo2.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.fl, homeFragment);
            transaction.commit();

            bnv.setSelectedItemId(R.id.home);
        });

        // 두피 검사하기 버튼 누를 시, 팝업창
        binding.btnDoopiTest.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext()).setTitle("공지").setMessage("이 검사는 의료 진단이 아닌 병원에서 수집된 데이터를 기반으로 제작된 AI검사입니다. 상세한 검사는 의료기관을 방문해 주시기 바랍니다.")
                    .setPositiveButton("시작", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 검사 시작 누를 시
                            // 권한 물어보기
                            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO}, 1);
                            } else {
                                // activity_board_write.xml에 있는 플러스 이미지 클릭시, 앨범 또는 카메라를 띄우는 기능
                                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                                builder.setTitle("이미지 선택")
                                        .setItems(new CharSequence[]{"카메라", "갤러리"},
                                                (dialog1, which1) -> {
                                                    switch (which1) {
                                                        case 0: // 카메라 선택
                                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                            cameraLauncher.launch(intent);
                                                            loadingDialog.show(); // 로딩화면 보여주기
                                                            break;
                                                        case 1: // 갤러리 선택
                                                            Intent cameraIntent = new Intent(Intent.ACTION_PICK);
                                                            cameraIntent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                                                            albumLauncher.launch(cameraIntent);
                                                            loadingDialog.show(); // 로딩화면 보여주기
                                                            break;
                                                    }
                                                });
                                builder.create().show();
                            }
                        }
                    }).show();
        });



        // 헤어스타일 검사하기 버튼 누를 시, 팝업창
        binding.btnHairTest.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext()).setTitle("현재 서비스 준비중 입니다.").setMessage("빠른시일 내에 서비스를 제공해 드리겠습니다.")
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 취소 누를 시
                            Toast.makeText(getActivity().getApplicationContext(), "취소", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
        });

        return binding.getRoot();
    }


    public void predict(Uri imgUri){
        // bitmap으로 변환
        Bitmap bitmap;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContext().getContentResolver(), imgUri));
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imgUri);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Base64로 변환
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);  // PNG 형식으로 압축
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String base64_img = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Log.d("base64변환은 성공", String.valueOf(base64_img.length()));

        // 서버통신
        StringRequest request = new StringRequest(
                Request.Method.POST,
                modelURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseCheck", response);
                        Intent intent = new Intent(getActivity(), TestResultActivity.class);
                        intent.putExtra("response",response);
                        intent.putExtra("img",imgUri.toString());
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("responseCheck", error.toString());

                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("img", base64_img);

                return params;

            }
        };
        queue.add(request);
    }
}