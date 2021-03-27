package com.app.expirationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpiryDetails extends AppCompatActivity {

    Button btn_add;
    EditText et_title, et_date, et_cycle, et_price, et_notes, et_reminder;
    String title, date, cycle, price, notes, reminder;
    ObjectExpiry objectExpiry = new ObjectExpiry();
    boolean createSuccessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expiry);

        btn_add = findViewById(R.id.btn_add);
        et_title = findViewById(R.id.et_title);
        et_date = findViewById(R.id.et_date);
        et_cycle = findViewById(R.id.et_cycle);
        et_price = findViewById(R.id.et_price);
        et_notes = findViewById(R.id.et_notes);
        et_reminder = findViewById(R.id.et_reminder);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDetails();
                Context context = v.getRootView().getContext();

                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(date)
                        || TextUtils.isEmpty(cycle) || TextUtils.isEmpty(reminder)) {
                    Toast.makeText(context, "Please enter all the required information!", Toast.LENGTH_SHORT).show();
                } else {
                    createSuccessful = new TableControllerExpiry(context).create(objectExpiry);
                }
                if (createSuccessful) {
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                    Toast.makeText(context, "Expiry details was saved.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void AddDetails() {
        title = et_title.getText().toString().trim();
        date = et_date.getText().toString().trim();
        cycle = et_cycle.getText().toString().trim();
        price = et_price.getText().toString().trim();
        notes = et_notes.getText().toString().trim();
        reminder = et_reminder.getText().toString().trim();

        objectExpiry.title = title;
        objectExpiry.date = date;
        objectExpiry.cycle = cycle;
        objectExpiry.price = price;
        objectExpiry.notes = notes;
        objectExpiry.reminder = reminder;

        if (TextUtils.isEmpty(title)) {
            et_title.setError(getString(R.string.empty_details_prompt, (getString(R.string.title))));
        }
        if (TextUtils.isEmpty(date)) {
            et_date.setError(getString(R.string.empty_details_prompt, (getString(R.string.date))));
        }
        if (TextUtils.isEmpty(cycle)) {
            et_cycle.setError(getString(R.string.empty_details_prompt, (getString(R.string.cycle))));
        }
        if (TextUtils.isEmpty(reminder)) {
            et_reminder.setError(getString(R.string.empty_details_prompt, (getString(R.string.reminder))));
        }
        if (TextUtils.isEmpty(price)) {
            et_price.setError(getString(R.string.empty_details_prompt, (getString(R.string.price))));
        }
        if (TextUtils.isEmpty(notes)) {
            et_notes.setError(getString(R.string.empty_details_prompt, (getString(R.string.notes))));
        }
    }
}