package com.example.silence.franlink;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.silence.franlink.bean.Event;
import com.example.silence.franlink.util.EventBusUtil;
import com.example.silence.franlink.util.MqttManager;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    int flagview;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveStickyEvent(Event event) {
        if (!((String) event.getData()).equals("close")) {
            if (((String) event.getData()).equals("fire!fire!fire!")) {
                firenotification();
            }
        }
    }

    private void firenotification() {
        Intent intent = new Intent(this, FirealarmActicity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("紧急！起火了！")
                .setContentText("你家后院起火了！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setAutoCancel(true)                       //点击自动取消
                .setVibrate(new long[]{0, 1000, 1000, 1000}) //震动
                .build();
        manager.notify(1, notification);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
        setContentView(R.layout.activity_main);
        MqttManager.getInstance().creatConnect("tcp://139.196.139.63:1883", "android", "123456", "789", "fire");
        MqttManager.getInstance().subscribe("fire", 1);
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://139.196.139.63/an_temhum.php");
        flagview = 1;
        Button button_1 = (Button) findViewById(R.id.button1);
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MqttManager.getInstance().publish("gpio", 1, "{\"pin\":10,\"value\": 1}");
            }
        });
        Button button_2 = (Button) findViewById(R.id.button2);
        button_2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                Intent intent = new Intent(this,TempandhumActivity.class);
                startActivity(intent);
        }
    }
}
