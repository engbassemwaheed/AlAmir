package com.waheed.bassem.alamir.ui.main_activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.waheed.bassem.alamir.R;
import com.waheed.bassem.alamir.database.StoreItem;
import com.waheed.bassem.alamir.utils.Utils;

public class StoreViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final StoreViewInterface storeViewInterface;

    private final ImageView storeImageView;

    private final TextView nameTextView;
    private final TextView addressTextView;
    private final TextView phoneTextView;

    private final ImageButton editImageButton;
    private ImageButton navigationImageButton;
    private ImageButton callImageButton;




    public StoreViewHolder(Context context, @NonNull View itemView, StoreViewInterface storeViewInterface) {
        super(itemView);

        this.context = context;
        this.storeViewInterface = storeViewInterface;

        storeImageView = itemView.findViewById(R.id.item_image_view);
        nameTextView = itemView.findViewById(R.id.item_name_text_view);
        addressTextView = itemView.findViewById(R.id.item_address_text_view);
        phoneTextView = itemView.findViewById(R.id.item_phone_text_view);
        editImageButton = itemView.findViewById(R.id.edit_image_button);
        navigationImageButton = itemView.findViewById(R.id.navigate_image_button);
        callImageButton = itemView.findViewById(R.id.call_image_button);
    }

    public void displayStoreItem (StoreItem storeItem) {
        nameTextView.setText(storeItem.getName());
        phoneTextView.setText(storeItem.getPhone());
        addressTextView.setText(storeItem.getFullAddress());
        Utils.displayImage(context, storeItem.getImageBitmap(), storeImageView, false);
    }

    public void setOnClickListeners (StoreItem storeItem) {
        editImageButton.setOnClickListener(v -> storeViewInterface.editStoreItem(storeItem));

        navigationImageButton.setOnClickListener(v -> storeViewInterface.navigate(storeItem.getNavigationAddress()));

        callImageButton.setOnClickListener(v -> storeViewInterface.callPhone(storeItem.getPhone()));
    }



}
