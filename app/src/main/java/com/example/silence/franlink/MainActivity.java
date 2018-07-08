package com.example.silence.franlink;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends BaseActivity {
    int flagview;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void receiveStickyEvent(Event event) {
        if (!((String)event.getData()).equals("close"))
            Toast.makeText(this,(String)event.getData(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusUtil.register(this);
        setContentView(R.layout.activity_main);
        MqttManager.getInstance().creatConnect("tcp://139.196.139.63:1883", "android", "123456", "789","fire");
        MqttManager.getInstance().subscribe("fire",1);
        WebView webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://139.196.139.63/an_temhum.php");
        flagview=1;
        Button button_1=(Button) findViewById(R.id.button1);
        button_1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MqttManager.getInstance().publish("gpio",1,"{\"pin\":10,\"value\": 1}");
            }
        });
        Button button_2=(Button) findViewById(R.id.button2);
        button_2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                WebView webView=(WebView)findViewById(R.id.web_view);
                Button button_2=(Button) findViewById(R.id.button2);
                if(flagview==1){
                    webView.loadUrl("http://139.196.139.63/an_zhexian.php");
                    flagview=0;
                    button_2.setText("返回");
                }
                else{
                    webView.loadUrl("http://139.196.139.63/an_temhum.php");
                    flagview=1;
                    button_2.setText("查看折线图");
                }

            }
        });
    }
}
