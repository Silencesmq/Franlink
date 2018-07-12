package com.example.silence.franlink;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FirealarmActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firealarm);
        TextView firealarm=(TextView)findViewById(R.id.textView_firealarm);
        Intent intent=getIntent();
        Boolean isfire=intent.getBooleanExtra("isfire",false);
        if(isfire){
            firealarm.setText("你家后院起火了！赶紧处理！");
        }else{
            firealarm.setText("放心吧，家里一切安全！");
        }
    }
}
