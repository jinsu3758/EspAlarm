package com.esp.alarm.dto;

public class UserAuthProtocol extends BaseProtocol {
    private UserAuth data;

    public UserAuthProtocol() {
        super(Function.USER_AUTH, "success");
    }

    private static class UserAuth {
        private String result;
    }
}
