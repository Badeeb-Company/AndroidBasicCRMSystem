package com.badeeb.waritex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badeeb.waritex.R;
import com.badeeb.waritex.model.Promotion;
import com.badeeb.waritex.view.PromotionViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ahmed on 6/7/2017.
 */

public class PromotionsRecyclerViewAdaptor extends RecyclerView.Adapter<PromotionViewHolder> {

    private Context mContext;
    private List<Promotion> mPromotionList;

    public PromotionsRecyclerViewAdaptor(Context context, List<Promotion> promotionList){
        mContext = context;
        mPromotionList = promotionList;
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotion_card, parent, false);
        return new PromotionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PromotionViewHolder holder, int position) {

        Promotion promotion = mPromotionList.get(position);
        holder.getTitle().setText(promotion.getTitle());
        holder.getDueDate().setText(promotion.getDueDate());
        Glide.with(mContext).load(promotion.getMainPhoto()).into(holder.getPromotionMainPhoto());
    }



    @Override
    public int getItemCount() {
        if(mPromotionList == null)
            return 0;
        return mPromotionList.size();
    }
}
