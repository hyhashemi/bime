package com.example.bime.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.bime.R;
import com.example.bime.mobilepayment.MobilePaymentActivity;
import com.example.bime.payment.PaymentActivity;
import com.example.bime.report.ReportActivity;
import com.example.bime.track.TrackActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView mPayment;
    private ImageView mReport;
    private ImageView mMobilePayment;
    private ImageView mAboutUs;
    private ImageView mTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initListeners();
    }

    public void initComponents() {
        mReport = findViewById(R.id.reportmainactivity);
        mTrack = findViewById(R.id.trackmainactivity);
        mMobilePayment = findViewById(R.id.mobilepaymentmainactivity);
        mPayment = findViewById(R.id.paymentmainactivity);
        mAboutUs = findViewById(R.id.aboutusmainactivity);
    }

    public void initListeners() {
        mReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        mTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TrackActivity.class);
                startActivity(intent);
            }
        });

        mPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        mMobilePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MobilePaymentActivity.class);
                startActivity(intent);
            }
        });

        mAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
