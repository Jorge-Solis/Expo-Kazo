package com.pikazo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by George
 */
public abstract class GenericRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<?> items;

    public GenericRecyclerViewAdapter(List<?> items){
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return onViewHolderCreation(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int itemPosition) {
        onViewHolderBinding(viewHolder, itemPosition);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return onGetItemViewType(position);
    }

    /**
     * Method used to handle the item view type
     * depending on current position
     *
     * @param position current item position
     * @return the number of view type used for view rendering
     */
    public abstract int onGetItemViewType(int position);


    /**
     * Method used to handle the creation of the view holder
     * @param viewGroup The associated view
     * @param viewType The current view type
     * @return The view holder created
     */
    public abstract RecyclerView.ViewHolder onViewHolderCreation(ViewGroup viewGroup, int viewType);


    /**
     * Methods used to handle the binding of the view holder created and render the view, display
     * the data of the current item
     * @param viewHolder The view holder created
     * @param itemPosition The view type
     */
    public abstract void onViewHolderBinding(RecyclerView.ViewHolder viewHolder, int itemPosition);

}
