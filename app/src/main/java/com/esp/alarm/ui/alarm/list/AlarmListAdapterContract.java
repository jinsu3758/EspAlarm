package com.esp.alarm.ui.alarm.list;

import com.esp.alarm.dto.Alarm;

import java.util.List;

public interface AlarmListAdapterContract {
    interface View {
        void refresh();
    }

    interface Model {
        Alarm getItem(int position);

        void setList(List<Alarm> alarmList);
    }
}
