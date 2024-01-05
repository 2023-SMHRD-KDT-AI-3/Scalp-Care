package com.example.applicationscalp_care.review;

public class reviewVO {

    // 객체 생성
    private int re_num;
    private String content;
    private String indate;
    private String re_uid_uid;

    private String re_uid_name;

    // 생성자 생성

    public reviewVO(int re_num, String content, String indate, String re_uid_name) {
        this.re_num = re_num;
        this.content = content;
        this.indate = indate;
        this.re_uid_name = re_uid_name;
    }


    // getter 생성

    public int getRe_num() {
        return re_num;
    }

    public String getContent() {
        return content;
    }

    public String getIndate() {
        return indate;
    }


    public String getRe_uid_uid() {
        return re_uid_uid;
    }

    public String getRe_uid_name() {
        return re_uid_name;
    }
}
