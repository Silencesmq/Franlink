package com.example.silence.franlink.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silence.franlink.Application;
import com.example.silence.franlink.FacedetecterActivity;
import com.example.silence.franlink.FingerdetecterActivity;
import com.example.silence.franlink.FirealarmActivity;
import com.example.silence.franlink.Item.Device;
import com.example.silence.franlink.R;
import com.example.silence.franlink.SettingActivity;
import com.example.silence.franlink.TemptActivity;
import com.example.silence.franlink.util.HttpUtil;

import org.litepal.crud.DataSupport;
import org.litepal.exceptions.DataSupportException;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private List<Device> mDeviceList;

    private SharedPreferences pref;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View deviceView;
        TextView deviceName;
        ImageView deviceImage;

        public ViewHolder(View itemView) {
            super(itemView);
            deviceView=itemView;
            deviceName=(TextView)itemView.findViewById(R.id.textView_device);
            deviceImage=(ImageView)itemView.findViewById(R.id.imageView_device);
        }
    }
    public DeviceAdapter(List<Device> deviceList){
        mDeviceList=deviceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.deviceView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view) {
                int position=holder.getAdapterPosition();
                Device device=mDeviceList.get(position);
                int type=device.getType();
                switch (type){
                    case Device.deviceType.Intellock:{
                        pref= PreferenceManager.getDefaultSharedPreferences(view.getContext());
                        final int verify_choose=pref.getInt("verify_choose", SettingActivity.Verify_pass);
                        switch (verify_choose){
                            case SettingActivity.Verify_pass:
                                Toast.makeText(view.getContext(),"choose_pass",Toast.LENGTH_SHORT).show();
                                break;
                            case SettingActivity.Verify_face:{
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Application app = (Application) view.getContext().getApplicationContext();
                                        app.mFaceDB.loadFaces();
                                        ((Activity)view.getContext()).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if( ((Application)view.getContext().getApplicationContext()).mFaceDB.mRegister.isEmpty() ) {
                                                    Toast.makeText(view.getContext(), "没有注册人脸，请先注册！", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Intent it = new Intent(view.getContext(), FacedetecterActivity.class);
                                                    it.putExtra("Camera", 1);
                                                    view.getContext().startActivity(it);

                                                }
                                            }
                                        });
                                    }
                                }).start();
                                break;
                            }
                            case SettingActivity.Verify_fing:
                                Intent it = new Intent(view.getContext(), FingerdetecterActivity.class);
                                view.getContext().startActivity(it);
                                break;
                        }
                        break;
                    }

                    case Device.deviceType.Temhum:{
                        Intent it = new Intent(view.getContext(), TemptActivity.class);
                        view.getContext().startActivity(it);
                        break;
                    }
                    case Device.deviceType.Firesensor:{
                        Intent it = new Intent(view.getContext(),FirealarmActivity.class);
                        view.getContext().startActivity(it);
                        break;
                    }
                }
            }
        });
        holder.deviceView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position=holder.getAdapterPosition();
                showPopMenu(view,position);
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Device device=mDeviceList.get(position);
        holder.deviceName.setText(device.getName());
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    private void removeItem(int pos){
        mDeviceList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void addItem(Device device){
         mDeviceList.add(device);
         notifyDataSetChanged();
    }

    public void update(){
        notifyDataSetChanged();
    }

    private void showPopMenu(final View view, final int pos){
        PopupMenu popupMenu = new PopupMenu(view.getContext(),view, Gravity.END);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Device device=mDeviceList.get(pos);
                RequestBody requestBody=new FormBody.Builder()
                        .add("choose","device")
                        .add("number",String.valueOf(device.getNumber()))
                        .add("owner",Application.owner)
                        .build();
                HttpUtil.OkHttpRequestPost("http://139.196.139.63/delete_device_scene.php",requestBody,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                });
                List<Device> devices=DataSupport.where("number=?",String.valueOf(device.getNumber())).find(Device.class);
                Iterator iter = devices.iterator();
                while(iter.hasNext()){

                    Device o = (Device) iter.next();
                    o.delete();
                    Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();

                }
                removeItem(pos);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(view.getContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }
}
