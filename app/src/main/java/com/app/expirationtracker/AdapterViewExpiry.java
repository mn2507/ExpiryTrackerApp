package com.app.expirationtracker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterViewExpiry extends RecyclerView.Adapter<AdapterViewExpiry.ViewHolder> {

    Context context;
    private Context activityContext;

    public List<ObjectExpiry> objectExpiryList;

    public AdapterViewExpiry(Context context, List<ObjectExpiry> objectExpiryList) {
        this.objectExpiryList = objectExpiryList;
        this.activityContext = context;
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                context = v.getContext();
                final CharSequence[] items = {"Edit", "Delete"};

                new AlertDialog.Builder(context).setTitle("Manage Expiry")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                if (item == 0) {
                                    Intent intent = new Intent(activityContext, EditExpiryDetails.class);
                                    intent.putExtra("ID", objectExpiryList.get(position).id);
                                    activityContext.startActivity(intent);
                                    ((Activity) activityContext).finish();
                                } else if (item == 1) {
                                    boolean deleteSuccessful = new TableControllerExpiry(context).delete(objectExpiryList.get(position).id);

                                    if (deleteSuccessful) {
                                        Toast.makeText(context, "Student record was deleted.", Toast.LENGTH_SHORT).show();
                                        objectExpiryList.remove(position);
                                        notifyItemRemoved(position);
                                    } else {
                                        Toast.makeText(context, "Unable to delete student record.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });
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
