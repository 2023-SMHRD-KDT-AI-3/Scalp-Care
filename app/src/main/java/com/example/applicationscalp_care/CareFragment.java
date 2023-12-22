package com.example.applicationscalp_care;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applicationscalp_care.care.BoardAdapter;
import com.example.applicationscalp_care.care.BoardVO;
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

        adapter = new BoardAdapter(dataset, keyset);

        // root 리턴
        return binding.getRoot();
    }
}