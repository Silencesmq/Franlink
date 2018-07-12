package com.example.silence.franlink.Item;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;
import org.litepal.exceptions.DataSupportException;

public class Device extends LitePalSupport{
    private long number;
    private String name;
    private int type;
    private String owner;

    public static final class deviceType {
        public static final int Intellock = 0x111111;
        public static final int Temhum = 0x222222;
        public static final int Firesensor = 0x333333;
        // other more
    }

    public Device(long number, String name,int type,String owner){
        this.number=number;
        this.name=name;
        this.type=type;
        this.owner=owner;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
