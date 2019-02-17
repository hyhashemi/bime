package com.example.bime.login;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bime.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportFragmentManager().beginTransaction().replace(R.id.logincontainer, new LoginFragment()).commit();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("ورود به آیلاس");
    }
}
