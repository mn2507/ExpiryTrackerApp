package com.app.expirationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    Button btn_add_expiry_details, btn_view_expiry_details;
    TextView tv_record_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        tv_record_count = findViewById(R.id.tv_record_count);
        btn_add_expiry_details = findViewById(R.id.btn_add_expiry_details);
        btn_view_expiry_details = findViewById(R.id.btn_view_expiry_details);

        btn_add_expiry_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, AddExpiryDetails.class));
            }
        });

        btn_view_expiry_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, ViewExpiryDetails.class));
            }
        });

        countRecords();
    }

    public void countRecords() {
        int recordCount = new TableControllerExpiry(this).countExpiryList();
        tv_record_count.setText(getString(R.string.no_of_records_found, recordCount));
    }
}