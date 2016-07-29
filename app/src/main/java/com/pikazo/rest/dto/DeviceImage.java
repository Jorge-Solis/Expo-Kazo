package com.pikazo.rest.dto;

/**
 * Created by jorgesolis on 7/28/16.
 */
public class DeviceImage {
    private String Id;
    private String path;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
