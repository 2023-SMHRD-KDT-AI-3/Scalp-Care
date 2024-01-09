package com.example.applicationscalp_care;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // title 없이
        setContentView((R.layout.loading_chang));

        // 라이브러리 로딩 이미지 사용
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);

    }
}
