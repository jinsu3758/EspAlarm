package com.esp.alarm.ui.user;

import android.os.Handler;
import android.util.Log;

import com.esp.alarm.common.Constant;
import com.esp.alarm.network.BluetoothService;

public class UserPresenter implements UserContract.Presenter{
    private UserContract.View mView;
    private Handler handler;

    public UserPresenter(UserContract.View view, Handler handler)
    {
        mView = view;
        this.handler = handler;
    }

    @Override
    public void setBluetooth() {
        if (!BluetoothService.getInstance().isConnected()) {
            showBluetootSettingView();
        }
    }

    @Override
    public void showBluetootSettingView() {
        if (BluetoothService.getInstance().isAdapterState()) {
            BluetoothService.getInstance().enableBluetooth();
        } else {
            mView.showToast("블루투스 실패");
            Log.d("USER", "블루투스 실패");
        }
    }

    @Override
    public void setBluetoothActivity(int status) {
        if (status == Constant.getInstance().getREQUEST_ENABLE_BT()) {
            mView.showBluetoothRequestEnable(status);
        } else if (status == Constant.getInstance().getREQUEST_CONNECT_DEVICE()) {
            mView.showBluetoothDeviceList(status);
        }
    }
}
