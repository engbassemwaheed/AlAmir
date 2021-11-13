package com.waheed.bassem.alamir.ui.main_activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waheed.bassem.alamir.R;
import com.waheed.bassem.alamir.database.StoreItem;

import java.util.ArrayList;

public class StoreRecyclerAdapter extends RecyclerView.Adapter<StoreViewHolder> {

    private final Context context;
    private final StoreViewInterface storeViewInterface;
    private final ArrayList<StoreItem> storeItems;

    public StoreRecyclerAdapter(Context context, StoreViewInterface storeViewInterface) {
        this.context = context;
        this.storeViewInterface = storeViewInterface;
        this.storeItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.store, parent, false);
        return new StoreViewHolder(context, view, storeViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        StoreItem currentItem = storeItems.get(position);
        holder.displayStoreItem(currentItem);
        holder.setOnClickListeners(currentItem);
    }

    @Override
    public int getItemCount() {
        return storeItems.size();
    }

    public void setData(ArrayList<StoreItem> storeItems) {
        this.storeItems.clear();
        this.storeItems.addAll(storeItems);
        notifyDataSetChanged();
    }

    public boolean isEmpty () {
        return storeItems.size() == 0;
    }
}
