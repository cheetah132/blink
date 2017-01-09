package com.blink.blink;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by user on 2016. 12. 20..
 */

public class FacebookFriend {
    List<Friend> data;
    Summary summary;

    public List<Friend> getData() {
        return data;
    }

    public Summary getSummary() {
        return summary;
    }
}
