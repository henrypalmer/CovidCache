package com.henryrpalmer.lewisu.covidcache;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void begin(View v) {
        Intent launchIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(launchIntent);
    }

}