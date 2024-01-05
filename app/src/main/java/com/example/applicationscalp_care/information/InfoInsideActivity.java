package com.example.applicationscalp_care.information;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.applicationscalp_care.CareFragment;
import com.example.applicationscalp_care.InformationFragment;
import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.care.BoardVO;
import com.example.applicationscalp_care.databinding.ActivityInfoInsideBinding;
import com.example.applicationscalp_care.review.reviewAdapter;
import com.example.applicationscalp_care.review.reviewVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class InfoInsideActivity extends AppCompatActivity {

    private ActivityInfoInsideBinding binding;
    private ArrayList<reviewVO> dataset = null;
    private ArrayList<String> keyset = null;
    private reviewAdapter adapter = null;
    private RequestQueue queue;

    // 좋아요 애니메이션
    ImageView btnLike, btnHate, thumbsUp, thumbsDown;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;

    String getImgURL2 = "http://192.168.219.52:8089/getImage2";
    String likeInsertURL =  "http://192.168.219.52:8089/likeInsert";
    String hateInsertURL =  "http://192.168.219.52:8089/hateInsert";
    String likeViewURL =  "http://192.168.219.52:8089/likeView";
    String hateViewURL =  "http://192.168.219.52:8089/hateView";
    String likeCheckIconURL =  "http://192.168.219.52:8089/likeCheckIcon";
    String hateCheckIconURL =  "http://192.168.219.52:8089/hateCheckIcon";
    String reviewInsertURL =  "http://192.168.219.52:8089/reviewInsert";
    String reviewViewURL =  "http://192.168.219.52:8089/reviewView";
    String ViewPlusURL =  "http://192.168.219.52:8089/ViewPlus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 바인딩 초기화
        binding = ActivityInfoInsideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Volley
        queue= Volley.newRequestQueue(this);

        // 어댑터에서 관리하는 데이터 목록을 저장하는 ArrayList
        dataset = new ArrayList<>();
        // 데이터와 관련된 키 또는 식별자 목록을 저장하는 ArrayList
        keyset = new ArrayList<>();
        // 데이터를 가져와서 뷰에 표시, dataset 및 keyset을 어댑터의 생성자에 전달
        adapter = new reviewAdapter(dataset, keyset);

        // intent 객체 불러오기
        Intent data = getIntent();

        // Intent에 저장된 info 정보 페이지 정보들을 가져오기
        String title = data.getStringExtra("title");
        String content = data.getStringExtra("content");
        String views = data.getStringExtra("views");
        String indate = InfoInsideActivity.this.formatIndate(data.getStringExtra("indate"));
        String img = data.getStringExtra("img");
        Long acNum = data.getLongExtra("acNum",0);

        // 좋아요 갯수 출력
        likeView();
        // 싫어요 갯수 출력
        hateView();
        // 좋아요 그림 체크
        likeCheckIcon();
        // 싫어요 그림 체크
        hateCheckIcon();
        // 리뷰 출력
        reviewView();
        // 조회수 증가 메소드에 글번호(Ac_num)를 담아 보냄
        ViewPlus(acNum);

        // RecyclerView를 초기화하고, 레이아웃 매니저를 설정하고, 어댑터를 연결하여 화면에 데이터를 표시하는 기능
        binding.infoChatList.setLayoutManager(new LinearLayoutManager(this));
        binding.infoChatList.setAdapter(adapter);


        // acNum을 이용해 사진 불러오기
        StringRequest request = new StringRequest(
                Request.Method.POST,
                getImgURL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("InfoInsideActivity_testBase64",String.valueOf(response.length()));
                        byte[] decodedBytes = Base64.decode(response, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        binding.infoInsideImg.setImageBitmap(bitmap);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("InfoInsideActivity_fail","통신 실패!");
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("acNum", String.valueOf(acNum));
                Log.d("acNum", String.valueOf(acNum));
                return params;
            }

        };
        queue.add(request);

        // 날짜 및 조회수
        binding.infoInsideTime.setText(indate);

        // 제목 및 내용
        binding.infoInsideTitle.setText(title);
        binding.infoInsideContent.setText(content);
        // 이미지
        Glide.with(this)
                .load(img)
                .into(binding.infoInsideImg);
        // 뒤로가기
        binding.tvBack4.setOnClickListener(v ->{
            finish();
        });
        binding.imgBack4.setOnClickListener(v ->{
            finish();
        });
        // 좋아요, 싫어요 애니메이션 초기화 작업
        btnLike = findViewById(R.id.btnlike);
        btnHate = findViewById(R.id.btnhate);
        thumbsUp = findViewById(R.id.thumbsUpMove);
        thumbsDown = findViewById(R.id.thumbsDownMove);

        // 좋아요를 클릭했을때
        binding.btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        likeInsertURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("like","좋아요 성공!");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("like","다시 도전!");

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        // autoLogin 내의 name 갖고옴
                        SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                        String uid = autoLogin.getString("uid","guest");

                        // Intent로 acNum 갖고옴
                        Intent data = getIntent();
                        Long acNum = data.getLongExtra("acNum",0);

                        Log.d("likeAcNum",String.valueOf(acNum));
                        Log.d("likeUid",uid);

                        params.put("likeAcNum", String.valueOf(acNum));
                        params.put("likeUid",uid);
                        return params;

                    }
                };
                queue.add(request);

                // 좋아요 버튼 클릭 후 딜레이
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        likeView();
                        hateView();
                        likeCheckIcon();;
                        hateCheckIcon();
                    }
                }, 1000); // 1000 밀리초 (1초) 딜레이

            }
        });

        // 싫어요를 클릭했을때
        binding.btnhate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        hateInsertURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("like_hate","싫어요 성공!");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("like_hate","다시 도전!");

                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        // autoLogin 내의 name 갖고옴
                        SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                        String uid = autoLogin.getString("uid","guest");

                        // Intent로 acNum 갖고옴
                        Intent data = getIntent();
                        Long acNum = data.getLongExtra("acNum",0);

                        Log.d("likeAcNum2",String.valueOf(acNum));
                        Log.d("likeUid2",uid);

                        params.put("likeAcNum", String.valueOf(acNum));
                        params.put("likeUid",uid);
                        return params;

                    }
                };
                queue.add(request);

                // 싫어요 버튼 클릭 후 딜레이
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        likeView();
                        hateView();
                        likeCheckIcon();;
                        hateCheckIcon();
                    }
                }, 1000); // 1000 밀리초 (1초) 딜레이

            }
        });

        // 댓글 등록 버튼을 클릭했을때
        binding.btnReviewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("review 클릭!","클릭!");

                // 기존 데이터셋 지우기
                dataset.clear();

                // 사용자가 입력한 댓글 내용 가져오기
                String rv_content = binding.edtReviewContent.getText().toString();

                // 로그인한 사용자 정보(uid) 가져오기
                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String reUid = autoLogin.getString("uid","null");
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        reviewInsertURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("review response","댓글 등록 완료!");

                                // 댓글 등록 후 키보드를 내림
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(binding.edtReviewContent.getWindowToken(), 0);
                                binding.edtReviewContent.setText(""); // 댓글 내용 지우기

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("review error","다시 해봐!");
                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("reAcNum", String.valueOf(acNum));
                        params.put("reUid",reUid);
                        params.put("content",rv_content);

                        return params;

                    }
                };
                queue.add(request);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        likeView();
                        hateView();
                        likeCheckIcon();
                        hateCheckIcon();
                        reviewView();
                    }
                }, 1000); // 1000 밀리초 (1초) 딜레이
            }
        });



    }

    // 좋아요 갯수 출력
    public void likeView(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                likeViewURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("likeView","Like Count: " + response);
                        binding.likeCount.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("likeView","다시 도전!");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String uid = autoLogin.getString("uid","guest");

                Intent data = getIntent();
                Long acNum = data.getLongExtra("acNum",0);

                params.put("likeAcNum", String.valueOf(acNum));
                params.put("likeUid",uid);

                return params;

            }
        };
        queue.add(request);

    }

    // 싫어요 갯수 출력
    public void hateView(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                hateViewURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hateView","hate Count: " + response);
                        binding.hateCount.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("hateView","다시 도전!");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String uid = autoLogin.getString("uid","guest");

                Intent data = getIntent();
                Long acNum = data.getLongExtra("acNum",0);

                params.put("likeAcNum", String.valueOf(acNum));
                params.put("likeUid",uid);

                return params;

            }
        };
        queue.add(request);

    }

    // 좋아요 사진 출력
    public void likeCheckIcon(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                likeCheckIconURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            final Drawable drawable = thumbsUp.getDrawable();
                            // 좋아요 애니메이션
                            thumbsUp.setAlpha(0.70f);
                            if (drawable instanceof AnimatedVectorDrawableCompat) {
                                avd = (AnimatedVectorDrawableCompat) drawable;
                                avd.start();
                            } else if (drawable instanceof  AnimatedVectorDrawable) {
                                avd2 = (AnimatedVectorDrawable) drawable;
                                avd2.start();
                            }
                            binding.btnlike.setImageResource(R.drawable.thumbs_up_on);
                        }else {
                            binding.btnlike.setImageResource(R.drawable.thumbs_up_off);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("likeCheckIcon","다시 도전!");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String uid = autoLogin.getString("uid","guest");

                Intent data = getIntent();
                Long acNum = data.getLongExtra("acNum",0);

                params.put("likeAcNum", String.valueOf(acNum));
                params.put("likeUid",uid);

                return params;

            }
        };
        queue.add(request);

    }

    // 싫어요 사진 출력
    public void hateCheckIcon(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                hateCheckIconURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            final Drawable drawable = thumbsDown.getDrawable();
                            // 싫어요 애니메이션
                            thumbsDown.setAlpha(0.70f);
                            if (drawable instanceof AnimatedVectorDrawableCompat) {
                                avd = (AnimatedVectorDrawableCompat) drawable;
                                avd.start();
                            } else if (drawable instanceof  AnimatedVectorDrawable) {
                                avd2 = (AnimatedVectorDrawable) drawable;
                                avd2.start();
                            }
                            binding.btnhate.setImageResource(R.drawable.thumbs_down_on);
                        }else {
                            binding.btnhate.setImageResource(R.drawable.thumbs_down_off);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("hateCheckIcon","다시 도전!");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences autoLogin = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
                String uid = autoLogin.getString("uid","guest");

                Intent data = getIntent();
                Long acNum = data.getLongExtra("acNum",0);

                params.put("likeAcNum", String.valueOf(acNum));
                params.put("likeUid",uid);

                return params;

            }
        };
        queue.add(request);
    }


    // 댓글 출력
    public void reviewView() {
        Log.d("reviewView", "리뷰 오는 중");

        StringRequest request = new StringRequest(
                Request.Method.POST,
                reviewViewURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("reviewView", response);
                        try {
                            JSONArray jsonArray = null;
                            jsonArray = new JSONArray(response);

                            // 홀수 인덱스에는 사용자 이름, 짝수 인덱스에는 댓글 데이터
                            for (int i = 0; i < jsonArray.length(); i=i+2) {
                                Log.d("reviewView","여기까진 오는건가?");

                                String JsonItemString1 = jsonArray.getString(i);
                                // Json(String) → 객체화
                                JSONObject jsonObject1 = new JSONObject(JsonItemString1);
                                String re_uid_name = jsonObject1.getString("name");

                                // Json(String) → 객체화
                                String JsonItemString2 = jsonArray.getString(i+1);
                                JSONObject jsonObject2 = new JSONObject(JsonItemString2);

                                // 각 필요한 데이터를 추출
                                int re_num = jsonObject2.getInt("reNum");
                                String content = jsonObject2.getString("content");
                                String indate = InfoInsideActivity.this.formatIndate(jsonObject2.getString("indate"));

                                reviewVO vo = new reviewVO(re_num, content, indate, re_uid_name);

                                // 데이터셋에 추가
                                dataset.add(vo);

                            }

                            // 어댑터에 데이터셋 변경을 알림
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("reviewView", "review 에러 !");
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                Intent data = getIntent();
                Long acNum = data.getLongExtra("acNum",0);
                params.put("reAcNum", String.valueOf(acNum));

                return params;

            }
        };
        queue.add(request);
    }

    // date 가져오는 거
    private String formatIndate(String indateString) {
        try {
            long indateValue = Long.parseLong(indateString);
            Date indateDate = new Date(indateValue);
            long currentTime = System.currentTimeMillis();
            long timeDifference = currentTime - indateValue;

            if (timeDifference < 60 * 1000) { // 1분 미만
                return "방금 전";
            } else if (timeDifference < 60 * 60 * 1000) { // 1시간 미만
                long minutes = timeDifference / (60 * 1000);
                return minutes + "분 전";
            } else if (timeDifference < 24 * 60 * 60 * 1000) { // 1일 미만
                long hours = timeDifference / (60 * 60 * 1000);
                return hours + "시간 전";
            } else if (timeDifference < 7 * 24 * 60 * 60 * 1000) { // 7일 미만
                long days = timeDifference / (24 * 60 * 60 * 1000);
                return days + "일 전";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                return sdf.format(indateDate);
            }
        } catch (NumberFormatException e) {
            Log.e("CareActivity", "날짜 파싱 오류: " + e.getMessage());
            return indateString; // 변환이 실패하면 원본 문자열 반환
        }
    }

    // 조회수 갯수 증가 후 출력
    public void ViewPlus(long acNum){
        Log.d("acNUm 오냐", String.valueOf(acNum));
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ViewPlusURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ViewPlus","View Count: " + response);
                        binding.infoInsideViews.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ViewPlus","다시 도전!");
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("acNum", String.valueOf(acNum));

                return params;

            }
        };
        queue.add(request);
    }

}