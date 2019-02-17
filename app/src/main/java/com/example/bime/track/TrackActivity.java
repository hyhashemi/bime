package com.example.bime.track;

import android.os.Bundle;

import com.example.bime.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("پیگیری خسارت");
        getSupportFragmentManager().beginTransaction().replace(R.id.trackcontainer, new TrackFragment()).commit();
    }
}
