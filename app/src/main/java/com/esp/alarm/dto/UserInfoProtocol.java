package com.esp.alarm.dto;

import com.google.gson.Gson;

public class UserInfoProtocol extends BaseProtocol {
    private UserInfo data;

    public UserInfoProtocol(String userName, String encodedProfile) {
        super(Function.USER_INFO, "success");
        data = new UserInfo(userName, encodedProfile.length(), encodedProfile);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    private static class UserInfo {
        private String name;
        private int length;
        private String image;

        public UserInfo(String name, int length, String image) {
            this.name = name;
            this.length = length;
            this.image = image;
        }
    }
}
