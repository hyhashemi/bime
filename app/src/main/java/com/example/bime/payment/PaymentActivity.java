package com.example.bime.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.bime.R;

public class PaymentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportFragmentManager().beginTransaction().replace(R.id.paymentcontainer, new PaymentFragment()).commit();
    }

}
