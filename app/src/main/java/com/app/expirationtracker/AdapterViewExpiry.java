package com.app.expirationtracker;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterViewExpiry extends RecyclerView.Adapter<AdapterViewExpiry.ViewHolder> {

    Context context;
    private Context activityContext;
    boolean deleteSuccessful;
    private expiryDeleteListener expiryDeleteListeners;
    CardView cv_expiry;
    View view;

    public List<ObjectExpiry> objectExpiryList;

    interface expiryDeleteListener {
        void onShowZeroStateScreen();
    }

    public AdapterViewExpiry(Context context, List<ObjectExpiry> objectExpiryList, expiryDeleteListener expiryDeleteListeners) {
        this.objectExpiryList = objectExpiryList;
        this.activityContext = context;
        this.expiryDeleteListeners = expiryDeleteListeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_expiry_detail, parent, false);
        cv_expiry = view.findViewById(R.id.cv_expiry);
        cv_expiry.setBackgroundResource(R.drawable.et_border);
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
                                    deleteSuccessful = new TableControllerExpiry(context).delete(objectExpiryList.get(position).id);

                                    if (deleteSuccessful) {
                                        Toast.makeText(context, "Expiry detail was deleted.", Toast.LENGTH_SHORT).show();
                                        objectExpiryList.remove(position);
                                        notifyDataSetChanged();
                                        if (objectExpiryList.isEmpty()) {
                                            expiryDeleteListeners.onShowZeroStateScreen();
                                        }
                                    } else {
                                        Toast.makeText(context, "Unable to delete expiry detail.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getRootView().getContext();
                if (objectExpiryList.get(position).image != null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogImageView = inflater.inflate(R.layout.dialog_image_view, null, false);

                    ImageView imageViewDialog = (ImageView) dialogImageView.findViewById(R.id.iv_dialog);
                    imageViewDialog.setImageBitmap(ImageUtils.getImage(objectExpiryList.get(position).image));

                    new AlertDialog.Builder(context)
                            .setView(dialogImageView)
                            .setTitle("Title: " + objectExpiryList.get(position).title)
                            .setPositiveButton("Done",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }

                                    }).show();
                } else {
                    Toast.makeText(context, "No image uploaded for this item.", Toast.LENGTH_SHORT).show();
                }
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
