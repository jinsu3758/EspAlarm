package com.esp.alarm.ui.user;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.esp.alarm.R;
import com.esp.alarm.common.Constant;
import com.esp.alarm.dto.UserInfoProtocol;
import com.esp.alarm.network.ProtocolSender;
import com.esp.alarm.ui.alarm.list.AlarmListActivity;
import com.esp.alarm.ui.bluetooth.DeviceListActivity;
import com.esp.alarm.network.BluetoothService;
import com.esp.alarm.utils.TransferUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity implements UserContract.View {

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

    private UserContract.Presenter mPresenter;
    private RequestManager GlideModule;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        GlideModule = Glide.with(this);
        mPresenter = new UserPresenter(this, handler);
        GlideModule.load(R.drawable.profile).into(userImProfile);
        BluetoothService.getInstance().setHandler(handler);
        BluetoothService.getInstance().setCallback(status -> {
            mPresenter.setBluetoothActivity(status);
        });
        initListener();

        mPresenter.setBluetooth();
    }

    private void initListener() {
        userBtnCamera.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, Constant.getInstance().getREQUEST_IMAGE());
        });

        userBtnCheck.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AlarmListActivity.class);
            startActivity(intent);
        });

        btbtn.setOnClickListener(v -> {
            String userName = userEditName.getText().toString();
            String encodedProfile = TransferUtils.ImageView2Base64(userImProfile);
            mPresenter.sendUserInfo(userName, encodedProfile);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 이것도 presenter로 뺄 수 있으나 그냥 남김.
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
            } else { //취소 버튼 눌렀을 경우
                finish();
            }
        } else if (requestCode == Constant.getInstance().getREQUEST_CONNECT_DEVICE()) {
            BluetoothService.getInstance().getDeviceInfo(data);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBluetoothRequestEnable(int status) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, status);
    }

    @Override
    public void showBluetoothDeviceList(int status) {
        Intent intent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(intent, status);
    }
}
