package com.esp.alarm.dto;

public class SignalStartProtocol extends BaseProtocol {
    private SignalStart data;

    public SignalStartProtocol() {
        super(Function.SIGNAL_START, "success");
    }

    private static class SignalStart {
        private int cat_speed;
    }
}

