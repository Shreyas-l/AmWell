package com.example.sleeptracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Boolean is_sleep = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sharedPreferences = getSharedPreferences("com.example.sleeptracker", MODE_PRIVATE);

        final Button recordSleepButton = findViewById(R.id.recordSleepButton);
        final TextView trackFlagTextView = findViewById(R.id.trackFlagTextView);

        recordSleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long sleepTime = 0;
                if (is_sleep) {
                    Calendar calendar = Calendar.getInstance();
                    long wakeUpTimeMillis = calendar.getTimeInMillis();
                    Log.i("SLEEP END", String.valueOf(wakeUpTimeMillis));

                    if (sharedPreferences.getLong("SleepTime", 0) != 0) {
                        sleepTime = sharedPreferences.getLong("SleepTime", 0);
                        sharedPreferences.edit().clear().apply();
                    }

                    long sleepDuration = wakeUpTimeMillis - sleepTime;
                    Log.i("SLEEPON sp", String.valueOf(sleepTime));

                    Log.i("DURATION", String.valueOf(sleepDuration));

                    final long second = (sleepDuration / 1000) % 60;
                    final long minute = (sleepDuration / (1000 * 60)) % 60;
                    final long hour = (sleepDuration / (1000 * 60 * 60)) % 60;

                    Log.i("HOUR", String.valueOf(hour));
                    Log.i("MIN", String.valueOf(minute));
                    Log.i("SEC", String.valueOf(second));

                    Toast.makeText(MainActivity.this, hour+":"+minute+":"+second, Toast.LENGTH_SHORT).show();
                    is_sleep = false;
                    trackFlagTextView.setVisibility(View.INVISIBLE);
                    recordSleepButton.setText("Start Recording");

                } else {
                    Calendar calendar = Calendar.getInstance();
                    long sleepTimeMillis = calendar.getTimeInMillis();
                    sharedPreferences.edit().putLong("SleepTime", sleepTimeMillis).apply();
                    Log.i("SLEEP ON", String.valueOf(sleepTimeMillis));
                    is_sleep = true;
                    trackFlagTextView.setVisibility(View.VISIBLE);
                    recordSleepButton.setText("Stop Recording");
                }
            }
        });
    }
}