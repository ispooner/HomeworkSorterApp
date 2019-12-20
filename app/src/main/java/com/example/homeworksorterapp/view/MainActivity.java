package com.example.homeworksorterapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.homeworksorterapp.R;

/*
Create an application in MVP architecture used to keep track of training homework's.
        HomeWork(week, day, subject, boolean complete)
        Application will be using a room DB
        The UI should categorize the homeworks by the day of the week. (d1, d2...d3, wkend)
        - use MVP
        - use ROOM
        - RV
        - Use fragments
*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
