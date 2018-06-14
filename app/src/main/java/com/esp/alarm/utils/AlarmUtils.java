package com.esp.alarm.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.esp.alarm.dto.Alarm;
import com.esp.alarm.dto.SignalStartProtocol;
import com.esp.alarm.ui.alarm.receiver.AlarmReceiver;

import java.util.Calendar;

public class AlarmUtils {
    private static AlarmUtils instance = new AlarmUtils();

    public static AlarmUtils getInstance() {
        return instance;
    }

    public void setAlarm(Alarm alarm, Context context) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra(SignalStartProtocol.CAR_SPEED, alarm.getCarSpeed());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), alarm.getHour(), alarm.getMinute(), 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            manager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
