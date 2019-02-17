package com.example.bime.report;

import android.os.Bundle;

import com.example.bime.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.reportcontainer, new ReportFragment())
                .commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("اعلام خسارت");
    }
}
