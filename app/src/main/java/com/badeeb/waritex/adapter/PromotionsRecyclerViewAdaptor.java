package com.badeeb.waritex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private boolean mIsExpired;

    public PromotionsRecyclerViewAdaptor(Context context, List<Promotion> promotionList, boolean isExpired){
        mContext = context;
        mPromotionList = promotionList;
        mIsExpired = isExpired;
    }

    @Override
    public PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_promotion, parent, false);

        TextView valid_text = (TextView) itemView.findViewById(R.id.valid_date_text);
        if(mIsExpired) {
            valid_text.setText(parent.getResources().getText(R.string.expired_date_text));
        }else{
            valid_text.setText(parent.getResources().getText(R.string.valid_promotion_statment));
        }


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
