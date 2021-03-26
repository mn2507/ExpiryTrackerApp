package com.app.expirationtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterViewExpiry extends RecyclerView.Adapter<AdapterViewExpiry.ViewHolder> {

    public List<ObjectExpiry> objectExpiryList;

    public AdapterViewExpiry(List<ObjectExpiry> objectExpiryList) {
        this.objectExpiryList = objectExpiryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expiry_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(objectExpiryList.get(position).title);
        holder.tv_date.setText(objectExpiryList.get(position).date);
        holder.tv_cycle.setText(objectExpiryList.get(position).cycle);
        holder.tv_price.setText(objectExpiryList.get(position).price);
        holder.tv_notes.setText(objectExpiryList.get(position).notes);
        holder.tv_reminder.setText(objectExpiryList.get(position).reminder);
    }


    @Override
    public int getItemCount() {
        return objectExpiryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        TextView tv_title, tv_date, tv_cycle, tv_price, tv_notes, tv_reminder;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            tv_title = mView.findViewById(R.id.tv_title);
            tv_date = mView.findViewById(R.id.tv_date);
            tv_cycle = mView.findViewById(R.id.tv_cycle);
            tv_price = mView.findViewById(R.id.tv_price);
            tv_notes = mView.findViewById(R.id.tv_notes);
            tv_reminder = mView.findViewById(R.id.tv_reminder);
        }
    }
}
