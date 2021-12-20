package com.example.wechatmessage;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    static final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");


    public static void postMsg(String msg) {
        try {
//        JSONObject object = new JSONObject();
//        object.put("msg",msg);
//         RequestBody body = RequestBody.create(JSON,object.toString());
            RequestBody requestBody = new FormBody.Builder()
                    .add("msg", msg)
                    .build();
            Request request = new Request.Builder()
                    .url("http://192.168.0.41:8088/wechat")
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println("返回的数据：" + response.body().string());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
