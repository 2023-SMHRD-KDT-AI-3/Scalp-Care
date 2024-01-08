package com.example.applicationscalp_care.information;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.applicationscalp_care.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        holder.getTvInfoTitle().setText(vo.getTitle());
        holder.getTvInfoContent().setText(vo.getContent());
        holder.getTvInfoViews().setText(vo.getViews());
        holder.getTvInfoIndate().setText(vo.getIndate());

        // 정보 페이지 게시물 누르면 InfoInsideActivity 이동
        holder.listener = new InfoItemListener() {
            @Override
            public void InfoClickListener(View v, int position) {
                Intent intent = new Intent(v.getContext(), InfoInsideActivity.class);

                intent.putExtra("title", vo.getTitle());
                intent.putExtra("content", vo.getContent());
                intent.putExtra("views", vo.getViews());
                intent.putExtra("indate", vo.getIndate());
                intent.putExtra("acNum", vo.getAc_num());

                v.getContext().startActivity(intent);
            }
        };
    }

    @Override
    public int getItemCount() { return dataset.size(); }


}
