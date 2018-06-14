package com.esp.alarm.network;

import com.esp.alarm.dto.UserInfoProtocol;

public class ProtocolSender {

    public static void sendUserInfo(UserInfoProtocol userInfoProtocol) {
        BluetoothService.getInstance().write(userInfoProtocol.toString().getBytes());
    }
}
