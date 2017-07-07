package com.badeeb.waritex.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.badeeb.waritex.R;
import com.badeeb.waritex.model.Notification;
import com.badeeb.waritex.view.NotificationViewHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Amr Alghawy on 7/5/2017.
 */

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationViewHolder>  {

    // Class Attributes
    private Context mContext;
    private List<Notification> mNotificationsList;

    // Constructor
    public NotificationsRecyclerViewAdapter(Context mContext, List<Notification> mNotificationsList) {
        this.mContext = mContext;
        this.mNotificationsList = mNotificationsList;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View itemView = layoutInflater.inflate(R.layout.card_notification, parent, false);

        NotificationViewHolder notificationViewHolder = new NotificationViewHolder(itemView);

        return notificationViewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {

        Notification notification = this.mNotificationsList.get(position);
        // Set notification values in holder
        holder.getTitle().setText(notification.getTitle());
        holder.getCreationDate().setText(notification.getCreationDate());
        holder.getDescription().setText(notification.getDescription());
    }

    @Override
    public int getItemCount() {
        if (this.mNotificationsList == null)
            return 0;

        return this.mNotificationsList.size();
    }
}
