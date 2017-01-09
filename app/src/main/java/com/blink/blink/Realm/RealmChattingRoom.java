package com.blink.blink.Realm;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Beomseok on 2017. 1. 5..
 */

public class RealmChattingRoom extends RealmObject{
    long matchingId;
    private RealmUser matchedUser;
    private RealmList<RealmMessage> messages;

    public RealmUser getMatchedUser() {
        return matchedUser;
    }

    public void setMatchedUser(RealmUser matchedUser) {
        this.matchedUser = matchedUser;
    }

    public RealmList<RealmMessage> getMessages() {
        return messages;
    }

    public void setMessages(RealmList<RealmMessage> messages) {
        this.messages = messages;
    }

    public RealmMessage getLastMessage() {
        if(messages.isEmpty())
            return null;
        else
            return messages.last();
    }

    public int getUnCheckedCount(){
        int count = 0;
        if(!messages.isEmpty())
            for (RealmMessage m : messages) {
                if(m.getUser().getId() == matchedUser.getId())
                    if(m.isRead() == false) count++;
            }
        return count;
    }

    public long getMatchingId() {
        return matchingId;
    }

    public void setMatchingId(long matchingId) {
        this.matchingId = matchingId;
    }
}
