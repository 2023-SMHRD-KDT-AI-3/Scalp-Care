package com.example.applicationscalp_care.care;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationscalp_care.R;

import java.io.ByteArrayOutputStream;
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
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.gaesigul_board, parent, false);
        BoardViewHolder holder = new BoardViewHolder(view1);
        return holder;
    }

    @Override //  RecyclerView의 각 항목에 대한 데이터를 결합하고 해당 데이터를 화면에 표시하는 기능
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {

        BoardVO vo = dataset.get(position);

        holder.getTvTime().setText(vo.getIndate());
        holder.getTvContent().setText(vo.getContent());

        holder.listener = new BoardItemListener() {
            @Override
            public void BoardClickListener(View v, int position) {
                Intent intent ;

                SharedPreferences nowPage = v.getContext().getSharedPreferences("page", Context.MODE_PRIVATE);
                String page = nowPage.getString("page","null");
                Log.d("Adapter에서 확인",page);


                if(page.equals("care")){
                    intent = new Intent(v.getContext(),BoardInsideActivity.class);
                    intent.putExtra("indate", vo.getIndate());
                    intent.putExtra("content", vo.getContent());
                    intent.putExtra("ucNum",vo.getUc_num());
                    v.getContext().startActivity(intent);

                }else if(page.equals("compare")){
                    intent = new Intent();
                    intent.putExtra("indate", vo.getIndate());
                    intent.putExtra("content", vo.getContent());
                    intent.putExtra("ucNum",vo.getUc_num());

                    ((Activity)v.getContext()).setResult(Activity.RESULT_OK,intent);
                    ((Activity) v.getContext()).finish();

                }


            }
        };

    }


    @Override
    public int getItemCount() { return dataset.size(); }


}
