package com.pikazo.rest.dto;

import java.util.List;

/**
 * Created by jorgesolis on 7/27/16.
 */
public class StylesInformation {
    private List<StylesPack> stylePacks;
    private int[] arrangement;

    public List<StylesPack> getStylePacks() {
        return stylePacks;
    }

    public void setStylePacks(List<StylesPack> stylePacks) {
        this.stylePacks = stylePacks;
    }

    public int[] getArrangement() {
        return arrangement;
    }

    public void setArrangement(int[] arrangement) {
        this.arrangement = arrangement;
    }
}
