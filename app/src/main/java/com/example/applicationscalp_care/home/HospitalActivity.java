package com.example.applicationscalp_care.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.applicationscalp_care.R;
import com.example.applicationscalp_care.kakaomap.CustomToastDialog;
import com.example.applicationscalp_care.kakaomap.KakaoAPI;
import com.example.applicationscalp_care.kakaomap.Place;
import com.example.applicationscalp_care.kakaomap.ResultSearchKeyword;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HospitalActivity extends AppCompatActivity implements MapView.POIItemEventListener{

    private static final int ACCESS_FINE_LOCATION = 3000;
    private static final String API_KEY = "KakaoAK 9f80969872d8d710bcc5dfc4dd6603c4";
    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        Log.d("현재 동작?","1");

        // 현재 위치 버튼
        Button btnReturnToCurrentLocation = findViewById(R.id.btnReturnToCurrentLocation);
        btnReturnToCurrentLocation.bringToFront();

        // 지도
        mapView = new MapView(this);
        setContentView(mapView);

        // POI(POIItemEventListener) 이벤트 리스너 등록
        mapView.setPOIItemEventListener(this);

        // 현재 위치
        startTracking();

        // 현재 위치 버튼 클릭시 동작
        if (btnReturnToCurrentLocation != null) {
            btnReturnToCurrentLocation.setOnClickListener(view -> startTracking());
        } else {
            // 버튼을 찾을 수 없는 경우
            Log.e("현재 버튼 상태", "버튼을 찾을 수 없습니다.");
        }


    }


    // 권한 요청 후 동작
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show();
                startTracking();
            } else {
                // 권한 요청 후 거절됨
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }



    // 현재 위치
    private void startTracking() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // ACCESS_FINE_LOCATION 권한이 부여되었는지 확인
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // 권한이 부여 확인 -> 마지막으로 알려진 위치를 가져옴
                android.location.Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double currentLatitude = location.getLatitude();
                    double currentLongitude = location.getLongitude();

                    mapView.setShowCurrentLocationMarker(false);
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

                    // 현재 위치로 지도 중심 이동
                     MapPoint currentLocation = MapPoint.mapPointWithGeoCoord(currentLatitude, currentLongitude);
                     mapView.setMapCenterPoint(currentLocation, true);

                    // 현재 위치를 기반으로 병원 검색
                    searchHospitalsNearby(currentLatitude, currentLongitude);

                } else {
                    Toast.makeText(this, "위치 정보를 가져올 수 없습니다", Toast.LENGTH_SHORT).show();
                }
            } else {
                // 권한이 부여되지 않음 -> 권한을 요청
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
            }
        } else {
            Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show();
        }
    }

    // 주변 병원 검색
    private void searchHospitalsNearby(double latitude, double longitude) {
        // 검색 API 호출
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        KakaoAPI api = retrofit.create(KakaoAPI.class);

        Call<ResultSearchKeyword> call = api.getSearchKeyword(API_KEY, "탈모병원",  longitude,latitude);
        Log.d("현재 위도", String.valueOf(latitude));
        Log.d("현재 경도", String.valueOf(longitude));

        call.enqueue(new Callback<ResultSearchKeyword>() {
            @Override
            public void onResponse(Call<ResultSearchKeyword> call, retrofit2.Response<ResultSearchKeyword> response) {
                if (response.isSuccessful()) {
                    ResultSearchKeyword result = response.body();
                    if (result != null && result.documents != null && !result.documents.isEmpty()) {
                        // 검색 결과를 마커로 표시
                        Log.d("현재 result.documents",result.documents.toString());
                        for (Place place : result.documents) {
                            addMarkerForPlace(place);
                        }
                    }
                } else {
                    // 서버 응답이 실패한 경우
                    Log.e("HospitalActivity", "서버 응답 실패: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
                Log.w("HospitalActivity", "통신 실패: " + t.getMessage());
            }
        });
    }

    private void addMarkerForPlace(Place place) {
        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName(place.place_name);
        customMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(place.y, place.x));
        Log.d("현재 위도1", String.valueOf(place.y));
        Log.d("현재 경도1", String.valueOf(place.x));

        // 병원 커스텀 이미지 마커
        customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        customMarker.setCustomImageResourceId(R.drawable.map_hospital_icon);
        // 선택될 때 보일 이미지 - x
        customMarker.setCustomSelectedImageResourceId(R.drawable.home_hospital_icon);

        customMarker.setUserObject(place); // -> userObject
        mapView.addPOIItem(customMarker);
    }

    // POI 여기 부터
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        // 마커를 클릭할 때 호출되는 메서드
        Object userObject = mapPOIItem.getUserObject();
        Log.d("현재 마커 클릭","현재 마커 클릭!");

        if (userObject instanceof Place) {
            // 마커에 첨부된 데이터가 Place 객체인 경우
            Log.d("현재 객체","객체맞아!");
            Place selectedPlace = (Place) userObject;
            showPlaceDetails(selectedPlace);
        } else {
            Log.d("현재 객체", "userObject는 Place 클래스의 인스턴스가 아닙니다!");
        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    // POI 여기 까지


    // POI 동작 -> 선택된 장소의 상세 정보
    private void showPlaceDetails(Place place) {
        String place_name = place.place_name;
        String address = place.address_name;
        String roadAddress = place.road_address_name;
        String phone = place.phone;
        float preDistance = place.distance/1000.0f;
        String distance = String.format("%.1f Km", preDistance); // 소수점 포매팅

        ArrayList<String> asd = new ArrayList<>();
        asd.add(place_name);
        asd.add(address);
        asd.add(roadAddress);
        asd.add(phone);
        asd.add(String.valueOf(distance));

        CustomToastDialog.show(this,asd);
    }

}