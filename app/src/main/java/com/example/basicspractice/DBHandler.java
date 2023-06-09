package com.example.basicspractice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {

    // Get reference
    SQLiteDatabase db = this.getReadableDatabase();

    private static final String DATABASE_NAME = "gamedb";
    private static final String TABLE_NAME = "player";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "player_name";
    private static final String CURRENT_STAGE_COL = "current_stage";
    private static final String IS_GAME_COMPLETE_COL = "is_game_complete";

    // STAGES
    private static final String STAGE1_RT_COL = "stage1_rt";
    private static final String STAGE1_BEST_RT_COL = "stage1_best_rt";

    private static final String STAGE2_RT_COL = "stage2_rt";
    private static final String STAGE2_BEST_RT_COL = "stage2_best_rt";

    // Creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + CURRENT_STAGE_COL + " INT,"
                + IS_GAME_COMPLETE_COL + " BOOL,"
                + STAGE1_RT_COL + " TEXT,"
                + STAGE1_BEST_RT_COL + " TEXT,"
                + STAGE2_RT_COL + " TEXT,"
                + STAGE2_BEST_RT_COL + " TEXT);";
        db.execSQL(query);
    }

    // METHODS
    public void addNewPlayer(String player_name, int current_stage, boolean is_game_complete, String stage1_rt, String stage1_best_rt, String stage2_rt, String stage2_best_rt) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, player_name);
        values.put(CURRENT_STAGE_COL, current_stage);
        values.put(IS_GAME_COMPLETE_COL, is_game_complete);
        values.put(STAGE1_RT_COL, stage1_rt);
        values.put(STAGE1_BEST_RT_COL, stage1_best_rt);
        values.put(STAGE2_RT_COL, stage2_rt);
        values.put(STAGE2_BEST_RT_COL, stage2_best_rt);

        db.insert(TABLE_NAME, null, values);
        Log.d("DB", "addNewPlayer: " + player_name);
        db.close();
    }

    public boolean deletePlayer(String playerName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the WHERE clause to specify the player to delete
        String whereClause = NAME_COL + " = ?";
        String[] whereArgs = new String[] { playerName };

        // Call the delete() method to delete the player from the database
        int rowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs);

        // Close the database connection
        db.close();

        // Return true if the player was deleted successfully, false otherwise
        return rowsDeleted > 0;
    }

    public void updatePlayer(String player_name, int current_stage, boolean is_game_complete, String stage1_rt, String stage1_best_rt, String stage2_rt, String stage2_best_rt) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to hold the updated player data
        ContentValues values = new ContentValues();
        values.put(CURRENT_STAGE_COL, current_stage);
        values.put(IS_GAME_COMPLETE_COL, is_game_complete);
        values.put(STAGE1_RT_COL, stage1_rt);
        values.put(STAGE1_BEST_RT_COL, stage1_best_rt);
        values.put(STAGE2_RT_COL, stage2_rt);
        values.put(STAGE2_BEST_RT_COL, stage2_best_rt);

        // Define the WHERE clause to specify the player to update
        String whereClause = NAME_COL + " = ?";
        String[] whereArgs = new String[] { player_name };

        // Call the update() method to update the player data in the database
        db.update(TABLE_NAME, values, whereClause, whereArgs);

        // Close the database connection
        db.close();
    }

    public Player getPlayer() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor with query to read data from database.
        Cursor cursorPlayer = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC LIMIT 1", null);

        Player newPlayer = null;

        // Moving our cursor to first position.
        if (cursorPlayer.moveToFirst()) {
            newPlayer = new Player(
                    cursorPlayer.getString(1),
                    cursorPlayer.getInt(2),
                    cursorPlayer.getInt(3) == 1,
                    cursorPlayer.getString(4),
                    cursorPlayer.getString(5),
                    cursorPlayer.getString(6),
                    cursorPlayer.getString(7)
            );

        }

        // Close & Return
        cursorPlayer.close();
        db.close();
        return newPlayer;
    }

    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Call the delete() method with no WHERE clause to delete all rows
        db.delete(TABLE_NAME, null, null);

        // Close the database connection
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
