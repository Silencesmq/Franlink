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
import com.example.silence.franlink.Item.Scene;
import com.example.silence.franlink.R;
import com.example.silence.franlink.adapter.DeviceAdapter;
import com.example.silence.franlink.adapter.SceneAdapter;

import java.util.ArrayList;
import java.util.List;

public class Myscenefragment extends Fragment{
    private List<Scene> sceneList=new ArrayList<>();
    private  View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initDevice();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2, null);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView_scene);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        SceneAdapter adapter = new SceneAdapter(sceneList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void initDevice(){
        Scene scene1=new Scene("回家",R.drawable.grape_pic,0);
        sceneList.add(scene1);
        Scene scene2=new Scene("起床",R.drawable.mango_pic,0);
        sceneList.add(scene2);
        Scene scene3=new Scene("睡眠",R.drawable.orange_pic,0);
        sceneList.add(scene3);
    }
}
