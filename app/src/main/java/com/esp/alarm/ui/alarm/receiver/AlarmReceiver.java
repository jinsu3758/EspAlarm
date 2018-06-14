package com.esp.alarm.ui.alarm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;

import com.esp.alarm.dto.SignalStartProtocol;
import com.esp.alarm.network.ProtocolSender;

import java.io.File;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test", "reciever test");
        // 음악실행

        int carSpeed = intent.getIntExtra(SignalStartProtocol.CAR_SPEED, 0);
        SignalStartProtocol signalStartProtocol = new SignalStartProtocol(carSpeed);
        // bluetooth 전송
        ProtocolSender.sendSignalStart(signalStartProtocol);
    }
}
