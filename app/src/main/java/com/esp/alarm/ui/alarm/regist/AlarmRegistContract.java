package com.esp.alarm.ui.alarm.regist;

import com.esp.alarm.dto.Alarm;

public interface AlarmRegistContract {
    interface View {

        void hideActivity();

        void showToast(String string);
    }

    interface Presenter {

        void registAlarm(Alarm alarm);
    }
}
