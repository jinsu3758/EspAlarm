package com.esp.alarm.ui.alarm.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.esp.alarm.R;
import com.esp.alarm.ui.alarm.regist.AlarmRegistActivity;

public class AlarmListActivity extends AppCompatActivity implements AlarmListContract.View {
    private FloatingActionButton alarmAddButton;
    private AlarmListContract.Presenter mPresenter;

    private RecyclerView alarmRecyclerView;

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadAlarmData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        initView();

        mPresenter = new AlarmListPresenter(this);
        AlarmListAdapter adapter = new AlarmListAdapter(mPresenter);
        mPresenter.setAdapter(adapter);

        alarmAddButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AlarmRegistActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        alarmAddButton = (FloatingActionButton) findViewById(R.id.btn_alarm_add);
        alarmRecyclerView = (RecyclerView) findViewById(R.id.list_alarm);
    }

    @Override
    public void setList(AlarmListAdapter adapter) {
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        alarmRecyclerView.setAdapter(adapter);
    }
}
