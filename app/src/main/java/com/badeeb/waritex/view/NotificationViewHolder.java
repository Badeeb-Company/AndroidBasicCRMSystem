package com.badeeb.waritex.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.badeeb.waritex.R;

/**
 * Created by Amr Alghawy on 7/5/2017.
 */

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    // Class Attributes - list of items that will be displayed in view
    private TextView title;
    private TextView creationDate;
    private TextView description;

    // Constructor
    public NotificationViewHolder(View itemView) {
        super(itemView);

        this.title = (TextView) itemView.findViewById(R.id.title);
        this.creationDate = (TextView) itemView.findViewById(R.id.creation_date);
        this.description = (TextView) itemView.findViewById(R.id.description);
    }

    // Setters and Getters
    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(TextView creationDate) {
        this.creationDate = creationDate;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }
}
