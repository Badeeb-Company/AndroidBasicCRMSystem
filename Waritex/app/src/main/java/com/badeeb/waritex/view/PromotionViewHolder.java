package com.badeeb.waritex.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.badeeb.waritex.R;

/**
 * Created by Amr Alghawy on 6/17/2017.
 */

public class PromotionViewHolder extends RecyclerView.ViewHolder {

    // Class Attributes
    private TextView title;
    private TextView dueDate;
    private ImageView promotionMainPhoto;

    public PromotionViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.promotion_card_title);
        dueDate = (TextView) itemView.findViewById(R.id.promotion_card_due_date);
        promotionMainPhoto = (ImageView) itemView.findViewById(R.id.promotion_card_photo);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getDueDate() {
        return dueDate;
    }

    public void setDueDate(TextView dueDate) {
        this.dueDate = dueDate;
    }

    public ImageView getPromotionMainPhoto() {
        return promotionMainPhoto;
    }

    public void setPromotionMainPhoto(ImageView promotionMainPhoto) {
        this.promotionMainPhoto = promotionMainPhoto;
    }
}
