package com.esp.alarm.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class TransferUtils {
    public static String ImageView2Base64(ImageView imageView) {
        Bitmap bitma = imageView2Bitmap(imageView);
        byte[] bytes = bitmap2byte(bitma);
        return byte2Base64(bytes);
    }

    private static String byte2Base64(byte[] profileByte) {
        return Base64.encodeToString(profileByte, Base64.DEFAULT);
    }

    private static byte[] bitmap2byte(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static Bitmap imageView2Bitmap(ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }
}
