package com.example.applicationscalp_care.care;

import android.content.Intent;
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

    @Override //  RecyclerView의 각 항목에 대한 데이터를 결합하고 해당 데이터를 화면에 표시하는 기능
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
//        String indate = dataset.get(position).getIndate();
//        String content = dataset.get(position).getContent();
//        String uid = dataset.get(position).getUid();

        BoardVO vo = dataset.get(position);

        holder.getTvTime().setText(vo.getIndate());
        holder.getTvContent().setText(vo.getContent());

        holder.listener = new BoardItemListener() {
            @Override
            public void BoardClickListener(View v, int position) {
                Intent intent = new Intent(v.getContext(), BoardInsideActivity.class);

                intent.putExtra("indate", vo.getIndate());
                intent.putExtra("content", vo.getContent());
                intent.putExtra("key", keyset.get(position));

                v.getContext().startActivity(intent);
            }
        };
    }

    @Override
    public int getItemCount() { return dataset.size(); }
}
