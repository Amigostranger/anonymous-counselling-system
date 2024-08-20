package com.example.harmony;

import org.json.JSONArray;
import org.json.JSONException;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Post_Request {
    OkHttpClient client = new OkHttpClient();
    HttpUrl.Builder Builder;
    Callback call;
    String url = "https://lamp.ms.wits.ac.za/home/s2570223/projects/";

    public Post_Request(Callback call, String url){
        if(url.equals("https://zenquotes.io/api/today")){
            this.url = url;
        }
        else{
            this.url = this.url + url + ".php";
        }
        this.call = call;
        Builder = HttpUrl.parse(this.url).newBuilder();
    }

    public void add(String where,String what){
        Builder.addQueryParameter(where, what);
    }

    public void post(){
        String url = Builder.build().toString();
        System.out.println(url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(this.call);
    }

    public static JSONArray processJSON(String js) {
        try{
            return new JSONArray(js);
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
