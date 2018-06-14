package com.esp.alarm.network;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.esp.alarm.common.Constant;
import com.esp.alarm.ui.bluetooth.DeviceListActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothService {
    private static BluetoothService service;
    private BluetoothAdapter adapter;
    private Handler handler;
    private BluetoothCallback callback;
    private int mState;

    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private static final int STATE_NONE = 0;// we're doing nothing
    private static final int STATE_LISTEN = 1;// now listening for incoming connections
    private static final int STATE_CONNECTING = 2;// now initiating an outgoing connection
    private static final int STATE_CONNECTED = 3;// now connected to a remote device

    private BluetoothService() {
        adapter = BluetoothAdapter.getDefaultAdapter();

    }

    public static BluetoothService getInstance() {
        if (service == null) {
            service = new BluetoothService();
        }
        return service;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public boolean isAdapterState() {
        return adapter != null;
    }

    public void enableBluetooth() {
        //블루투스 상태가 on인 경우
        if (adapter.isEnabled()) {
            scanDevice();
        } else {       //블루투스 상태가 off인 경우
            callback.startIntent(Constant.getInstance().getREQUEST_ENABLE_BT());
        }
    }

    //블루투스 디바이스 찾기
    public void scanDevice() {
        callback.startIntent(Constant.getInstance().getREQUEST_CONNECT_DEVICE());
    }

    public void setCallback(BluetoothCallback callback) {
        this.callback = callback;
    }

    public void getDeviceInfo(Intent data) {
        if (data != null) {
            String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
            BluetoothDevice device = adapter.getRemoteDevice(address);
            connect(device);
        }
    }

    //     Bluetooth 상태 get
    public synchronized int getState() {
        return mState;
    }

    public synchronized void start() {
//     Cancel any thread attempting to make a connection
        if (mConnectThread == null) {
        } else {
            mConnectThread.cancel();
            mConnectThread = null;
        }
//     Cancel any thread currently running a connection

        if (mConnectedThread == null) {
        } else {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
    }

    //    ConnectThread 초기화 device의 모든 연결 제거
    public synchronized void connect(BluetoothDevice device) {
//        Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread == null) {
            } else {
                mConnectThread.cancel();
                mConnectThread = null;
            }
        }

//        Cancel any thread currently running a connection
        if (mConnectedThread == null) {
        } else {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
//        Start the thread to connect with the given device
        Log.d("BlUETOOTH", "스레드 시작");
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    //    ConnectedThread 초기화
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        Log.d("BlUETOOTH", "connected()");
//        Cancel the thread that completed the connection
        if (mConnectThread == null) {
        } else {
            mConnectThread.cancel();
            mConnectThread = null;
        }

//        Cancel any thread currently running a connection
        if (mConnectedThread == null) {
        } else {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

//        Start the thread to manage the connection and perform transmissions
        Log.d("BlUETOOTH", "connected 쓰레드 시작");
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
        setState(STATE_CONNECTED);
    }

    //    모든 thread stop
    public synchronized void stop() {
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }
        setState(STATE_NONE);
    }

    //    값을 쓰는 부분(보내는 부분)
    public void write(byte[] out) {
//        Create temporary object
        ConnectedThread r;
//        Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED)
                return;

            r = mConnectedThread;
        }

//        Perform the write unsynchronized
        r.write(out);
    }

    //    연결 실패했을때
    private void connectionFailed() {
        setState(STATE_LISTEN);
    }

    //    연결을 잃었을 때
    private void connectionLost() {
        setState(STATE_LISTEN);
    }

    // Bluetooth 상태 set
    private synchronized void setState(int state) {
        Log.d("BlUETOOTH", "블루투스 상태 : " + state);
        mState = state;
    }

    public boolean isConnected() {
        return mState == STATE_CONNECTED;
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            // 디바이스 정보를 얻어서 BluetoothSocket 생성
            try {
                tmp = device.createRfcommSocketToServiceRecord(Constant.getInstance().getMY_UUID());
            } catch (IOException e) {

            }
            mmSocket = tmp;
        }

        public void run() {
            setName("ConnectThread");
            // 연결을 시도하기 전에는 항상 기기 검색을 중지한다.
            // 기기 검색이 계속되면 연결속도가 느려지기 때문이다.
            adapter.cancelDiscovery();
            Log.d("BlUETOOTH", "연결 스레드 시작");
            // BluetoothSocket 연결 시도

            try {
                // BluetoothSocket 연결 시도에 대한 return 값은 succes 또는 exception이다.
                Log.d("BlUETOOTH", "연결 시도");
                mmSocket.connect();
            } catch (IOException e) {
                connectionFailed();
                // 연결 실패시 불러오는 메소드
                Log.d("BlUETOOTH", "Connect Fail");
                // socket을 닫는다.
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                }
                // 연결중? 혹은 연결 대기상태인 메소드를 호출한다.
                BluetoothService.this.start();
                return;
            }
            // ConnectThread 클래스를 reset한다.
            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }
            // ConnectThread를 시작한다.
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            // BluetoothSocket의 inputstream 과 outputstream을 얻는다.
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.d("BlUETOOTH", "connected 쓰레드 run");
            byte[] buffer = new byte[1024];
            int bytes;
            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    Log.d("BlUETOOTH", "값 읽음");
                    // InputStream으로부터 값을 받는 읽는 부분(값을 받는다)
                    bytes = mmInStream.read(buffer);
                    Log.d("BlUETOOTH", "byte : " + bytes);
                    if (buffer == null) {
                        Log.d("BlUETOOTH", "값이 null");
                    }
                    Log.d("BlUETOOTH", "값 : " + new String(buffer).trim());
                } catch (IOException e) {
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream. * @param buffer The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                Log.d("BlUETOOTH", "값 보냄");
                // 값을 쓰는 부분(값을 보낸다)
                mmOutStream.write(buffer);
            } catch (IOException e) {
                Log.d("BlUETOOTH", "값 보내기 실패 : " + e.getMessage());
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

}
