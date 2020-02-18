package com.example.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView displayTime;
    Button start, pause, reset, lap;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int seconds, minutes, milliseconds;
    ListView listView;
    String[] ListElements = new String[] { };
    List<String> ListElemntsArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayTime = findViewById(R.id.textView);
        start = findViewById(R.id.button_start);
        pause = findViewById(R.id.button_pause);
        reset = findViewById(R.id.button_reset);
        lap = findViewById(R.id.button_lap);
        listView = findViewById(R.id.listview1);
        handler = new Handler();
        ListElemntsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElemntsArrayList);
        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MillisecondTime = 0L;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                milliseconds = 0;
                seconds = 0;
                minutes = 0;
                displayTime.setText("00:00:00");
                ListElemntsArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListElemntsArrayList.add(displayTime.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            seconds = (int)(UpdateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliseconds = (int)(UpdateTime % 1000);
            displayTime.setText("" + minutes +  ":" + String.format("0%2d", seconds)
                + ":" + String.format("0%3d", milliseconds));
            handler.postDelayed(this, 0);
        }
    };
}
