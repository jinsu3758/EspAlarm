package com.esp.alarm.ui.user;

import android.os.Handler;

public class UserPresenter implements UserContract.Presenter{
    private UserContract.View view;
    private Handler handler;

    public UserPresenter(UserContract.View view, Handler handler)
    {
        this.view = view;
        this.handler = handler;
    }


}
