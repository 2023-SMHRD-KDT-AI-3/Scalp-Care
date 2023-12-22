package com.example.applicationscalp_care;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applicationscalp_care.care.BoardAdapter;
import com.example.applicationscalp_care.care.BoardVO;
import com.example.applicationscalp_care.care.BoardWriteActivity;
import com.example.applicationscalp_care.databinding.FragmentCareBinding;

import java.util.ArrayList;

public class CareFragment extends Fragment {

    // 변수 선언
    private FragmentCareBinding binding = null;

    private ArrayList<BoardVO> dataset = null;
    private ArrayList<String> keyset = null;
    private BoardAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 초기화 작업
        binding = FragmentCareBinding.inflate(inflater, container, false);
        dataset = new ArrayList<>();
        keyset = new ArrayList<>();
        adapter = new BoardAdapter( dataset, keyset );

        // RecyclerView를 초기화하고, 레이아웃 매니저를 설정하고, 어댑터를 연결하여 화면에 데이터를 표시하는 기능
        binding.BoardRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.BoardRv.setAdapter(adapter);

        // 작동 버튼 클릭시 BoardWriteActivity로 이동하는 기능 구현
        binding.btnAddBoard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), BoardWriteActivity.class);
            startActivity(intent);
        });
        // root 리턴
        return binding.getRoot();
    }

    // 정보 가져와야함







}