package com.example.applicationscalp_care.care;

public class BoardVO {

    // 객체 생성
    private String time;
    private String content;
    private String img;
    private String uid;

    // 생성자 생성
    public BoardVO(){}

    public BoardVO(String time, String content, String img, String uid) {
        this.time = time;
        this.content = content;
        this.img = img;
        this.uid = uid;
    }

    // getter 생성
    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public String getImg() {
        return img;
    }

    public String getUid() {
        return uid;
    }

}
