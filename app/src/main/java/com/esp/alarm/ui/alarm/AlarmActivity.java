package com.esp.alarm.ui.alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esp.alarm.R;

public class AlarmActivity extends AppCompatActivity implements AlarmContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
    }
}
