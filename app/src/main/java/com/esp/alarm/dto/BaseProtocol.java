package com.esp.alarm.dto;

public class BaseProtocol {
    enum Function {
        USER_INFO,
        SIGNAL_START,
        USER_AUTH
    }

    public BaseProtocol(Function messageId, String result) {
        this.message_id = messageId;
        this.result = result;
    }

    private Function message_id;
    private String result;
}
