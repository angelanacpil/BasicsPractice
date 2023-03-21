package com.example.basicspractice;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    // Context
    Context context;

    // Database Test
    String playerN = "UnknownPlayer";

    // Declare game grid and tiles
    GridView gameBoardView;
    GridAdapter gridAdapter;

    int tilesImg[] = {R.drawable.tile1, R.drawable.tile2, R.drawable.tile3, R.drawable.tile4, R.drawable.tile5};

    List<Integer> initialTiles = new ArrayList<Integer>();

    // Initialize Inventory
    private RecyclerView inventoryRecyclerView;
    private ItemListAdapter itemListAdapter;
    private List<Integer> inventory = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    // Score & Timer
    int score = 0;
    TextView timerText;

    private CountDownTimer countDownTimer;

    private void startTimer(long duration) {
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update UI with the remaining time
                long secondsRemaining = millisUntilFinished / 1000;
                timerText.setText("Time remaining: " + secondsRemaining + " seconds");
            }

            @Override
            public void onFinish() {
                // Timer finished, do something here
                timerText.setText("Timer finished. GameOver!");
            }
        };
        countDownTimer.start();
    }


    // Game Logic Conditional
    void checkInventory(List<Integer> stack) {
        // Check inventory size first
        if (inventory.size() >= 7) {
            Log.d("Game", "7! Gameover"); // Working
        }

        // Check stack if there's at least 3 elements in the ArrayList (to avoid indexOutOfBounds err)
        if (stack.size() >= 3) {
            // Check if last inputted num is equal to its right and left indeces
            List<Integer> last_three = stack.subList(stack.size() - 3, stack.size());

            if (last_three.get(0).equals(last_three.get(1)) && last_three.get(1).equals(last_three.get(2))) {
                Log.d("Game", "Banzai! Last three elements have matched.");
                inventory.remove(inventory.size() - 1);
                inventory.remove(inventory.size() - 1);
                inventory.remove(inventory.size() - 1);
                score++;
                checkScore();
            }
        }
    }

    // CHECK SCORE
    void checkScore() {
        switch(score) {
            case 3:
                Log.d("Game", "1st Loc.");
                break;
            case 6:
                Log.d("Game", "2nd Loc.");
                break;
            case 9:
                Log.d("Game", "Sugoi! You win the game.");
                break;
            default:
                // code block
        }

        Log.d("Score", score + "");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // Database Reference
        GameDbHelper dbHelper = new GameDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Define a projection that specifies which columns to retrieve
        String[] projection = {
                GameContract.ScoreEntry._ID,
                GameContract.ScoreEntry.COLUMN_NAME_PLAYER_NAME,
                GameContract.ScoreEntry.COLUMN_NAME_SCORE
        };

        // DB Content Values
        ContentValues values = new ContentValues();
        values.put(GameContract.ScoreEntry.COLUMN_NAME_PLAYER_NAME, "Juan");
        values.put(GameContract.ScoreEntry.COLUMN_NAME_SCORE, 1);

        // Define a selection criteria
        String selection = GameContract.ScoreEntry.COLUMN_NAME_PLAYER_NAME + " = ?";
        String[] selectionArgs = { "Juan" };

        // Query the database
        Cursor cursor = db.query(
                GameContract.ScoreEntry.TABLE_NAME,   // The table to query
                projection,                            // The columns to retrieve
                selection,                             // The selection criteria
                selectionArgs,                         // The selection criteria values
                null,                                  // Group by
                null,                                  // Filter by
                null                                   // Sort order
        );

        // Loop through the cursor to retrieve the data
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(GameContract.ScoreEntry._ID));
                String playerName = cursor.getString(cursor.getColumnIndexOrThrow(GameContract.ScoreEntry.COLUMN_NAME_PLAYER_NAME));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(GameContract.ScoreEntry.COLUMN_NAME_SCORE));
            } while (cursor.moveToNext());
        }

        // Close the cursor
        cursor.close();

        // Timer
        startTimer(24000);

        // Initialize gameBoardView
        gameBoardView = findViewById(R.id.GameBoard);

        // 30 Tiles
        for (int i = 0; i < 36; i++) {
            int randomIndex = new Random().nextInt(tilesImg.length);
            int randomImageId = tilesImg[randomIndex];
            initialTiles.add(randomImageId);
        }

        // GameBoard Adapter
        gridAdapter = new GridAdapter(context, initialTiles);
        gameBoardView.setAdapter(gridAdapter);

        // Inventory
        inventoryRecyclerView = findViewById(R.id.inventoryView);
        itemListAdapter = new ItemListAdapter(inventory);

        linearLayoutManager = new LinearLayoutManager(GameActivity.this, LinearLayoutManager.HORIZONTAL, false);
        inventoryRecyclerView.setLayoutManager(linearLayoutManager);
        inventoryRecyclerView.setAdapter(itemListAdapter);

        // Timer
        timerText = findViewById(R.id.timerView);

        // Event Handlers
        gameBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int resourceId = initialTiles.get(position);

                inventory.add(resourceId);
                checkInventory(inventory);
                itemListAdapter.notifyDataSetChanged();

                // Use a ListIterator to remove the element at the current position - Just to be 100% sure it's correct
                ListIterator<Integer> iterator = initialTiles.listIterator(position);
                iterator.next();
                iterator.remove();
                gridAdapter.notifyDataSetChanged();

                // Database Test
                Log.d("Test", playerN + "");
            }
        });

        // Disable scroll via detecting touch
        gameBoardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getAction() == MotionEvent.ACTION_MOVE;
            }
        });

    }
}
