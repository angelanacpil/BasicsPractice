package com.example.basicspractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "game.db";

    public GameDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database table
        db.execSQL(GameContract.ScoreEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Upgrade the database if necessary
        db.execSQL(GameContract.ScoreEntry.DELETE_TABLE);
        onCreate(db);
    }
}
