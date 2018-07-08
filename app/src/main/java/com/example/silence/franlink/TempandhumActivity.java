package com.example.silence.franlink;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.silence.franlink.util.MqttManager;

public class TempandhumActivity extends BaseActivity implements View.OnClickListener {
    int flagview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempandhum);
        flagview = 1;
        Button diagram = (Button) findViewById(R.id.diagram);
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://139.196.139.63/an_temhum.php");
        diagram.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diagram:
                WebView webView = (WebView) findViewById(R.id.web_view);
                Button diagram = (Button) findViewById(R.id.diagram);
                if (flagview == 1) {
                    webView.loadUrl("http://139.196.139.63/an_zhexian.php");
                    flagview = 0;
                    diagram.setText("返回");
                } else {
                    webView.loadUrl("http://139.196.139.63/an_temhum.php");
                    flagview = 1;
                    diagram.setText("查看折线图");
                }
            default:
                break;
        }
    }
}
