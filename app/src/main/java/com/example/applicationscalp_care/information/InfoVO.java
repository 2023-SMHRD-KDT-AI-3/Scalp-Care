package com.example.applicationscalp_care.information;

public class InfoVO {

    // 객체 생성
    private String infotitle;
    private String infosub;
    private String infoview;
    private String infoindate;
    private String infoimg;

    // 생성자 생성
    public InfoVO(){}
    public InfoVO(String infotitle, String infosub, String infoview, String infoindate, String infoimg) {
        this.infotitle = infotitle;
        this.infosub = infosub;
        this.infoview = infoview;
        this.infoindate = infoindate;
        this.infoimg = infoimg;
    }

    public InfoVO(String title, String sub, String view, String indate) {
        this.infotitle = infotitle;
        this.infosub = infosub;
        this.infoview = infoview;
        this.infoindate = infoindate;
    }

    // getter 생성
    public String getInfotitle() { return infotitle; }
    public String getInfosub() { return infosub; }
    public String getInfoview() { return infoview; }
    public String getInfoindate() { return infoindate; }
    public String getInfoimg() { return infoimg; }

}
