package com.example.silence.franlink;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;

public class MqttCallbackBus implements MqttCallback {

    //连接中断
    @Override
    public void connectionLost(Throwable cause) {
        Log.e("MqttManager", "cause : " + cause.toString());
        // 可在此方法内写重连的逻辑
    }

    // 消息送达
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.e("MqttManager", "topic : " + topic + "\t MqttMessage : " + message.toString());
        EventBusUtil.sendStickyEvent(new Event<String>(10001, message.toString()));
    }

    //交互完成
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.e("MqttManager", "token : " + token.toString());
    }
}

