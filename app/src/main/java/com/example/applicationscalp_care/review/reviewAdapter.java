package com.example.applicationscalp_care.review;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationscalp_care.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class reviewAdapter extends RecyclerView.Adapter<reviewViewHolder> {

    // 객체 생성
    private ArrayList<reviewVO> dataset;
    private ArrayList<String> keyset;

    public reviewAdapter(ArrayList<reviewVO> dataset, ArrayList<String> keyset) {
        // 초기화
        this.dataset = dataset;
        this.keyset = keyset;
    }

    @NonNull
    @Override // info_board.xml을 객체화 시켜 화면에 출력하는 기능 → reviewViewHolder한테 보냄
    public reviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_chat, parent, false);
        reviewViewHolder holder = new reviewViewHolder(view);
        return holder;
    }

    @Override // RecyclerView의 각 항목에 대한 데이터를 결합하고 해당 데이터를 화면에 표시하는 기능
    public void onBindViewHolder(@NonNull reviewViewHolder holder, int position) {
        reviewVO vo = dataset.get(position);

        holder.getTvReviewName().setText(vo.getRe_uid_name());
        holder.getTvReviewContent().setText(vo.getContent());
        holder.getTvReviewDate().setText(vo.getIndate());


        // 작성 시간을 변환하여 표시
//        String getIndate = dataset.get(position).getIndate();
//        String formatTime = timeDi(getIndate);
//        holder.getTvReviewDate().setText(formatTime);


    }

    @Override
    public int getItemCount() { return dataset.size(); }


}