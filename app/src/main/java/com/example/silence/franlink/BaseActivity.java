package com.example.silence.franlink;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.silence.franlink.bean.Event;
import com.example.silence.franlink.util.ActivityCollector;
import com.example.silence.franlink.util.EventBusUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseActivity extends AppCompatActivity {
    protected boolean isRegisterEventBus() {
        return false;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /* 接收到分发到事件
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /*接受到分发的粘性事件
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }
}
