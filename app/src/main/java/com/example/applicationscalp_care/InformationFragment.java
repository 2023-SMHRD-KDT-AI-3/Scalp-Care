package com.example.applicationscalp_care;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationActions;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.applicationscalp_care.care.BoardVO;
import com.example.applicationscalp_care.databinding.FragmentInformationBinding;
import com.example.applicationscalp_care.information.InfoAdapter;
import com.example.applicationscalp_care.information.InfoVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class InformationFragment extends Fragment {

    // 변수 선언
    private FragmentInformationBinding binding = null;

    private ArrayList<InfoVO> dataset = null;
    private ArrayList<String> keyset = null;
    private InfoAdapter adapter = null;
    private RequestQueue queue;

    String getInfoURL = "http://192.168.219.52:8089/Newsview";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 초기화 작업
        binding = FragmentInformationBinding.inflate(inflater, container, false);
        dataset = new ArrayList<>();
        keyset = new ArrayList<>();
        adapter = new InfoAdapter( dataset, keyset);
        if (queue == null) {
            queue = Volley.newRequestQueue(requireContext());
        }

        getInfoData();

        // RecyclerView를 초기화하고, 레이아웃 매니저를 설정하고, 어댑터를 연결하여 화면에 데이터를 표시하는 기능
        binding.InfoRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.InfoRv.setAdapter(adapter);


        // 로고 누를 시, 홈 페이지 이동
        binding.scalpLogo3.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.fl, homeFragment);
            transaction.commit();
        });

        // root 리턴
        return binding.getRoot();
    }

    // 어댑터에서 사용할 날짜 형식 메소드
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

    // 게시판 출력
    public void getInfoData() {
        Log.d("InfoFragment", "데이터 가져올래요!제발");
        StringRequest request = new StringRequest(
                Request.Method.POST,
                getInfoURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("InfoFragment", "데이터 가져오는지 확인 : " + response);
                        try {
                            // JsonArray(List<String>)
                            JSONArray jsonArray = new JSONArray(response);
                            Log.d("qwer1", jsonArray.toString());

                            // 파싱한 데이터를 데이터셋에 추가
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("InfoFragment", "체크1");

                                String JsonItemString = jsonArray.getString(i);
                                // Json(Stirng) → 객체화
                                JSONObject jsonObject = new JSONObject(JsonItemString);

                                Log.d("qwer2", jsonObject.toString());

                                // 각 필요한 데이터를 추출
                                int ac_num = jsonObject.getInt("acNum");
                                String title = jsonObject.getString("title");
                                String content = jsonObject.getString("content");
                                String views = jsonObject.getString("views");
                                String img = jsonObject.getString("img");
                                String indate = InformationFragment.this.formatIndate(jsonObject.getString("indate"));

                                // 데이터셋에 추가
                                dataset.add(new InfoVO(ac_num, title, content, views, indate, img));
                            }

                            // 어댑터에 데이터셋 변경을 알림
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            Log.d("InfoFragment", "여기 문제있음1111");
                            Log.d("InfoFragment", e.toString());
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("InfoFragment", "여기 문제있음2222");
            }
        }
        );
        queue.add(request);
    }
}