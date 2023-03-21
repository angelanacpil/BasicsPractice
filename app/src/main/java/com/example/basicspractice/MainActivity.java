package com.example.basicspractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Context
    Context context;

    // Declare game grid and tiles
    private GridView gameBoardView;
    GridAdapter gridAdapter;

    int tilesImg[] = {R.drawable.tile1, R.drawable.tile2, R.drawable.tile3};

    List<Integer> initialTiles = new ArrayList<Integer>();

    // Initialize Inventory
    private RecyclerView inventoryRecyclerView;
    private ItemListAdapter itemListAdapter;
    private List<Integer> inventory = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    // Toast message, debugging purposes
    CharSequence text = "";
    int duration = Toast.LENGTH_SHORT;

    // Game Logic Conditional
    void checkInventory(List<Integer> stack) {
        // Check inventory size first
        if (inventory.size() >= 7) {
            Log.d("Game", "7! Gameover"); // Working
        }

        if (stack.size() >= 3) {
            // Check if last inputted num is equal to its right and left indeces
            List<Integer> last_three = stack.subList(stack.size() - 3, stack.size());

            if (last_three.get(0).equals(last_three.get(1)) && last_three.get(1).equals(last_three.get(2))) {
                Log.d("Game", "Banzai! Last three elements have matched.");
                inventory.remove(inventory.size() - 1);
                inventory.remove(inventory.size() - 1);
                inventory.remove(inventory.size() - 1);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // Initialize gameBoardView
        gameBoardView = findViewById(R.id.GameBoard);

        // 30 Tiles
        for (int i = 0; i < 30; i++) {
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

        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        inventoryRecyclerView.setLayoutManager(linearLayoutManager);
        inventoryRecyclerView.setAdapter(itemListAdapter);

        // Event Handlers
        gameBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int resourceId = initialTiles.get(position);
                text = getResources().getResourceEntryName(resourceId);

                inventory.add(resourceId);
                checkInventory(inventory);
                itemListAdapter.notifyDataSetChanged();

                // Use a ListIterator to remove the element at the current position - Just to be 100% sure it's correct
                ListIterator<Integer> iterator = initialTiles.listIterator(position);
                iterator.next();
                iterator.remove();
                gridAdapter.notifyDataSetChanged();
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