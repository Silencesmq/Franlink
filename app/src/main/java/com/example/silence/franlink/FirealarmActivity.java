package com.example.silence.franlink;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class FirealarmActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firealarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_firealarm);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        TextView firealarm=(TextView)findViewById(R.id.textView_firealarm);
        Intent intent=getIntent();
        Boolean isfire=intent.getBooleanExtra("isfire",false);
        if(isfire){
            firealarm.setText("你家后院起火了！赶紧处理！");
        }else{
            firealarm.setText("放心吧，家里一切安全！");
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
