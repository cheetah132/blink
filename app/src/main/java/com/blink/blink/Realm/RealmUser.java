package com.blink.blink.Realm;

import io.realm.RealmObject;

/**
 * Created by Beomseok on 2017. 1. 5..
 */

public class RealmUser extends RealmObject{
    private long id;
    private String name;
    private String imageURL;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
