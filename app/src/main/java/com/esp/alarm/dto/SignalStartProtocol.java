package com.esp.alarm.dto;

import com.google.gson.Gson;

public class SignalStartProtocol extends BaseProtocol {
    public static final String CAR_SPEED = "CAR_SPEED";
    private SignalStart data;

    public SignalStartProtocol(int carSpeed) {
        super(Function.SIGNAL_START, "success");
        data = new SignalStart(carSpeed);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private static class SignalStart {
        private int cat_speed;

        public SignalStart(int cat_speed) {
            this.cat_speed = cat_speed;
        }
    }
}

