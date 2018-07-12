package com.example.silence.franlink.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.silence.franlink.Application;
import com.example.silence.franlink.Item.Device;
import com.example.silence.franlink.R;
import com.example.silence.franlink.adapter.DeviceAdapter;
import com.example.silence.franlink.bean.Event;
import com.example.silence.franlink.util.EventBusUtil;
import com.example.silence.franlink.util.HttpUtil;

import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class Mydevicefragment  extends Fragment{
    private  RecyclerView recyclerView;
    private DeviceAdapter adapter;
    private List<Device> deviceList=new ArrayList<>();
    private  View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDevice();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, null);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView_device);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DeviceAdapter(deviceList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void initDevice(){
        List<Device> devices= DataSupport.findAll(Device.class);
        for (Device device: devices){
            deviceList.add(device);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:{
                if(RESULT_OK==RESULT_OK){
                    String name=data.getStringExtra("name");
                    int type=data.getIntExtra("type",0);
                    long time=new Date().getTime();
                    Device device=new Device(time,name,type, Application.owner);
                    adapter.addItem(device);
                    device.save();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("choose","device")
                            .add("number",String.valueOf(time))
                            .add("name",name)
                            .add("type",String.valueOf(type))
                            .add("owner",Application.owner)
                            .build();
                    HttpUtil.OkHttpRequestPost("http://139.196.139.63/add_device_scene.php",requestBody,new okhttp3.Callback(){
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                        }
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }
                    });
                }
                break;
            }
        }
    }
    public void noticeupdate(){
        initDevice();
        adapter.update();
    }
}
