package com.example.a7_1p.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.UiContext;

import com.example.a7_1p.model.Item;
import com.example.a7_1p.model.User;
import com.example.a7_1p.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.USER_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + Util.USER_NAME + " TEXT," + Util.PASSWORD + " TEXT)" ;

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        String CREATE_ITEM_TABLE = "CREATE TABLE " + Util.ITEM_TABLE_NAME + "(" + Util.ITEM_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + Util.ITEM_DESCRIPTION + " TEXT,"
                + Util.ITEM_NAME + " TEXT, " + Util.ITEM_PHONE + " INTEGER," + Util.ITEM_LOST
                + " INTEGER, " + Util.ITEM_DATE + " TEXT, " + Util.ITEM_LOCATION + " TEXT)" ;

        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS '" + Util.TABLE_NAME + "'";
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

        String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS'" + Util.ITEM_TABLE_NAME + "'";
        sqLiteDatabase.execSQL(DROP_ITEM_TABLE);

        onCreate(sqLiteDatabase);

    }

    public long insertUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Util.USER_NAME, user.getUsername());
        contentValues.put(Util.PASSWORD, user.getPassword());

        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }

    public boolean fetchUser (String userName, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.USER_ID}, Util.USER_NAME + " =? and "
                + Util.PASSWORD + "=?", new String[] {userName, password}, null, null, null);
        int numberOfrows = cursor.getCount();
        db.close();
        if (numberOfrows > 0)
            return true;
        return false;
    }


    // ITEMS Related Methods

    public long insertItem(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Util.ITEM_DESCRIPTION, item.getDescription());
        contentValues.put(Util.ITEM_PHONE, item.getPhone());
        contentValues.put(Util.ITEM_LOCATION, item.getLocation());
        contentValues.put(Util.ITEM_DATE, item.getDate().toString());
        contentValues.put(Util.ITEM_LOST, item.isLost());
        contentValues.put(Util.ITEM_NAME, item.getName());

        long newRowId = db.insert(Util.ITEM_TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }


    @SuppressLint("Range")
    public List<Item> fetchItem ()
    {
        List<Item> myList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Util.ITEM_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        //Cursor cursor = db.query(Util.ITEM_TABLE_NAME, null,null, null, null, null, null);
        int numberOfrows = cursor.getCount();
        db.close();
        cursor.moveToFirst();
        for (int i=0; i < numberOfrows; i++)
        {
            Item item = new Item();
            item.setItemId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.ITEM_ID))));
            item.setDescription(cursor.getString(cursor.getColumnIndex(Util.ITEM_DESCRIPTION)));
            item.setDate(cursor.getString(cursor.getColumnIndex(Util.ITEM_DATE)));
            item.setLocation(cursor.getString(cursor.getColumnIndex(Util.ITEM_LOCATION)));
            item.setLost(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.ITEM_LOST))));
            item.setName(cursor.getString(cursor.getColumnIndex(Util.ITEM_NAME)));
            item.setPhone(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Util.ITEM_PHONE))));

            myList.add(item);
            cursor.moveToNext();
        }
        return myList;
    }
    public void deleteItem (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + Util.ITEM_TABLE_NAME + " WHERE " + Util.ITEM_ID + " = "+ id;
        //Cursor cursor = db.rawQuery(query, null);
        db.execSQL(query);
        db.close();
    }

}
