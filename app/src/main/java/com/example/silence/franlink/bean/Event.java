package com.example.silence.franlink.bean;

public class Event<T> {
    private int code;
    private T data;

    public Event(int code) {
        this.code = code;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }
    public static final class EventCode {
        public static final int LoginSucceed = 0x111111;
        public static final int LoginFail = 0x222222;
        public static final int LoadDeviceDatabase = 0x333333;
        public static final int LoadSceneDatabase = 0x444444;
        // other more
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

