package com.example.basicspractice;

import android.provider.BaseColumns;

public final class GameContract {
    private GameContract() {}

    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";
        public static final String COLUMN_NAME_PLAYER_NAME = "player_name";
        public static final String COLUMN_NAME_SCORE = "score";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_PLAYER_NAME + " TEXT," +
                COLUMN_NAME_SCORE + " INTEGER)";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
