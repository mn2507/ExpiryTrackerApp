package com.app.expirationtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
        values.put("image", objectExpiry.image);

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("expiry", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    public int countExpiryList() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM expiry";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();
        return recordCount;
    }

    public List<ObjectExpiry> objectExpiryList() {
        List<ObjectExpiry> objectExpiries = new ArrayList<>();
        String sql = "SELECT * FROM expiry ORDER BY id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String cycle = cursor.getString(cursor.getColumnIndex("cycle"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String notes = cursor.getString(cursor.getColumnIndex("notes"));
                String reminder = cursor.getString(cursor.getColumnIndex("reminder"));
                byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));

                ObjectExpiry objectExpiry = new ObjectExpiry();
                objectExpiry.id = id;
                objectExpiry.title = title;
                objectExpiry.date = date;
                objectExpiry.cycle = cycle;
                objectExpiry.price = price;
                objectExpiry.notes = notes;
                objectExpiry.reminder = reminder;
                objectExpiry.image = image;

                objectExpiries.add(objectExpiry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return objectExpiries;
    }

    public ObjectExpiry readSingleExpiry(int expiryId) {
        ObjectExpiry objectExpiry = null;
        String sql = "SELECT * FROM expiry WHERE id = " + expiryId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String cycle = cursor.getString(cursor.getColumnIndex("cycle"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String notes = cursor.getString(cursor.getColumnIndex("notes"));
            String reminder = cursor.getString(cursor.getColumnIndex("reminder"));

            objectExpiry = new ObjectExpiry();
            objectExpiry.id = id;
            objectExpiry.title = title;
            objectExpiry.date = date;
            objectExpiry.cycle = cycle;
            objectExpiry.price = price;
            objectExpiry.notes = notes;
            objectExpiry.reminder = reminder;

        }

        cursor.close();
        db.close();

        return objectExpiry;

    }

    public boolean update(ObjectExpiry objectExpiry) {

        ContentValues values = new ContentValues();

        values.put("title", objectExpiry.title);
        values.put("date", objectExpiry.date);
        values.put("cycle", objectExpiry.cycle);
        values.put("price", objectExpiry.price);
        values.put("notes", objectExpiry.notes);
        values.put("reminder", objectExpiry.reminder);

        String where = "id = ?";

        String[] whereArgs = {Integer.toString(objectExpiry.id)};

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("expiry", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("expiry", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
}
