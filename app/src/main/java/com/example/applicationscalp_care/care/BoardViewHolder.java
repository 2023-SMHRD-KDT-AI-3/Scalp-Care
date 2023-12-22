package com.example.applicationscalp_care.care;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationscalp_care.R;

public class BoardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // gaesigul_board.xml에 배치한 View 객체 선언
    private TextView tvTime;
    private TextView tvContent;

    // 객체 생성
    BoardItemListener listener;

    public BoardViewHolder(@NonNull View itemView) {
        super(itemView);
        // View 객체 초기화
        this.tvTime = itemView.findViewById(R.id.tvTime);
        this.tvContent = itemView.findViewById(R.id.tvContent);
        itemView.setOnClickListener(this);
    }

    // View 객체를 접근할 수 있는 getter 생성
    public TextView getTvTime() { return tvTime; }
    public TextView getTvContent() { return tvContent; }

    @Override
    public void onClick(View v) { this.listener.BoardClickListener(v, getLayoutPosition()); }
}
