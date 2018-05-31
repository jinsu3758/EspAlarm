package com.esp.alarm.dto;

import android.os.Build;
import android.widget.EditText;
import android.widget.TimePicker;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Alarm extends RealmObject {
    @PrimaryKey
    private int id;
    private int hour;
    private int minute;
    private int carSpeed;
    private boolean isTurnOn;

    public Alarm() {
    }

    public Alarm(int hour, int minute, int carSpeed) {
        this.hour = hour;
        this.minute = minute;
        this.carSpeed = carSpeed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getCarSpeed() {
        return carSpeed;
    }

    public void setTurnOn(boolean turnOn) {
        isTurnOn = turnOn;
    }

    public boolean isTurnOn() {
        return isTurnOn;
    }

    public static Alarm getInstance(TimePicker timePicker, EditText carSpeedText) {
        int alarmHour, alarmMinute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmHour = timePicker.getHour();
            alarmMinute = timePicker.getMinute();
        } else {
            alarmHour = timePicker.getCurrentHour();
            alarmMinute = timePicker.getCurrentMinute();
        }
        int carSpeed;
        try {
            carSpeed = Integer.valueOf(carSpeedText.getText().toString());
        } catch (NumberFormatException exception) {
            return null;
        }
        return new Alarm(alarmHour, alarmMinute, carSpeed);
    }

    public String getTime() {
        boolean isAM = getHour() / 12 != 1;
        StringBuilder stringBuilder = new StringBuilder();
        if (isAM) {
            stringBuilder.append("오전 ");
        } else {
            stringBuilder.append("오후 ");
        }
        stringBuilder.append(getHour() % 12);
        stringBuilder.append(":");
        stringBuilder.append(getMinute());
        return stringBuilder.toString();
    }
}
