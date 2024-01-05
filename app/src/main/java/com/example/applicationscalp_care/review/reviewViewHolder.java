package com.example.applicationscalp_care.review;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationscalp_care.R;

public class reviewViewHolder extends RecyclerView.ViewHolder{


    // info_chat.xml에 배치한 View 객체 선언
    private TextView tvReviewName;
    private TextView tvReviewContent;
    private TextView tvReviewDate;

    // 생성자
    public reviewViewHolder(@NonNull View itemView) {
        super(itemView);
        this.tvReviewName = itemView.findViewById(R.id.tvReviewName);
        this.tvReviewContent = itemView.findViewById(R.id.tvReviewContent);
        this.tvReviewDate = itemView.findViewById(R.id.tvReviewDate);
    }


    // View 객체를 접근할 수 있는 getter 생성
    public TextView getTvReviewName() {
        return tvReviewName;
    }

    public TextView getTvReviewContent() {
        return tvReviewContent;
    }

    public TextView getTvReviewDate() {
        return tvReviewDate;
    }
}
