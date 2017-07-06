package com.badeeb.waritex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badeeb.waritex.R;
import com.badeeb.waritex.model.Product;
import com.badeeb.waritex.view.ProductViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Amr Alghawy on 6/17/2017.
 */

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    // Class Attributes
    private Context mContext;
    private List<Product> mProductsList;

    // Constructor
    public ProductsRecyclerViewAdapter(Context mContext, List<Product> mProductsList) {
        this.mContext = mContext;
        this.mProductsList = mProductsList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View itemView = layoutInflater.inflate(R.layout.card_product, parent, false);

        ProductViewHolder productViewHolder = new ProductViewHolder(itemView);

        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

        Product product = this.mProductsList.get(position);
        // Set product values in holder
        Glide.with(mContext).load(product.getPhotoUrl()).into(holder.getPhoto());
        holder.getProductName().setText(product.getName());
    }

    @Override
    public int getItemCount() {

        if (this.mProductsList == null)
            return 0;

        return this.mProductsList.size();
    }
}
