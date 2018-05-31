package com.esp.alarm.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.esp.alarm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {


    @BindView(R.id.imgView)
    ImageView imgView;
    @BindView(R.id.getCustom)
    Button getCustom;

    @BindView(R.id.inputInfo_next_btn)
    Button inputInfoNextBtn;


    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        getCustom.setOnClickListener(v ->
        {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1: {
                uri = data.getData();
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("outputX", 95);
                intent.putExtra("outputY", 95);
                intent.putExtra("aspectX", 95);
                intent.putExtra("aspectY", 95);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                intent.putExtra("noFaceDetection", true);
                startActivityForResult(intent, 2);
                break;
            }
            case 2: {
                final Bundle extras = data.getExtras();
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PostersCardImage/" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    Glide.with(this).load(photo).apply(RequestOptions.bitmapTransform(new CircleCrop()))
                            .into(imgView);
                   /* imgView.setImageBitmap(photo);
                    imgView.setBackground(getResources().getDrawable(R.drawable.shape));*/
                    break;
                }

            }
            default:
            {
                return;
            }
        }
    }
}
