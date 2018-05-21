package com.esp.alarm.ui.alarm.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.esp.alarm.R;
import com.esp.alarm.ui.alarm.regist.AlarmRegistActivity;

public class AlarmListActivity extends AppCompatActivity implements AlarmListContract.View {
    private FloatingActionButton alarmAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        initView();

        alarmAddButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AlarmRegistActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        alarmAddButton = (FloatingActionButton) findViewById(R.id.btn_alarm_add);
    }
}
