package com.example.silence.franlink.Item;

public class Device {
    private String name;
    private int imageId;
    private int type;

    public static final class deviceType {
        public static final int Intellock = 0x111111;
        public static final int Temhum = 0x222222;
        public static final int Firesensor = 0x333333;
        // other more
    }

    public Device(String name,int imageId,int type){
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
