package com.example.bime.trackreport;

import android.os.Bundle;

import com.example.bime.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class TrackReportActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackreport);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("نتایج پیگیری");
        String data = getIntent().getStringExtra("Data");
        getSupportFragmentManager().beginTransaction().replace(R.id.trackreportcontainer, TrackReport.newInstance(data)).commit();
    }
}
