package com.etnet.coresdk.nssCoreService;

import java.util.Map;

public class ApiResponse {
    String _message;
    String _status;
    IPGeoInfo _data;

    public ApiResponse(String message, String status, IPGeoInfo data) {
        this._message = message;
        this._status = status;
        this._data = data;
    }

    public static ApiResponse fromJson(Map<String, Object> data) {
        return new ApiResponse(
                String.valueOf(data.get("message")),
                String.valueOf(data.get("status")),
                IPGeoInfo.fromJson((Map<String, Object>) data.get("data"))
        );
    }

    public String getMessage() {
        return this._message;
    }

    public String getStatus() {
        return this._status;
    }

    public IPGeoInfo getData() {
        return this._data;

    }
}