package com.example.basicspractice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startBtn = (Button)findViewById(R.id.startBtn);
        startBtn.setText("OFW/You landed on Manila blah blah...");

        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // To Intro
                setContentView(R.layout.activity_intro);
                Button textBoxBtn = (Button)findViewById(R.id.textBox);

                textBoxBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        // To GameActivity or Stage1
                        Intent intent = new Intent(MainActivity.this, GameActivity.class);

                        // Start the new activity
                        startActivity(intent);
                    }
                });
            }
        });

    }
}