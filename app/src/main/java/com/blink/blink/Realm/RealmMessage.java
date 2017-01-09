package com.blink.blink.Realm;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Beomseok on 2017. 1. 5..
 */

public class RealmMessage extends RealmObject{
    private RealmUser user;
    private String text;
    private Date time;

    private boolean isRead;
    private boolean isSent;

    public RealmUser getUser() {
        return user;
    }

    public void setUser(RealmUser user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}
