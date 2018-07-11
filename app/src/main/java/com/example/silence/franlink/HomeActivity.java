package com.example.silence.franlink;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.silence.franlink.adapter.TabFragmentPagerAdapter;
import com.example.silence.franlink.bean.Event;
import com.example.silence.franlink.fragment.Mydevicefragment;
import com.example.silence.franlink.fragment.Myscenefragment;
import com.example.silence.franlink.util.EventBusUtil;
import com.example.silence.franlink.util.MqttManager;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private Button tv_item_one;
    private Button tv_item_two;
    private ViewPager myViewPager;
    private List<Fragment> list;
    private TabFragmentPagerAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
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
        Intent intent = new Intent(this, FirealarmActivity.class);
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
        setContentView(R.layout.activity_home);

        InitView();
        InitEvent();

    }

    private void InitView() {
        tv_item_one = (Button) findViewById(R.id.tv_item_one);
        tv_item_two = (Button) findViewById(R.id.tv_item_two);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.profile);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.setting:
                        Intent i=new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

    private void InitEvent() {
        // 设置菜单栏的点击事件
        tv_item_one.setOnClickListener(this);
        tv_item_two.setOnClickListener(this);
        myViewPager.setOnPageChangeListener(new MyPagerChangeListener());

        //把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(new Mydevicefragment());
        list.add(new Myscenefragment());
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面
        tv_item_one.setBackgroundColor(Color.RED);//被选中就为红色
    }
    //Button 点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_item_one:
                myViewPager.setCurrentItem(0);
                tv_item_one.setBackgroundColor(Color.RED);
                tv_item_two.setBackgroundColor(Color.WHITE);
                break;
            case R.id.tv_item_two:
                myViewPager.setCurrentItem(1);
                tv_item_one.setBackgroundColor(Color.WHITE);
                tv_item_two.setBackgroundColor(Color.RED);
                break;

        }
    }

    //Viewpager 监听事件
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    tv_item_one.setBackgroundColor(Color.RED);
                    tv_item_two.setBackgroundColor(Color.WHITE);
                    break;
                case 1:
                    tv_item_one.setBackgroundColor(Color.WHITE);
                    tv_item_two.setBackgroundColor(Color.RED);
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.adddevice:
                Toast.makeText(this, "adddevice", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addscene:
                Toast.makeText(this, "adds", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}
