package com.example.silence.franlink.Item;

public class Scene {
    private String name;
    private int imageId;
    private int type;
    public Scene(String name,int imageId,int type){
        this.name=name;
        this.imageId=imageId;
        this.type=type;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
    public int getType(){
        return type;
    }
}
