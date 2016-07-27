package com.pikazo.rest.dto;

import java.util.List;

/**
 * Created by jorgesolis on 7/27/16.
 */
public class StylesPack {
    private String title;
    private String price;
    private List<Style> styles;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }
}
