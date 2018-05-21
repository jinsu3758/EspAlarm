package com.esp.alarm.ui.alarm.regist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esp.alarm.R;

public class AlarmRegistActivity extends AppCompatActivity implements AlarmRegistContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_regist);
    }
}
