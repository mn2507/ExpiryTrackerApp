package com.app.expirationtracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewExpiryDetails extends AppCompatActivity  {

    ArrayAdapter<ObjectExpiry> arrayAdapter;
    RecyclerView recyclerView;
    AdapterViewExpiry adapterViewExpiry;
    List<ObjectExpiry> objectExpiryList;
    TextView tv_zero_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expiry_details);
        tv_zero_state = findViewById(R.id.tv_zero_state);

        objectExpiryList = new ArrayList<>();
        objectExpiryList = new TableControllerExpiry(this).viewExpiryList();

        adapterViewExpiry = new AdapterViewExpiry(this, objectExpiryList, new AdapterViewExpiry.expiryDeleteListener() {
            @Override
            public void onShowZeroStateScreen() {
                objectExpiryList.clear();
                setLayoutVisibility();
            }
        });
        recyclerView = findViewById(R.id.rv_expiry_details);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterViewExpiry);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        setLayoutVisibility();
    }

    private void setLayoutVisibility() {
        if (objectExpiryList.isEmpty()) {
            tv_zero_state.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tv_zero_state.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}