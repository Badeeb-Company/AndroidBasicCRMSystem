package com.badeeb.waritex.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.badeeb.waritex.R;

/**
 * Created by Amr Alghawy on 6/17/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    // Class Attributes - list of items that will be displayed in view
    public ImageView photo;

    // Constructor
    public ProductViewHolder(View itemView) {
        super(itemView);
        photo = (ImageView) itemView.findViewById(R.id.photo);
    }

    // Setters and Getters

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }
}
