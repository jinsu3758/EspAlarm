package com.esp.alarm.common;

import java.util.UUID;

public class Constant {

    private static Constant instance;
    private final int REQUEST_IMAGE = 1;
    private final int REQUEST_ENABLE_BT = 2;
    private final int REQUEST_CONNECT_DEVICE = 3;
    private  final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    private Constant()
    {}

    public static Constant getInstance()
    {
        if(instance == null)
        {
            instance = new Constant();
        }
        return instance;
    }

    public int getREQUEST_IMAGE() {
        return REQUEST_IMAGE;
    }

    public int getREQUEST_ENABLE_BT() {
        return REQUEST_ENABLE_BT;
    }

    public int getREQUEST_CONNECT_DEVICE() {
        return REQUEST_CONNECT_DEVICE;
    }

    public UUID getMY_UUID() {
        return MY_UUID;
    }
}
