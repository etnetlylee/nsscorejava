package com.etnet.coresdk.coreNetwork;

import com.etnet.coresdk.nssCoreService.ApiResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Ajax {

    OkHttpClient client = new OkHttpClient();

    public String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public ApiResponse sendRequest(String url) {
        final Logger log = Logger.getLogger("Ajax");
        Ajax getFromUrlgetFromUrl = new Ajax();
        ApiResponse result = null;
        log.info("send http request: " + url);
        String getResponse = "";
        try {
            getResponse = getFromUrlgetFromUrl.doGetRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(getResponse);
        result = gson.fromJson(object, ApiResponse.class);
        return result;
    }

    public ApiResponse postRequest(String url, String json) {
        final Logger log = Logger.getLogger("Ajax");
        Ajax getFromUrlgetFromUrl = new Ajax();
        ApiResponse result = null;
        log.info("send http request: " + url);
        String getResponse = "";
        try {
            getResponse = getFromUrlgetFromUrl.doPostRequest(url, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(getResponse);
        result = gson.fromJson(object, ApiResponse.class);
        return result;
    }


    public String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
