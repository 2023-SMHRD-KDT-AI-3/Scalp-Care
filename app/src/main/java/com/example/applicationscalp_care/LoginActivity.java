package com.example.applicationscalp_care;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.applicationscalp_care.databinding.ActivityLoginBinding;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final String TAG = "LoginActivity";
    private View loginButton;


    private RequestQueue queue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        queue= Volley.newRequestQueue(this);

        Log.d("LoginActivity","진입 성공");

        // Kakao KeyHash 확인
        String keyHash = Utility.INSTANCE.getKeyHash(this);
        Log.d("KeyHash: ", keyHash);

        loginButton = findViewById(R.id.btn_loginkakao);

        // 카카오톡이 설치되어 있는지 확인하는 메서드 , 카카오에서 제공함. 콜백 객체를 이용합.
        Function2<OAuthToken,Throwable, Unit> callback =new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            // 콜백 메서드 ,
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                Log.e(TAG,"CallBack Method " + oAuthToken);

                //oAuthToken != null 이라면 로그인 성공
                if(oAuthToken!=null){
                    // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.

                    // updateKakaoLoginUi();
                    updateKakaoLoginUi();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    //로그인 실패
                    Log.e(TAG, "invoke: login fail" );
                }

                return null;
            }
        };

        // 로그인 버튼 클릭 리스너
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 해당 기기에 카카오톡이 설치되어 있는 확인
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                }else{
                    // 카카오톡이 설치되어 있지 않다면
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
                updateKakaoLoginUi();

            }
        });

        // 로그아읏 버튼
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
//                    @Override
//                    public Unit invoke(Throwable throwable) {
//                        // updateKakaoLoginUi();
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        return null;
//                    }
//                });
//            }
//        });
//
        updateKakaoLoginUi();

    }

    private void insertLogin(String uid, String name, String classes, String email){
        Log.d("LoginActivity","DB저장 함수 실행");
        Log.d("qwer",uid);
        Log.d("qwer",name);
        Log.d("qwer",classes);
        Log.d("qwer",email);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                "http://192.168.219.52:8089/join",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("responseCheck",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("responseCheck","이미 등록된 회원입니다.");

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("m_uid",uid);
                params.put("m_name",name);
                params.put("m_class",classes);
                params.put("m_email",email);

                return params;

            }
        };
        queue.add(request);
    }













    private void updateKakaoLoginUi() {

        // 로그인 여부에 따른 UI 설정
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {

                if (user != null) {
                    // 유저의 아이디
                    String uid = user.getId().toString();
                    // 유저의 이메일
                    String email = user.getKakaoAccount().getEmail().toString();
                    // 유저의 이름(닉네임)
                    String name = user.getKakaoAccount().getProfile().getNickname().toString();

                    // 로그인 분류
                    String classes = "kakao";
                    String img = user.getKakaoAccount().getProfile().getThumbnailImageUrl().toString();

                    // 유저 정보 DB 저장
                    insertLogin(uid,name,classes,email);

                    // 유저 정보 세션 저장
                    SharedPreferences autoLogin = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor autoLoginEdit = autoLogin.edit();
                    autoLoginEdit.putString("uid",uid);
                    autoLoginEdit.putString("email",email);
                    autoLoginEdit.putString("name",name);
                    autoLoginEdit.putString("img",img);
                    autoLoginEdit.commit();

                } else {

                }

                return null;
            }
        });

    }

}