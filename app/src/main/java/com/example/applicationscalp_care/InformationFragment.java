package com.example.applicationscalp_care;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applicationscalp_care.databinding.FragmentInformationBinding;


public class InformationFragment extends Fragment {

    private FragmentInformationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInformationBinding.inflate(inflater, container, false);












        return binding.getRoot();
    }
}