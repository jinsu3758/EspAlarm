package com.esp.alarm.ui.alarm.list;

import com.esp.alarm.dto.Alarm;

import java.util.List;

import io.realm.Realm;

class AlarmListPresenter implements AlarmListContract.Presenter {
    private AlarmListContract.View mView;
    private AlarmListAdapterContract.View mAdapterView;
    private AlarmListAdapterContract.Model mAdapterModel;

    AlarmListPresenter(AlarmListContract.View view) {
        mView = view;
    }

    @Override
    public void setAdapter(AlarmListAdapter adapter) {
        mAdapterView = adapter;
        mAdapterModel = adapter;
        mView.setList(adapter);
    }

    @Override
    public void loadAlarmData() {
        List<Alarm> alarmList = Realm.getDefaultInstance().where(Alarm.class).findAll().sort("id");
        mAdapterModel.setList(alarmList);
        mAdapterView.refresh();
    }
}
