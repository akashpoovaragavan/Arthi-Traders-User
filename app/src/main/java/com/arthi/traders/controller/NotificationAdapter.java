package com.arthi.traders.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.model.Notification;

import java.sql.Connection;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private Context context;
    private List<Notification> nt;

    public NotificationAdapter(Context context, List<Notification> nt) {
        this.context = context;
        this.nt = nt;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.sms_title.setText(nt.get(position).getTitle());
        holder.sms_content.setText(nt.get(position).getMessage());
        holder.sms_date.setText(nt.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return nt.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView sms_title,sms_content,sms_date;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            sms_title=itemView.findViewById(R.id.sms_title);
            sms_content=itemView.findViewById(R.id.sms_content);
            sms_date=itemView.findViewById(R.id.sms_date);
        }
    }
}
