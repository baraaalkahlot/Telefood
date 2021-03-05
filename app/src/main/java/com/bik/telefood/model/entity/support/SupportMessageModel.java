package com.bik.telefood.model.entity.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportMessageModel {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("msgAttachment")
    @Expose
    private List<Object> msgAttachment = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<Object> getMsgAttachment() {
        return msgAttachment;
    }

    public void setMsgAttachment(List<Object> msgAttachment) {
        this.msgAttachment = msgAttachment;
    }
}
