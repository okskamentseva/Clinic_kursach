package com.example.clinic_kursach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimetableActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        Button btnP1 = findViewById(R.id.btnP1);
        btnP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to1clinic();
            }
        });

        Button btnP2 = findViewById(R.id.btnP2);
        btnP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnP3 = findViewById(R.id.btnP3);
        btnP3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnP4 = findViewById(R.id.btnP4);
        btnP4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnP5 = findViewById(R.id.btnP5);
        btnP5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnP6 = findViewById(R.id.btnP6);
        btnP6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnP7 = findViewById(R.id.btnP7);
        btnP7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnP8 = findViewById(R.id.btnP8);
        btnP8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnP9 = findViewById(R.id.btnP9);
        btnP9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnP10 = findViewById(R.id.btnP10);
        btnP10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void to1clinic() {
        Intent intent = new Intent(this, Pol1Activity.class);
        startActivity(intent);
        finish();
    }
}
