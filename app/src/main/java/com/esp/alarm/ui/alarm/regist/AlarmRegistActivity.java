package com.esp.alarm.ui.alarm.regist;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.esp.alarm.R;
import com.esp.alarm.dto.Alarm;

public class AlarmRegistActivity extends AppCompatActivity implements AlarmRegistContract.View {
    private AlarmRegistContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_regist);
        mPresenter = new AlarmRegistPresenter(this);

        Button registButton = (Button) findViewById(R.id.btn_alarm_regist);
        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        EditText carSpeedText = (EditText) findViewById(R.id.et_car_speed);

        registButton.setOnClickListener(v -> {
            Alarm alarm = Alarm.getInstance(timePicker, carSpeedText);
            mPresenter.registAlarm(alarm);
        });
    }

    @Override
    public void hideActivity() {
        finish();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
