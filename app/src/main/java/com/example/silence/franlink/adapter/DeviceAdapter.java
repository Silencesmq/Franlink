package com.example.silence.franlink.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silence.franlink.Application;
import com.example.silence.franlink.FacedetecterActivity;
import com.example.silence.franlink.FingerdetecterActivity;
import com.example.silence.franlink.Item.Device;
import com.example.silence.franlink.R;
import com.example.silence.franlink.SettingActivity;

import java.util.List;

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

                    case Device.deviceType.Temhum:
                        break;
                    case Device.deviceType.Firesensor:
                        break;
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Device device=mDeviceList.get(position);
        holder.deviceImage.setImageResource(device.getImageId());
        holder.deviceName.setText(device.getName());
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}
