package com.esp.alarm.ui.user;

public interface UserContract {
    interface View {

        void showToast(String message);

        void showBluetoothRequestEnable(int status);

        void showBluetoothDeviceList(int status);
    }

    interface Presenter {
        void setBluetooth();

        void showBluetootSettingView();

        void setBluetoothActivity(int status);
    }
}
