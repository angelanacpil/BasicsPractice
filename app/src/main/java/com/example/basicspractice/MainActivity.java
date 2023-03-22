package com.example.basicspractice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    String player_name;
    Button textBoxBtn;
    Button startBtn;
    private DBHandler dbHandler;

    void moveToIntro() {
        // To Intro
        startBtn.setText("OFW/You landed on Manila blah blah...");
        setContentView(R.layout.activity_intro);
        textBoxBtn = (Button)findViewById(R.id.textBox);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        dbHandler = new DBHandler(MainActivity.this);

        startBtn = (Button)findViewById(R.id.startBtn);
        EditText playerNameEditText = findViewById(R.id.playerNameEditText);

        // If player doesn't exist yet, show text input
        if (dbHandler.getPlayer() == null) {
            playerNameEditText.setVisibility(View.VISIBLE);
        } else {
            player_name = dbHandler.getPlayer().getPlayer_name();
        }

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                dbHandler.clearDatabase();

                moveToIntro();

                textBoxBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        // Player Name to DB
                        dbHandler.addNewPlayer(playerNameEditText.getText().toString(), 1, false, "0", "0", "0", "0");

                        String msg = dbHandler.getPlayer().getPlayer_name() + "";

                        Log.d("Player", msg);

                        // To GameActivity or Stage1
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);

                        // Start new activity and pass player name - only for reference
                        // intent.putExtra("player_name", player_name);
                         startActivity(intent);
                    }
                });
            }
        });

    }
}