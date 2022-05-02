package com.arthi.traders.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arthi.traders.R;
import com.arthi.traders.model.Payment;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {
    private Context context;
    private List<Payment> py;

    public PaymentAdapter(Context context, List<Payment> py) {
        this.context = context;
        this.py = py;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        holder.payment_status.setText(py.get(position).getStatus());
        holder.payment_type.setText(py.get(position).getTrandsactionmode());
        holder.payment_date.setText(py.get(position).getPaymentdate());
        holder.payment_amount.setText(py.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return py.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        private TextView payment_status,payment_type,payment_date,payment_amount;
        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            payment_status=itemView.findViewById(R.id.payment_status);
            payment_type=itemView.findViewById(R.id.payment_type);
            payment_date=itemView.findViewById(R.id.payment_date);
            payment_amount=itemView.findViewById(R.id.payment_amount);
        }
    }
}
