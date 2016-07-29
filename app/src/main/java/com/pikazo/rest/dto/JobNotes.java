package com.pikazo.rest.dto;

import io.realm.RealmObject;

/**
 * Created by jorgesolis on 7/28/16.
 */
public class JobNotes extends RealmObject {
    private String placeholder;// TODO: This is just a placeholder, ignore for now...

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
