package com.pikazo.rest.dto;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by jorgesolis on 7/28/16.
 */
public class JobParameters extends RealmObject {

    @SerializedName("contentid")
    private String contentId;
    @SerializedName("initid")
    private String initId;
    @SerializedName("styleid")
    private String styleId;
    @SerializedName("styleproxyid")
    private String styleProxyId;
    private String mode;
    private int size;
    @SerializedName("parentjobid")
    private String parentJobId;
    @SerializedName("iospushtoken")
    private String iosPushToken;
    @SerializedName("clientversion")
    private int clientVersion;
    @SerializedName("clientbuild")
    private String clientBuild;
    @SerializedName("clientplatform")
    private String clientPlatform;
    @SerializedName("outputid")
    private String outputId;


    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getInitId() {
        return initId;
    }

    public void setInitId(String initId) {
        this.initId = initId;
    }

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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getParentJobId() {
        return parentJobId;
    }

    public void setParentJobId(String parentJobId) {
        this.parentJobId = parentJobId;
    }

    public String getIosPushToken() {
        return iosPushToken;
    }

    public void setIosPushToken(String iosPushToken) {
        this.iosPushToken = iosPushToken;
    }

    public int getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(int clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getClientBuild() {
        return clientBuild;
    }

    public void setClientBuild(String clientBuild) {
        this.clientBuild = clientBuild;
    }

    public String getClientPlatform() {
        return clientPlatform;
    }

    public void setClientPlatform(String clientPlatform) {
        this.clientPlatform = clientPlatform;
    }

    public String getOutputId() {
        return outputId;
    }

    public void setOutputId(String outputId) {
        this.outputId = outputId;
    }
}
