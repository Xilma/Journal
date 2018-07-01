package com.example.android.journal.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.journal.model.Constants.DESCRIPTION;
import static com.example.android.journal.model.Constants.TABLE_NAME;
import static com.example.android.journal.model.Constants.TITLE;

public class JournalData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "journal.db";
    private static final int DATABASE_VERSION = 1;

    //Create a helper object for the Journal database
    JournalData(Context con){
        super(con, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + TABLE_NAME + " (" +
        TITLE + " TEXT NOT NULL, " +
        DESCRIPTION + " TEXT NOT NULL);";

        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
