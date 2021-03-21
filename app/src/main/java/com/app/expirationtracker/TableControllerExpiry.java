package com.app.expirationtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TableControllerExpiry extends DatabaseHandler {
    public TableControllerExpiry(Context context) {
        super(context);
    }

    public boolean create(ObjectExpiry objectExpiry) {

        ContentValues values = new ContentValues();

        values.put("title", objectExpiry.title);
        values.put("date", objectExpiry.date);
        values.put("cycle", objectExpiry.cycle);
        values.put("price", objectExpiry.price);
        values.put("notes", objectExpiry.notes);
        values.put("reminder", objectExpiry.reminder);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("expiry", null, values) > 0;
        db.close();

        return createSuccessful;
    }
}
