package com.pikazo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by George
 */
public abstract class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> menuPages;
    private List<String> tabTitles;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> menuPages) {
        super(fm);
        this.menuPages = menuPages;
    }

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> menuPages, List<String> tabTitles) {
        super(fm);
        this.menuPages = menuPages;
        this.setTabTitles(tabTitles);
    }

    @Override
    public Fragment getItem(int position) {
        return menuPages.get(position);
    }

    @Override
    public int getCount() {
        return menuPages.size();
    }

    /**
     * Used with support tab layouts
     * @param position The position of the tab title
     * @return The title to show in the current page
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return onGetPageTitle(position);
    }

    /**
     * This method has to be implemented to define title logic
     * @param position The tab position
     * @return The title to use
     */
    public abstract CharSequence onGetPageTitle(int position);

    public List<String> getTabTitles() {
        return tabTitles;
    }

    public void setTabTitles(List<String> tabTitles) {
        this.tabTitles = tabTitles;
    }
}
