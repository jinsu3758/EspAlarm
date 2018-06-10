package com.esp.alarm.ui.user;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.esp.alarm.R;
import com.esp.alarm.common.Constant;
import com.esp.alarm.ui.bluetooth.DeviceListActivity;
import com.esp.alarm.utils.BluetoothCallback;
import com.esp.alarm.utils.BluetoothService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity implements UserContract.View, BluetoothCallback {


    @BindView(R.id.user_im_profile)
    ImageView userImProfile;
    @BindView(R.id.user_btn_camera)
    Button userBtnCamera;
    @BindView(R.id.user_edit_name)
    EditText userEditName;
    @BindView(R.id.user_btn_check)
    Button userBtnCheck;
    @BindView(R.id.btbtn)
    Button btbtn;


    private UserContract.Presenter presenter;
    private RequestManager GlideModule;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        GlideModule = Glide.with(this);
        presenter = new UserPresenter(this, handler);
        GlideModule.load(R.drawable.profile).into(userImProfile);
        BluetoothService.getInstance().setHandler(handler);
        BluetoothService.getInstance().setCallback(this);
        initListener();

    }


    private void initListener() {
        userBtnCamera.setOnClickListener(v ->
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, Constant.getInstance().getREQUEST_IMAGE());
        });

        userBtnCheck.setOnClickListener(v ->
        {
            if (BluetoothService.getInstance().getDeviceState()) {
                BluetoothService.getInstance().enableBluetooth();
            } else {
                Log.d("USER", "블루투스 실패");
            }
        });

        btbtn.setOnClickListener(v ->
        {
            BluetoothService.getInstance().write("123 : 123".getBytes());
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.getInstance().getREQUEST_IMAGE()) {
            try {
                GlideModule.load(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()))
                        .apply(new RequestOptions().circleCrop())
                        .into(userImProfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == Constant.getInstance().getREQUEST_ENABLE_BT()) {
            //블루투스 활성화 확인버튼 눌렀을 경우
            if (resultCode == Activity.RESULT_OK) {
                BluetoothService.getInstance().scanDevice();
            }
            //취소 버튼 눌렀을 경우
            else {
                finish();
            }
        } else if (requestCode == Constant.getInstance().getREQUEST_CONNECT_DEVICE()) {
            BluetoothService.getInstance().getDeviceInfo(data);
        }

    }


    @Override
    public void startIntent(int status) {

        if (status == Constant.getInstance().getREQUEST_ENABLE_BT()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, status);
        } else if (status == Constant.getInstance().getREQUEST_CONNECT_DEVICE()) {
            Intent intent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(intent, status);
        }


    }


}
