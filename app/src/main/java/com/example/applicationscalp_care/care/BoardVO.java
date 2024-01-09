package com.example.applicationscalp_care.care;

public class BoardVO {

    // 객체 생성
    private int uc_num;
    private String indate;
    private String content;
    private String img;
    private String uid;

    private String result;

    // 생성자 생성
    public BoardVO(){}

    public BoardVO(int uc_num, String indate, String content, String img, String uid) {
        this.uc_num = uc_num;
        this.indate = indate;
        this.content = content;
        this.img = img;
        this.uid = uid;
    }

    public BoardVO(int uc_num,String indate, String content, String result) {
        this.uc_num = uc_num;
        this.indate = indate;
        this.content = content;
        this.result = result;
    }


    // getter 생성

    public long getUc_num() {
        return uc_num;
    }

    public String getIndate() {
        return indate;
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

    public String getResult() {
        return result;
    }
}
