package com.example.applicationscalp_care.information;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationscalp_care.R;

public class InfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // info_board.xml에 배치한 View 객체 선언
    private TextView tvInfoTitle;
    private TextView tvInfoContent;
    private TextView tvInfoViews;
    private TextView tvInfoIndate;

    // 객체 생성
    InfoItemListener listener;

    public InfoViewHolder(@NonNull View itemView) {
        super(itemView);
        // View 객체 초기화
        this.tvInfoTitle = itemView.findViewById(R.id.tvInfoTitle);
        this.tvInfoContent = itemView.findViewById(R.id.tvInfoContent);
        this.tvInfoViews = itemView.findViewById(R.id.tvInfoViews);
        this.tvInfoIndate = itemView.findViewById(R.id.tvInfoIndate);

        itemView.setOnClickListener(this::onClick);
    }

    // View 객체를 접근할 수 있는 getter 생성
    public TextView getTvInfoTitle() { return tvInfoTitle; }
    public TextView getTvInfoContent() { return tvInfoContent; }
    public TextView getTvInfoViews() { return tvInfoViews; }
    public TextView getTvInfoIndate() {
        return tvInfoIndate;
    }

    @Override
    public void onClick(View v) { this.listener.InfoClickListener(v, getLayoutPosition()); }
}
