package com.example.applicationscalp_care;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class HospitalActivity extends AppCompatActivity {

    private static final int ACCESS_FINE_LOCATION = 1000;
    private MapView mapView;
    MapPoint MARKER_POINT2 = MapPoint.mapPointWithGeoCoord(35.159407, 126.883793);
    MapPoint MARKER_POINT3 = MapPoint.mapPointWithGeoCoord(35.128063, 126.792336);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        mapView = new MapView(this);
        setContentView(mapView);

        if (checkLocationService()) {
            // GPS가 켜져있을 경우
            permissionCheck();
        } else {
            // GPS가 꺼져있을 경우
            Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show();
        }
    }

    // 위치 권한 확인
    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
        } else {
            startTracking();
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
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // GPS가 켜져있는지 확인
    private boolean checkLocationService() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    // 현재 위치
    private void startTracking() {
        mapView.setShowCurrentLocationMarker(true);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        // 현재 위치로 지도 중심 이동
        MapPoint currentLocation = mapView.getMapCenterPoint();
        mapView.setMapCenterPoint(currentLocation, true);

        // "현재위치" 마커 추가
        MapPOIItem currentLocationMarker = new MapPOIItem();
        currentLocationMarker.setItemName("현재위치");
        currentLocationMarker.setTag(1); // 태그는 유일한 값으로 설정
        currentLocationMarker.setMapPoint(currentLocation);
        currentLocationMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        currentLocationMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(currentLocationMarker);


        addCustomMarker2();
        addCustomMarker3();
    }

    // 마커 추가
    private void addCustomMarker2() {
        // 병원1 마커 추가
        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName("나용필모피부과의원");
        customMarker.setTag(2); // 다른 태그로 설정
        customMarker.setMapPoint(MARKER_POINT2);
        customMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
        customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(customMarker);
    }
    private void addCustomMarker3() {
        // 병원2 마커 추가
        MapPOIItem customMarker = new MapPOIItem();
        customMarker.setItemName("편안손한방병원");
        customMarker.setTag(3); // 다른 태그로 설정
        customMarker.setMapPoint(MARKER_POINT3);
        customMarker.setMarkerType(MapPOIItem.MarkerType.YellowPin);
        customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mapView.addPOIItem(customMarker);
    }

}