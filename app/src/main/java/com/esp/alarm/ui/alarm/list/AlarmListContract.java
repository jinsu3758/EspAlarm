package com.esp.alarm.ui.alarm.list;

public interface AlarmListContract {
    interface View {

        void setList(AlarmListAdapter adapter);
    }

    interface Presenter {

        void setAdapter(AlarmListAdapter adapter);

        void loadAlarmData();
    }
}
