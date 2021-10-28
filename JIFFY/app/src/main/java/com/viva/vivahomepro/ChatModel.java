package com.viva.vivahomepro;

public class ChatModel {

    private String mid;
    private String leadid;
    private String pid;
    private String uid;
    private String message;
    private String mdate;
    private String whosend;
    private String attachment;

    public ChatModel(String mid, String leadid, String pid, String uid, String message, String mdate, String whosend, String attachment) {
        this.mid = mid;
        this.leadid = leadid;
        this.pid = pid;
        this.uid = uid;
        this.message = message;
        this.mdate = mdate;
        this.whosend = whosend;
        this.attachment = attachment;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getChatModel() {
        return leadid;
    }

    public void setChatModel(String leadid) {
        this.leadid = leadid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getmDate() {
        return mdate;
    }

    public void setmDate(String mdate) {
        this.mdate = mdate;
    }

    public String getWhosend() {
        return whosend;
    }

    public void setWhosend(String whosend) {
        this.whosend = whosend;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
