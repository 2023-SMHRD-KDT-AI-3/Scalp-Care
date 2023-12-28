package com.example.applicationscalp_care.information;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationscalp_care.R;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoViewHolder> {

    // 객체 생성
    private ArrayList<InfoVO> dataset;
    private ArrayList<String> keyset;

    public InfoAdapter(ArrayList<InfoVO> dataset, ArrayList<String> keyset) {
        // 초기화
        this.dataset = dataset;
        this.keyset = keyset;
    }

    @NonNull
    @Override // info_board.xml을 객체화 시켜 화면에 출력하는 기능 → InfoViewHolder한테 보냄
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_board, parent, false);
        InfoViewHolder holder = new InfoViewHolder(view);
        return holder;
    }

    @Override // RecyclerView의 각 항목에 대한 데이터를 결합하고 해당 데이터를 화면에 표시하는 기능
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        InfoVO vo = dataset.get(position);

        holder.getTvInfoTitle().setText(vo.getInfotitle());
        holder.getTvInfoSub().setText(vo.getInfosub());
        holder.getTvInfoView().setText(vo.getInfoview());
        holder.getTvInfoDate().setText(vo.getInfoindate());

        holder.listener = new InfoItemListener() {
            @Override
            public void InfoClickListener(View v, int position) {
                Intent intent = new Intent(v.getContext(), InfoInsideActivity.class);

                intent.putExtra("title", vo.getInfotitle());
                intent.putExtra("sub", vo.getInfosub());
                intent.putExtra("view", vo.getInfoview());
                intent.putExtra("indate", vo.getInfoindate());
                intent.putExtra("key", keyset.get(position));

                v.getContext().startActivity(intent);
            }
        };
    }

    @Override
    public int getItemCount() { return dataset.size(); }
}
