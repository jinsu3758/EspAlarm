package com.esp.alarm.ui.alarm.regist;

import com.esp.alarm.dto.Alarm;

public interface AlarmRegistContract {
    interface View {

        void hideActivity();

        void showToast(String string);

        void setAlarm(Alarm alarm);
    }

    interface Presenter {

        void registAlarm(Alarm alarm);
    }
}
