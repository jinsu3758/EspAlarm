package com.esp.alarm.ui.alarm.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esp.alarm.R;

public class AlarmListActivity extends AppCompatActivity implements AlarmListContract.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
    }
}
