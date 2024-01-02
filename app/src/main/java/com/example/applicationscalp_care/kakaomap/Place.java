package com.example.applicationscalp_care.kakaomap;

public class Place {
    public String place_name;          // 장소명, 업체명
    public String address_name;        // 전체 지번 주소
    public String road_address_name;   // 전체 도로명 주소
    public String phone;               // 전화번호
    public String place_url;           // url
    public float distance;           // url
    public double x;                   // X 좌표값 혹은 longitude
    public double y;                   // Y 좌표값 혹은 latitude

    public Place(String place_name, String address_name, String road_address_name, String phone, String place_url, float distance, double x, double y) {
        this.place_name = place_name;
        this.address_name = address_name;
        this.road_address_name = road_address_name;
        this.phone = phone;
        this.place_url = place_url;
        this.distance = distance;
        this.x = x;
        this.y = y;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getAddress_name() {
        return address_name;
    }

    public String getRoad_address_name() {
        return road_address_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPlace_url() {
        return place_url;
    }

    public float getDistance() {
        return distance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
