package com.example.applicationscalp_care.information;

public class InfoVO {

    // 객체 생성
    private String title;
    private String content;
    private String views;
    private String indate;
    private String img;

    // 생성자 생성
    public InfoVO(){}

    public InfoVO(String title, String content, String views, String indate, String img) {
        this.title = title;
        this.content = content;
        this.views = views;
        this.indate = indate;
        this.img = img;
    }

    public InfoVO(String title, String content, String views, String indate) {
        this.title = title;
        this.content = content;
        this.views = views;
        this.indate = indate;
    }

    // getter 생성
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getViews() { return views; }
    public String getIndate() { return indate; }
    public String getImg() { return img; }

}
