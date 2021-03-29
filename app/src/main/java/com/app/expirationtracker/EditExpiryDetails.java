package com.app.expirationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class EditExpiryDetails extends AppCompatActivity {

    Button btn_edit, btn_change_image;
    EditText et_title, et_date, et_cycle, et_price, et_notes, et_reminder;
    ImageView iv_expiry;
    Uri selectedImageUri;
    byte[] byteImage;
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
        iv_expiry = findViewById(R.id.iv_expiry);
        btn_change_image = findViewById(R.id.btn_change_image);

        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                itemID = bundle.getInt("ID");
                editExpiry(itemID);
            }
        }

        btn_change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
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
        if (objectExpiry.image != null) {
            iv_expiry.setImageBitmap(ImageUtils.getImage(objectExpiry.image));
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get image
                try {
                    byteImage = ImageUtils.getImageBytes(ImageUtils.getImageSizeCompressed(getApplicationContext(), selectedImageUri));
                } catch (IOException ioe) {
                    Log.e("TAG", "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), "Unable to save image into database", Toast.LENGTH_SHORT).show();
                }

                ObjectExpiry objectExpiry = new ObjectExpiry();
                objectExpiry.id = expiryId;
                objectExpiry.title = et_title.getText().toString();
                objectExpiry.date = et_date.getText().toString();
                objectExpiry.cycle = et_cycle.getText().toString();
                objectExpiry.price = et_price.getText().toString();
                objectExpiry.notes = et_notes.getText().toString();
                objectExpiry.reminder = et_reminder.getText().toString();
                objectExpiry.image = byteImage;

                boolean updateSuccessful = tableControllerExpiry.update(objectExpiry);

                if (updateSuccessful) {
                    startActivity(new Intent(getApplicationContext(), ViewExpiryDetails.class));
                    Toast.makeText(EditExpiryDetails.this, "Expiry details was updated.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditExpiryDetails.this, "Unable to update expiry details.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), 100);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    iv_expiry.setImageURI(selectedImageUri);
                }
            }
        }
    }
}