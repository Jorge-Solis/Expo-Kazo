package com.pikazo.rest.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jsolisl-as on 06/10/2015.
 */
public class User {
    private int id;
    private String email;
    private int weight;
    private float height;
    @SerializedName("display_name") private String displayName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
