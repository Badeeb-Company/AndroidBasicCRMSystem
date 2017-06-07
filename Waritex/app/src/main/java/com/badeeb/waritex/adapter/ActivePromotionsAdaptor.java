package com.badeeb.waritex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.badeeb.waritex.Model.Promotion;
import com.badeeb.waritex.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ahmed on 6/7/2017.
 */

public class ActivePromotionsAdaptor extends RecyclerView.Adapter<ActivePromotionsAdaptor.MyViewHolder> {

    private Context mContext;
    private List<Promotion> mActivePromotionList;

    public ActivePromotionsAdaptor(Context context, List<Promotion> activePromotionList){
        mContext = context;
        mActivePromotionList = activePromotionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotion_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("InsideOnBindView: "," position "+position);
        Promotion promotion = mActivePromotionList.get(position);
        holder.mTitle.setText(promotion.getTitle());
        holder.mDueDate.setText(promotion.getDueDateFormated());
        Glide.with(mContext).load(Integer.parseInt(promotion.getMainPhoto())).into(holder.mPromotionMainPhoto); // Change it to String after test
    }

    @Override
    public int getItemCount() {
        if(mActivePromotionList == null)
            return 0;
        return mActivePromotionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mDueDate;
        public ImageView mPromotionMainPhoto;

        public MyViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.promotion_card_title);
            mDueDate = (TextView) view.findViewById(R.id.promotion_card_due_date);
            mPromotionMainPhoto = (ImageView) view.findViewById(R.id.promotion_card_photo);
        }
    }
}
