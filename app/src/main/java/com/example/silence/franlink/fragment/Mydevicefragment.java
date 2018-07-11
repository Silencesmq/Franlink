package com.example.silence.franlink.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.silence.franlink.Item.Device;
import com.example.silence.franlink.R;
import com.example.silence.franlink.adapter.DeviceAdapter;

import java.util.ArrayList;
import java.util.List;

public class Mydevicefragment  extends Fragment{
    private List<Device> deviceList=new ArrayList<>();
    private  View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initDevice();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1, null);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView_device);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DeviceAdapter adapter = new DeviceAdapter(deviceList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void initDevice(){
        Device device1=new Device("智能锁",R.drawable.apple_pic,Device.deviceType.Intellock);
        deviceList.add(device1);
        Device device2=new Device("温湿度计",R.drawable.banana_pic,Device.deviceType.Temhum);
        deviceList.add(device2);
        Device device3=new Device("火焰报警器",R.drawable.cherry_pic,Device.deviceType.Firesensor);
        deviceList.add(device3);
    }
}
