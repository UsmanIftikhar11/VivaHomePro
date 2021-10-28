package com.viva.vivahomepro;

public class ServiceComplete {
    private String tname;
    private String ul_name;
    private String ul_uid;
    private String lead_id;
    private String u_pic;
    private String date;

    public ServiceComplete(String tname, String ul_name, String ul_uid, String lead_id, String u_pic, String date) {
        this.tname = tname;
        this.ul_name = ul_name;
        this.ul_uid = ul_uid;
        this.lead_id = lead_id;
        this.u_pic = u_pic;
        this.date = date;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getUl_name() {
        return ul_name;
    }

    public void setUl_name(String ul_name) {
        this.ul_name = ul_name;
    }

    public String getUl_uid() {
        return ul_uid;
    }

    public void setUl_uid(String ul_uid) {
        this.ul_uid = ul_uid;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getU_pic() {
        return u_pic;
    }

    public void setU_pic(String u_pic) {
        this.u_pic = u_pic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
