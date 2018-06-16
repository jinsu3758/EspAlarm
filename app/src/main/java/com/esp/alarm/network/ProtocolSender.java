package com.esp.alarm.network;

import android.os.Environment;

import com.esp.alarm.dto.SignalStartProtocol;
import com.esp.alarm.dto.UserInfoProtocol;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class ProtocolSender {

    public static void sendUserInfo(UserInfoProtocol userInfoProtocol) {
        BluetoothService.getInstance().write(userInfoProtocol.toString().getBytes());
    }

    public static void sendSignalStart(SignalStartProtocol signalStartProtocol) {
        BluetoothService.getInstance().write(signalStartProtocol.toString().getBytes());
    }
}
