package com.example.applicationscalp_care;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationscalp_care.databinding.FragmentHomeBinding;
import com.example.applicationscalp_care.home.HospitalActivity;
import com.example.applicationscalp_care.information.InfoInsideActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RequestQueue queue;
    String NewsviewBestURL = "http://192.168.219.57:8089/NewsviewBest";
    String getBoardDataRecentURL = "http://192.168.219.52:8089/getBoardDataRecent";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 초기화 작업
        binding = FragmentHomeBinding.inflate(inflater, container,false);
        if (queue == null) {
            queue = Volley.newRequestQueue(requireContext());
        }

        // 근처 병원 찾기 버튼
        binding.btnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HospitalActivity.class);
                startActivity(intent);
            }
        });
        // 두피 버튼 누를 시 검사 페이지 이동
        binding.goTest1.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            TestFragment testFragment = new TestFragment();
            transaction.replace(R.id.fl, testFragment);
            transaction.commit();
        });
        // 헤어 버튼 누를 시 검사 페이지 이동
        binding.goTest2.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            TestFragment testFragment = new TestFragment();
            transaction.replace(R.id.fl, testFragment);
            transaction.commit();
        });
        // 더보기 버튼 누를 시 정보 페이지 이동
        binding.goInfo.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            InformationFragment informationFragment = new InformationFragment();
            transaction.replace(R.id.fl, informationFragment);
            transaction.commit();
        });
        // 게시글 누를 시 관리 페이지 이동
        binding.goMyBoard.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            CareFragment careFragment = new CareFragment();
            transaction.replace(R.id.fl, careFragment);
            transaction.commit();
        });
        // 로고 누를 시, 홈 페이지 이동
        binding.scalpLogo7.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.fl, homeFragment);
            transaction.commit();
        });

        // 인기 정보글 출력
        NewsviewBest();
        //
        getBoardDataRecent();

        return binding.getRoot();
    }

    // 인기 정보글 출력
    public void NewsviewBest() {
        Log.d("베스트", "왔니?");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                NewsviewBestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            // JsonArray(List<String>)
                            JSONArray jsonArray = new JSONArray(response);


                            // 파싱한 데이터를 데이터셋에 추가
                            for (int i = 0; i < jsonArray.length(); i++) {

                                String JsonItemString = jsonArray.getString(i);
                                // Json(Stirng) → 객체화
                                JSONObject jsonObject = new JSONObject(JsonItemString);

                                Log.d("베스트", jsonObject.toString());

                                // 각 필요한 데이터를 추출
                                Long ac_num = (long) jsonObject.getInt("acNum");
                                String title = jsonObject.getString("title");
                                String content = jsonObject.getString("content");
                                String views = jsonObject.getString("views");
                                String img = jsonObject.getString("img");
                                String indate = HomeFragment.this.formatIndate(jsonObject.getString("indate"));

                                if(i == 0){
                                    binding.tvBest1.setText(title);

                                    // 인기 정보글 1위 클릭 시
                                    binding.tvBest1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d("베스트","클릭");

                                            Intent intent = new Intent(getActivity(), InfoInsideActivity.class);
                                            intent.putExtra("title", title);
                                            intent.putExtra("content", content);
                                            intent.putExtra("views", views);
                                            intent.putExtra("indate", indate);
                                            intent.putExtra("acNum", ac_num);
                                            Log.d("베스트 title : ", title);
                                            Log.d("베스트 acNum : ", String.valueOf(ac_num));

                                            startActivity(intent);
                                        }
                                    });

                                }else if (i==1){
                                    binding.tvBest2.setText(title);

                                    // 인기 정보글 2위 클릭 시
                                    binding.tvBest2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d("베스트","클릭");
                                            Intent intent = new Intent(getActivity(), InfoInsideActivity.class);
                                            intent.putExtra("title", title);
                                            intent.putExtra("content", content);
                                            intent.putExtra("views", views);
                                            intent.putExtra("indate", indate);
                                            intent.putExtra("acNum", ac_num);
                                            Log.d("베스트 title : ", title);
                                            Log.d("베스트 acNum : ", String.valueOf(ac_num));

                                            startActivity(intent);
                                        }
                                    });

                                } else if (i==2) {
                                    binding.tvBest3.setText(title);

                                    // 인기 정보글 3위 클릭 시
                                    binding.tvBest3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d("베스트","클릭");
                                            Intent intent = new Intent(getActivity(), InfoInsideActivity.class);
                                            intent.putExtra("title", title);
                                            intent.putExtra("content", content);
                                            intent.putExtra("views", views);
                                            intent.putExtra("indate", indate);
                                            intent.putExtra("acNum", ac_num);
                                            Log.d("베스트 title : ", title);

                                            startActivity(intent);
                                        }
                                    });
                                }



                            }

                        } catch (JSONException e) {
                            Log.d("베스트 에러", e.toString());
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("베스트", "에러2");
            }
        }
        );
        queue.add(request);
    }

    // 날짜 형식 메소드
    private String formatIndate(String indateString) {
        try {
            long indateValue = Long.parseLong(indateString);
            Date indateDate = new Date(indateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.dd", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            return sdf.format(indateDate);
        } catch (NumberFormatException e) {
            Log.e("InformationFragment", "날짜 파싱 오류: " + e.getMessage());
            return indateString; // 변환이 실패하면 원본 문자열 반환
        }
    }

    // 최근 본인 기록 출력
    public void getBoardDataRecent() {
        Log.d("최근", "왔니?");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                getBoardDataRecentURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            if(response.equals("[]")){
                                binding.tvNoBoard.setVisibility(View.VISIBLE);
                                binding.goMyBoard.setVisibility(View.GONE);
                                binding.tvGoMyBoardDate.setVisibility(View.GONE);
                                binding.tvGoMyBoardContent.setVisibility(View.GONE);
                            }else{
                                binding.tvNoBoard.setVisibility(View.GONE);
                                binding.goMyBoard.setVisibility(View.VISIBLE);
                                binding.tvGoMyBoardDate.setVisibility(View.VISIBLE);
                                binding.tvGoMyBoardContent.setVisibility(View.VISIBLE);

                            }
                            // JsonArray(List<String>)
                            JSONArray jsonArray = new JSONArray(response);


                            // 파싱한 데이터를 데이터셋에 추가
                            for (int i = 0; i < jsonArray.length(); i++) {

                                String JsonItemString = jsonArray.getString(i);
                                // Json(Stirng) → 객체화
                                JSONObject jsonObject = new JSONObject(JsonItemString);

                                Log.d("최근", jsonObject.toString());

                                // 각 필요한 데이터를 추출
                                String indate = HomeFragment.this.formatIndate(jsonObject.getString("indate"));
                                String content = jsonObject.getString("content");
                                Long uc_num = (long) jsonObject.getInt("ucNum");
                                String img = jsonObject.getString("img");

                                if(i == 0){
                                    binding.tvGoMyBoardContent.setText(content);
                                    binding.tvGoMyBoardDate.setText(indate);

                                    // 최근 본인 기록 클릭 시
                                    binding.goMyBoard.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d("최근!","클릭");

                                            Intent intent = new Intent(getActivity(), InfoInsideActivity.class);
                                            intent.putExtra("indate", indate);
                                            intent.putExtra("content", content);
                                            intent.putExtra("ucNum", uc_num);

                                            Log.d("최근 content!: ", content);
                                            Log.d("최근 ucNum!: ", String.valueOf(uc_num));

                                            startActivity(intent);
                                        }
                                    });

                                }

                            }

                        } catch (JSONException e) {
                            Log.d("최근 에러", e.toString());
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("최근", "에러2");
            }
        }
        );
        queue.add(request);
    }
}