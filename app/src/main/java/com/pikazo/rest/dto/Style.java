package com.pikazo.rest.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jorgesolis on 7/27/16.
 */
public class Style {
    @SerializedName("styleid")
    private String styleId;
    @SerializedName("styleproxyid")
    private String styleProxyId;
    private String title;

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getStyleProxyId() {
        return styleProxyId;
    }

    public void setStyleProxyId(String styleProxyId) {
        this.styleProxyId = styleProxyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
