package com.example.silence.franlink;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AdddeviceActivity extends BaseActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private ImageView Confirmadd;
    private TextView devicename;
    private static final String[] strs = new String[] {
            "智能锁", "温湿度计", "火焰报警器"
    };//定义一个String数组用来显示ListView的内容
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddevice);
        InitView();
    }

    private void InitView() {
        devicename = (TextView)findViewById(R.id.Devicename);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, strs));
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //点击后在标题上显示点击了第几行
                devicename.setText(strs[arg2]);
                setTitle("你点击了第"+arg2+"行");
            }
        });

        Confirmadd = (ImageView) findViewById(R.id.confirmadd);
        Confirmadd.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this,"sb",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmadd:
                Toast.makeText(this,"acnma",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
