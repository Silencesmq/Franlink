package com.example.silence.franlink;

/**
 * Created by Axin on 2017/3/23.
 */

public class DateForLine {
    private String time;
    private String value;

    public DateForLine(String time, String value) {
        this.time = time;
        this.value = value;
    }

    public DateForLine() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
