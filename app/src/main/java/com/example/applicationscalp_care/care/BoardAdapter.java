package com.example.applicationscalp_care.care;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationscalp_care.R;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardViewHolder> {

    // 객체 생성
    private ArrayList<BoardVO> dataset;
    private ArrayList<String> keyset;

    public BoardAdapter(ArrayList<BoardVO> dataset, ArrayList<String> keyset) {
        // 초기화
        this.dataset = dataset;
        this.keyset = keyset;
    }

    @NonNull
    @Override // gaesigul_board.xml을 객체화 시켜 화면에 출력하는 기능 → BoardViewHolder한테 보냄
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gaesigul_board, parent, false);
        BoardViewHolder holder = new BoardViewHolder(view);
        return holder;
    }

    @Override //
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
