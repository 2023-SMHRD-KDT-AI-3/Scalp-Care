package com.example.applicationscalp_care.kakaomap;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.applicationscalp_care.R;

// kakaomap 커스텀 다이얼로그 : toast_message를 출력
public class CustomToastDialog {

    public static void show(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_custom_toast_dialog, null);
        TextView textView = view.findViewById(R.id.toast_message);
        textView.setText(message);
        dialog.setContentView(view);
        dialog.show();

    }
}
