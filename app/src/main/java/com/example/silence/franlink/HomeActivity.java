package com.example.silence.franlink;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.silence.franlink.Item.Device;
import com.example.silence.franlink.Item.Scene;
import com.example.silence.franlink.adapter.TabFragmentPagerAdapter;
import com.example.silence.franlink.bean.Event;
import com.example.silence.franlink.fragment.Mydevicefragment;
import com.example.silence.franlink.fragment.Myscenefragment;
import com.example.silence.franlink.jsong.AnimationUtil;
import com.example.silence.franlink.jsong.FloatingDraftButton;
import com.example.silence.franlink.util.EventBusUtil;
import com.example.silence.franlink.util.HttpUtil;
import com.example.silence.franlink.util.MqttManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.internal.Util;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.floatingActionButton)
    FloatingDraftButton floatingActionButton;
    @BindView(R.id.floatingActionButton_1)
    FloatingActionButton floatingActionButton1;
    @BindView(R.id.floatingActionButton_2)
    FloatingActionButton floatingActionButton2;
    @BindView(R.id.floatingActionButton_3)
    FloatingActionButton floatingActionButton3;
    @BindView(R.id.floatingActionButton_4)
    FloatingActionButton floatingActionButton4;
    @BindView(R.id.floatingActionButton_5)
    FloatingActionButton floatingActionButton5;

    private SharedPreferences pref;

    private Button tv_item_one;
    private Button tv_item_two;
    private ViewPager myViewPager;
    private List<Fragment> list;
    private Mydevicefragment myDevicefragment;
    private Myscenefragment myScenefragment;
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

    @Override
    protected void receiveEvent(Event event) {
        if (event.getCode()==Event.EventCode.LoadDeviceDatabase){
            myDevicefragment.noticeupdate();
        }else if(event.getCode()==Event.EventCode.LoadSceneDatabase){
            myScenefragment.noticeupdate();
        }
    }

    private void firenotification() {
        Intent intent = new Intent(this, FirealarmActivity.class);
        intent.putExtra("isfire",true);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
    protected void onStart() {
        super.onStart();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        Boolean floatball_choose=pref.getBoolean("floatball",false);
        View floatball=(View)findViewById(R.id.floatBall);
        if(floatball_choose){
            floatball.setVisibility(View.VISIBLE);
        }else {
            floatball.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MqttManager.getInstance().creatConnect("tcp://139.196.139.63:1883", "android", "123456", "789","fire");
        MqttManager.getInstance().subscribe("fire",1);
        initLitePalDatabase();
        InitView();
        InitEvent();
        InitFloatBall();
    }

    private void InitFloatBall(){
        ButterKnife.bind(this);
        floatingActionButton.registerButton(floatingActionButton1);
        floatingActionButton.registerButton(floatingActionButton2);
        floatingActionButton.registerButton(floatingActionButton3);
        floatingActionButton.registerButton(floatingActionButton4);
        floatingActionButton.registerButton(floatingActionButton5);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AnimationUtil.slideButtons(HomeActivity.this,floatingActionButton);
            }
        });
        floatingActionButton1.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(HomeActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "1111111", Toast.LENGTH_SHORT).show(); }});
        floatingActionButton2.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(HomeActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "2222222", Toast.LENGTH_SHORT).show(); }});
        floatingActionButton3.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(HomeActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "3333333", Toast.LENGTH_SHORT).show(); }});
        floatingActionButton4.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(HomeActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "4444444", Toast.LENGTH_SHORT).show(); }});
        floatingActionButton5.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) {  AnimationUtil.slideButtons(HomeActivity.this,floatingActionButton); Toast.makeText(getApplicationContext(), "5555555", Toast.LENGTH_SHORT).show(); }});
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
        myDevicefragment=new Mydevicefragment();
        myScenefragment=new Myscenefragment();
        list.add(myDevicefragment);
        list.add(myScenefragment);
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
            case R.id.adddevice:{
                Intent i=new Intent(HomeActivity.this, AdddeviceActivity.class);
                startActivityForResult(i,1);
                break;
            }
            case R.id.addscene:{
                Intent i=new Intent(HomeActivity.this, AddsceneActivity.class);
                startActivityForResult(i,2);
                break;
            }
            default:
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:{
                if (resultCode == RESULT_OK) {
                    myDevicefragment.onActivityResult(requestCode, resultCode, data);
                }
                break;
            }
            case 2:{
                if (resultCode == RESULT_OK) {
                    myScenefragment.onActivityResult(requestCode, resultCode, data);
                }
                break;
            }
        }
    }
    private void initLitePalDatabase(){
        LitePal.getDatabase();//create database
        DataSupport.deleteAll(Device.class);
        DataSupport.deleteAll(Scene.class);
        HttpUtil.OkHttpRequestGet("http://139.196.139.63/select_device.php?owner="+Application.owner,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsondata=response.body().string();
                    Gson gson=new Gson();
                    List<Device> deviceList=gson.fromJson(jsondata,new TypeToken<List<Device>>(){}.getType());
                    for(Device device :deviceList){
                        device.save();
                    }
                    EventBusUtil.sendEvent(new Event<String>(Event.EventCode.LoadDeviceDatabase, "LoadDatabase Success"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
            }

        });
        HttpUtil.OkHttpRequestGet("http://139.196.139.63/select_scene.php?owner="+Application.owner,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsondata=response.body().string();
                    Gson gson=new Gson();
                    List<Scene> sceneList=gson.fromJson(jsondata,new TypeToken<List<Scene>>(){}.getType());
                    for(Scene scene :sceneList){
                        scene.save();
                    }
                    EventBusUtil.sendEvent(new Event<String>(Event.EventCode.LoadSceneDatabase, "LoadDatabase Success"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
            }

        });
    }
}
