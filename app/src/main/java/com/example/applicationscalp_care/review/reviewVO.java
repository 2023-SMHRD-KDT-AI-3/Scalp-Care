package com.example.applicationscalp_care.review;

public class reviewVO {

    // 객체 생성
    private int re_num;
    private String content;
    private String indate;
    private String re_ac_num_ac_num;
    private String re_uid_uid;

    // 생성자 생성
    public reviewVO(int re_num, String content, String indate, String re_ac_num_ac_num, String re_uid_uid) {
        this.re_num = re_num;
        this.content = content;
        this.indate = indate;
        this.re_ac_num_ac_num = re_ac_num_ac_num;
        this.re_uid_uid = re_uid_uid;
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

    public String getRe_ac_num_ac_num() {
        return re_ac_num_ac_num;
    }

    public String getRe_uid_uid() {
        return re_uid_uid;
    }
}
