package com.esp.alarm.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.esp.alarm.R;
import com.esp.alarm.ui.alarm.list.AlarmListActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private Button mButtonAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        mButtonAlarm.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AlarmListActivity.class);
            startActivity(intent);
        });

    }

    private void initView() {
        mButtonAlarm = (Button) findViewById(R.id.btn_alarm);
    }
}
