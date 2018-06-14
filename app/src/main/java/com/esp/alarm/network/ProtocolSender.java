package com.esp.alarm.network;

import com.esp.alarm.dto.SignalStartProtocol;
import com.esp.alarm.dto.UserInfoProtocol;

public class ProtocolSender {

    public static void sendUserInfo(UserInfoProtocol userInfoProtocol) {
        BluetoothService.getInstance().write(userInfoProtocol.toString().getBytes());
    }

    public static void sendSignalStart(SignalStartProtocol signalStartProtocol) {
        BluetoothService.getInstance().write(signalStartProtocol.toString().getBytes());
    }
}
