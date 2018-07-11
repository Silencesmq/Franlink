package com.example.silence.franlink.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.silence.franlink.Item.Scene;
import com.example.silence.franlink.R;

import java.util.List;

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
        return holder;
    }

    @Override
    public void onBindViewHolder(SceneAdapter.ViewHolder holder, int position) {
        Scene scene=mSceneList.get(position);
        holder.sceneImage.setImageResource(scene.getImageId());
        holder.sceneName.setText(scene.getName());
    }

    @Override
    public int getItemCount() {
        return mSceneList.size();
    }
}
