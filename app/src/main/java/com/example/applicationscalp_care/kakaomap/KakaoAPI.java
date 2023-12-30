package com.example.applicationscalp_care.kakaomap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

// 카카오맵 검색 API에 요청
public interface KakaoAPI {
    @GET("v2/local/search/keyword.json")
    Call<ResultSearchKeyword> getSearchKeyword(
            @Header("Authorization") String key,
            @Query("query") String query,
            @Query("x") double longitude,
            @Query("y") double latitude
    );
}
