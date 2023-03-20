package com.example.basicspractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Context
    Context context;

    // Declare game grid and tiles
    private GridView gameBoardView;
    GridAdapter gridAdapter;
    int tiles[] = {R.drawable.tile1, R.drawable.tile2, R.drawable.tile3, R.drawable.tile1, R.drawable.tile2, R.drawable.tile2, R.drawable.tile3, R.drawable.tile1, R.drawable.tile2, R.drawable.tile1};

    // Initialize Inventory
    private RecyclerView inventoryRecyclerView;
    private ItemListAdapter itemListAdapter;
    private List<Integer> inventory = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;

    // Toast message, debugging purposes
    CharSequence text = "";
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // Initialize gameBoardView
        gameBoardView = findViewById(R.id.GameBoard);

        // GameBoard Adapter
        gridAdapter = new GridAdapter(context, tiles);
        gameBoardView.setAdapter(gridAdapter);

        // Inventory
        inventoryRecyclerView = findViewById(R.id.inventoryView);
        itemListAdapter = new ItemListAdapter(inventory);

        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        inventoryRecyclerView.setLayoutManager(linearLayoutManager);
        inventoryRecyclerView.setAdapter(itemListAdapter);

        // Event Handler
        gameBoardView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int resourceId = tiles[position];
                text = getResources().getResourceEntryName(resourceId);

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                inventory.add(resourceId);
                itemListAdapter.notifyDataSetChanged();
            }
        });
    }
}