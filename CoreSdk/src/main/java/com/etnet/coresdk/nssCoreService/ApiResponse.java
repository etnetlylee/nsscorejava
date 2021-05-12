package com.etnet.coresdk.nssCoreService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ApiResponse {
    String message;
    String status;
    IPGeoInfo data;

    public ApiResponse(String _message, String _status, IPGeoInfo _data) {
        this.message = _message;
        this.status = _status;
        this.data = _data;
    }

    public static ApiResponse fromJson(Map<String, Object> data) {
        return new ApiResponse(
                String.valueOf(data.get("message")),
                String.valueOf(data.get("status")),
                IPGeoInfo.fromJson((Map<String, Object>) data.get("data"))
        );
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public IPGeoInfo getData() {
        return this.data;

    }
}