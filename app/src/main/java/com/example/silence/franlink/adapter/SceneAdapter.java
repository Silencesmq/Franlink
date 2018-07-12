package com.example.silence.franlink.adapter;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silence.franlink.Application;
import com.example.silence.franlink.Item.Device;
import com.example.silence.franlink.Item.Scene;
import com.example.silence.franlink.R;
import com.example.silence.franlink.util.HttpUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SceneAdapter extends RecyclerView.Adapter<SceneAdapter.ViewHolder>  {
    private List<Scene> mSceneList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View sceneView;
        TextView sceneName;
        ImageView sceneImage;

        public ViewHolder(View itemView) {
            super(itemView);
            sceneView=itemView;
            sceneName=(TextView)itemView.findViewById(R.id.textView_scene);
            sceneImage=(ImageView)itemView.findViewById(R.id.imageView_scene);
        }
    }
    public SceneAdapter(List<Scene> sceneList){
        mSceneList=sceneList;
    }

    @Override
    public SceneAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scene,parent,false);
        final SceneAdapter.ViewHolder holder=new SceneAdapter.ViewHolder(view);
        holder.sceneView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Scene scene=mSceneList.get(position);
                int type=scene.getType();
                Toast.makeText(view.getContext(),"click_scene",Toast.LENGTH_SHORT).show();
            }
        });
        holder.sceneView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position=holder.getAdapterPosition();
                Scene scene=mSceneList.get(position);
                showPopMenu(view,position);
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SceneAdapter.ViewHolder holder, int position) {
        Scene scene=mSceneList.get(position);
        holder.sceneName.setText(scene.getName());
    }

    @Override
    public int getItemCount() {
        return mSceneList.size();
    }

    private void removeItem(int pos){
        mSceneList.remove(pos);
        notifyItemRemoved(pos);
    }

    private void showPopMenu(final View view, final int pos){
        PopupMenu popupMenu = new PopupMenu(view.getContext(),view, Gravity.END);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Scene scene=mSceneList.get(pos);
                RequestBody requestBody=new FormBody.Builder()
                        .add("choose","scene")
                        .add("number",String.valueOf(scene.getNumber()))
                        .add("owner", Application.owner)
                        .build();
                HttpUtil.OkHttpRequestPost("http://139.196.139.63/delete_device_scene.php",requestBody,new okhttp3.Callback(){
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                });
                List<Scene> devices=DataSupport.where("number=?",String.valueOf(scene.getNumber())).find(Scene.class);
                Iterator iter = devices.iterator();
                while(iter.hasNext()){

                    Scene o = (Scene) iter.next();
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

    public void addItem(Scene scene){
        mSceneList.add(scene);
        notifyDataSetChanged();
    }
    public void update(){
        notifyDataSetChanged();
    }
}
