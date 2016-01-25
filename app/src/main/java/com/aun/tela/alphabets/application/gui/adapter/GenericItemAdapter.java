package com.aun.tela.alphabets.application.gui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.aun.tela.alphabets.application.generic.DoubleConsumer;
import com.aun.tela.alphabets.application.generic.QuatroCollector;

import java.util.List;

import io.meengle.util.Value;


/**
 * A generic adapter for adapter views such as a {@link android.widget.ListView} or {@link android.widget.GridView}
 * @param <T> the item type for this adapter
 * @param <ViewHolderImpl> the ViewHolder implementation. Must be a subclass of {@link android.support.v7.widget.RecyclerView.ViewHolder}
 */
public class GenericItemAdapter<T, ViewHolderImpl extends RecyclerView.ViewHolder> extends BaseAdapter {

    List<T> items; //list of items

    /**
     * An instance of {@link DoubleConsumer} which would return the id for a specific item
     */
    DoubleConsumer<Long, T, Integer> idConsumer;

    /**
     * An instance of {@link QuatroCollector} which would collect view items from this adapter for further operations
     */
    QuatroCollector<ViewHolderImpl, T, Integer, Boolean> viewCollector;

    /**
     * An instance of {@link DoubleConsumer} which would consume an item and return the view for it
     */
    DoubleConsumer<ViewHolderImpl, ViewGroup, Integer> viewConsumer;

    /**
     * Set an instance of {@link DoubleConsumer} which would return the id for a specific item
     * @param idConsumer the instance of {@link DoubleConsumer} which would return the id for a specific item
     * @return this adapter instance
     */
    public GenericItemAdapter<T, ViewHolderImpl> setIdConsumer(DoubleConsumer<Long, T, Integer> idConsumer){
        this.idConsumer = idConsumer; return this;
    }

    /**
     * Set An instance of {@link QuatroCollector} which would collect view items from this adapter for further operations
     * @param viewCollector the instance of {@link QuatroCollector} which would collect view items from this adapter for further operations
     * @return this adapter instance
     */
    public GenericItemAdapter<T, ViewHolderImpl> setViewCollector(QuatroCollector<ViewHolderImpl, T, Integer, Boolean> viewCollector){
        this.viewCollector = viewCollector; return this;
    }

    /**
     * Set an instance of {@link DoubleConsumer} which would consume an item and return the view for it
     * @param viewConsumer the instance of {@link DoubleConsumer} which would consume an item and return the view for it
     * @return this adapter instance
     */
    public GenericItemAdapter<T, ViewHolderImpl> setViewConsumer(DoubleConsumer<ViewHolderImpl, ViewGroup, Integer> viewConsumer){
        this.viewConsumer = viewConsumer; return this;
    }

    /**
     * Returns a new instance of {@link GenericItemAdapter}
     * @param <T> the item data type
     * @param <ViewHolderImpl> the viewHolder type. This must extend {@link android.support.v7.widget.RecyclerView.ViewHolder}
     * @return
     */
    public static <T, ViewHolderImpl extends RecyclerView.ViewHolder> GenericItemAdapter<T, ViewHolderImpl> getInstance(){
        return new GenericItemAdapter<>();
    }

    /**
     * Set the items for ths adapter
     * @param items the items to set
     * @return this adapter instance
     */
    public GenericItemAdapter<T, ViewHolderImpl> setItems(List<T> items){this.items = items; refresh(); return this;}

    @Override
    public int getCount() {
        return Value.EMPTY(items) ? 0 : items.size();
    }

    @Override
    public T getItem(int position) {
        return Value.EMPTY(getItems()) ? null : getItems().get(position);
    }

    @Override
    public long getItemId(int position) {
        return idConsumer.consume(getItem(position), position);
    }

    /**
     * Refresh this adapter by telling it that items have changed
     */
    public void refresh(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderImpl impl;
        if(Value.NULL(convertView)){
            impl = viewConsumer.consume(parent, getItemViewType(position));
            convertView = impl.itemView;
            convertView.setTag(impl);
        }else{
            impl = (ViewHolderImpl) convertView.getTag();
        }
        viewCollector.collect(impl, getItem(position), position, Value.Same.INTEGER(position, getCount() - 1));
        return convertView;
    }

    /**
     * @return the items associated with this adapter
     */
    List<T> getItems(){
        return items;
    }
}