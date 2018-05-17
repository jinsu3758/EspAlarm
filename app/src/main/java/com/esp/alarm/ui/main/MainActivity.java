package com.esp.alarm.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esp.alarm.R;

public class MainActivity extends AppCompatActivity implements MainContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
