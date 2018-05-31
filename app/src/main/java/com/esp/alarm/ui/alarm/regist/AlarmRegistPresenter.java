package com.esp.alarm.ui.alarm.regist;

import com.esp.alarm.dto.Alarm;
import com.esp.alarm.utils.RealmUtils;

import io.realm.Realm;

class AlarmRegistPresenter implements AlarmRegistContract.Presenter {
    private AlarmRegistContract.View mView;

    AlarmRegistPresenter(AlarmRegistContract.View view) {
        mView = view;
    }

    @Override
    public void registAlarm(Alarm alarm) {
        // DB저장
        if (alarm == null) {
            mView.showToast("알람을 등록하는데 문제가 발생하였습니다.");
        } else {
            Realm.getDefaultInstance().executeTransaction(realm -> {
                int nextPrimaryKey = RealmUtils.getNextPrimaryKey(realm, Alarm.class);
                alarm.setId(nextPrimaryKey);
                alarm.setTurnOn(true);
                realm.copyToRealmOrUpdate(alarm);
            });
        }
        mView.hideActivity();
        // 화면 닫기
    }
}
