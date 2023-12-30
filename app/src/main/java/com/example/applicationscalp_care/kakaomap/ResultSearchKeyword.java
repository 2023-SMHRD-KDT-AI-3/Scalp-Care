package com.example.applicationscalp_care.kakaomap;

import java.util.List;

// 카카오맵 검색 API에서 받아온 키워드 검색 결과
public class ResultSearchKeyword {
    public List<Place> documents; // 검색 결과를 담는 리스트

    public ResultSearchKeyword(List<Place> documents) {
        this.documents = documents;
    }
}

