package com.pikazo.rest.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jorgesolis on 7/27/16.
 */
public class QueueParams {
    @SerializedName("userid")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
