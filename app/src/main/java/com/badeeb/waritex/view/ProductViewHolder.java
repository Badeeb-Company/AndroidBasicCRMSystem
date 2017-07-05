package com.badeeb.waritex.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badeeb.waritex.R;

/**
 * Created by Amr Alghawy on 6/17/2017.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {

    // Class Attributes - list of items that will be displayed in view
    private ImageView photo;
    private TextView productName;

    // Constructor
    public ProductViewHolder(View itemView) {
        super(itemView);
        photo = (ImageView) itemView.findViewById(R.id.photo);
        productName = (TextView) itemView.findViewById(R.id.product_card_name);
    }

    // Setters and Getters
    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public TextView getProductName() {
        return productName;
    }

    public void setProductName(TextView productName) {
        this.productName = productName;
    }
}
