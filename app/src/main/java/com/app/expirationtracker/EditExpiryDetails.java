package com.app.expirationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditExpiryDetails extends AppCompatActivity {

    Button btn_edit;
    EditText et_title, et_date, et_cycle, et_price, et_notes, et_reminder;
    private int itemID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expiry);

        btn_edit = findViewById(R.id.btn_edit);
        et_title = findViewById(R.id.et_title);
        et_date = findViewById(R.id.et_date);
        et_cycle = findViewById(R.id.et_cycle);
        et_price = findViewById(R.id.et_price);
        et_notes = findViewById(R.id.et_notes);
        et_reminder = findViewById(R.id.et_reminder);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                itemID = bundle.getInt("ID");
                editExpiry(itemID);
            }
        }
    }

    public void editExpiry(final int expiryId) {
        final TableControllerExpiry tableControllerExpiry = new TableControllerExpiry(this);
        ObjectExpiry objectExpiry = tableControllerExpiry.readSingleExpiry(expiryId);

        et_title.setText(objectExpiry.title);
        et_date.setText(objectExpiry.date);
        et_cycle.setText(objectExpiry.cycle);
        et_price.setText(objectExpiry.price);
        et_notes.setText(objectExpiry.notes);
        et_reminder.setText(objectExpiry.reminder);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectExpiry objectExpiry = new ObjectExpiry();
                objectExpiry.id = expiryId;
                objectExpiry.title = et_title.getText().toString();
                objectExpiry.date = et_date.getText().toString();
                objectExpiry.cycle = et_cycle.getText().toString();
                objectExpiry.price = et_price.getText().toString();
                objectExpiry.notes = et_notes.getText().toString();
                objectExpiry.reminder = et_reminder.getText().toString();

                boolean updateSuccessful = tableControllerExpiry.update(objectExpiry);

                if (updateSuccessful) {
                    Toast.makeText(EditExpiryDetails.this, "Expiry details was updated.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditExpiryDetails.this, "Unable to update expiry details.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}