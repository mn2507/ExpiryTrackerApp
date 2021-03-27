package com.app.expirationtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewExpiryDetails extends AppCompatActivity {

    ListView mListView;
    ArrayAdapter<ObjectExpiry> arrayAdapter;
    RecyclerView recyclerView;
    AdapterViewExpiry adapterViewExpiry;
    List<ObjectExpiry> objectExpiryList;
    SQLiteDatabase mDatabase;
    TableControllerExpiry controllerExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expiry_details);

        objectExpiryList = new ArrayList<>();
//        objectExpiryList.add(new ObjectExpiry());

        objectExpiryList = new TableControllerExpiry(this).objectExpiryList();

        adapterViewExpiry = new AdapterViewExpiry(this,objectExpiryList);
        recyclerView = findViewById(R.id.rv_expiry_details);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterViewExpiry);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

    }
}