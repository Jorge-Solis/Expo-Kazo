package com.pikazo.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by jorgesolis on 7/28/16.
 */
public abstract class GenericAdapter extends BaseAdapter {

    private List<?> items;
    private Context context;
    private int totalItemTypes;

    public GenericAdapter(Context context, List<?> items, int totalItemTypes){
        this.context = context;
        this.setItems(items);
        this.totalItemTypes = totalItemTypes;
    }

    @Override
    public int getCount() {
        return getItems().size();
    }

    @Override
    public Object getItem(int position) {
        return getItems().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return onGetItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return this.totalItemTypes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return onRenderingView(position, convertView, parent);
    }

    @Override
    public boolean isEnabled(int position) {
        return onEnable(position);
    }

    /**
     * Method used to enable/disable specific items in the data collection
     * @param position
     * @return
     */
    public abstract boolean onEnable(int position);

    /**
     * Method used to overwrite the getView method with a custom view
     *
     * @param position current item position
     * @param convertView item view
     * @param parent container
     *
     * @return the view rendered
     */
    public abstract View onRenderingView(int position, View convertView, ViewGroup parent);

    /**
     * Method used to handle the item view type
     * depending on current position
     *
     * @param position current item position
     * @return the number of view type used for view rendering
     */
    public abstract int onGetItemViewType(int position);

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }
}
