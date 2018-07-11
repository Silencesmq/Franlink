package com.example.silence.franlink.util;

import org.json.JSONObject;

import java.util.Iterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void OkHttpRequestGet(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void OkHttpRequestPost(String address,RequestBody requestBody,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
