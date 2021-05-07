package com.etnet.coresdk.coreNetwork;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Ajax {

    OkHttpClient client = new OkHttpClient();

    public Response run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

//        Request request = new Request.Builder()
//                .header("Authorization", "your token")
//                .url("https://api.github.com/users/vogella")
//                .build();

        try (Response response = client.newCall(request).execute()) {
            return response;
        }
    }

    public Response sendRequest(String url, Object postData) {
        final Logger log = Logger.getLogger("Ajax");
        Ajax getFromUrlgetFromUrl = new Ajax();
        Response result = null;
        log.info("send http request: " + url);

        try {
            result = getFromUrlgetFromUrl.run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
//        Future<http.Response> request = http.get(url);
//        return request;
    }
}
