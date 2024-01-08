package com.example.applicationscalp_care;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoSdkLoginActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Kakao SDK 초기화
        KakaoSdk.init(this, "621eb78ca49236a6c13983164f426d08");
    }
}