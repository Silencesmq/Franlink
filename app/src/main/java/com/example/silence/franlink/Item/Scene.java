package com.example.silence.franlink.Item;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

public class Scene extends LitePalSupport {
    private long number;
    private String name;
    private String owner;
    private int type;

    public Scene(long number,String name,int type,String owner){
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
