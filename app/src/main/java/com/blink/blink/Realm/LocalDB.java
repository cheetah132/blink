package com.blink.blink.Realm;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Beomseok on 2017. 1. 5..
 */

public class LocalDB extends RealmObject {
    private RealmUser user;
    private RealmList<RealmUser> matchedUsers;
    private RealmList<RealmChattingRoom> chattingRooms;

    public RealmUser getUser() {
        return user;
    }

    public void setUser(RealmUser user) {
        this.user = user;
    }

    public RealmList<RealmUser> getMatchedUsers() {
        return matchedUsers;
    }

    public void setMatchedUsers(RealmList<RealmUser> matchedUsers) {
        this.matchedUsers = matchedUsers;
    }

    public RealmList<RealmChattingRoom> getChattingRooms() {
        return chattingRooms;
    }

    public void setChattingRooms(RealmList<RealmChattingRoom> chattingRooms) {
        this.chattingRooms = chattingRooms;
    }
}
