package com.example.applicationscalp_care.kakaomap;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.databinding.ActivityCustomToastDialogBinding;

import java.util.ArrayList;

// kakaomap 커스텀 다이얼로그 : toast_message를 출력
public class CustomToastDialog {

    private ActivityCustomToastDialogBinding binding;

    public static void show(Context context, ArrayList<String> asd) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_custom_toast_dialog, null);

        TextView hospitalname = view.findViewById(R.id.hospitalname);
        TextView hospitaljuso = view.findViewById(R.id.hospitaljuso);
        TextView hospitaldoro = view.findViewById(R.id.hospitaldoro);
        TextView hospitalphone = view.findViewById(R.id.hospitalphone);
        TextView hospitalguri = view.findViewById(R.id.hospitalguri);

        String aa = asd.get(0);
        String bb = asd.get(1);
        String cc = asd.get(2);
        String dd = asd.get(3);
        String ee = asd.get(4);

        hospitalname.setText(aa);
        hospitaljuso.setText(bb);
        hospitaldoro.setText(cc);
        hospitalphone.setText(dd);
        hospitalguri.setText(ee + "Km");

        dialog.setContentView(view);
        dialog.show();

    }
}
