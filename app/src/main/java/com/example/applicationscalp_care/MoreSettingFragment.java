package com.example.applicationscalp_care;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.applicationscalp_care.databinding.FragmentCareBinding;
import com.example.applicationscalp_care.databinding.FragmentMoreSettingBinding;

import java.util.HashMap;
import java.util.Map;

public class MoreSettingFragment extends Fragment {

    private FragmentMoreSettingBinding binding = null;

    private RequestQueue queue;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoreSettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreSettingFragment newInstance(String param1, String param2) {
        MoreSettingFragment fragment = new MoreSettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
    }
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreSettingBinding.inflate(inflater, container, false);

        // 회원가입 정보 보내기
        binding.test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ClickEvent","클릭 확인됨");
                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        "http://172.30.1.54:8089/join",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("responseCheck",response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ){

                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id","test");
                        params.put("name","test_name");
                        params.put("m_class","kakao");
                        params.put("email","test@test.com");


                        return params;

                    }
                };
                queue.add(request);
            }
        });

        return binding.getRoot();
    }



}