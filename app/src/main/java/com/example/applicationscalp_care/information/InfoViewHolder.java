package com.example.applicationscalp_care.information;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationscalp_care.R;

import java.lang.ref.ReferenceQueue;

public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // info_board.xml에 배치한 View 객체 선언
    private TextView tvInfoTitle;
    private TextView tvInfoSub;
    private TextView tvInfoView;
    private TextView tvInfoDate;

    // 객체 생성
    InfoItemListener listener;

    public InfoViewHolder(@NonNull View itemView) {
        super(itemView);
        // View 객체 초기화
        this.tvInfoTitle = itemView.findViewById(R.id.tvInfoTitle);
        this.tvInfoSub = itemView.findViewById(R.id.tvInfoSub);
        this.tvInfoView = itemView.findViewById(R.id.tvInfoView);
        this.tvInfoDate = itemView.findViewById(R.id.tvInfoDate);

        itemView.setOnClickListener(this::onClick);
    }

    // View 객체를 접근할 수 있는 getter 생성
    public TextView getTvInfoTitle() { return tvInfoTitle; }
    public TextView getTvInfoSub() { return tvInfoSub; }
    public TextView getTvInfoView() { return tvInfoView; }
    public TextView getTvInfoDate() { return tvInfoDate; }

    @Override
    public void onClick(View v) { this.listener.InfoClickListener(v, getLayoutPosition()); }
}
