package com.etnet.coresdk.nssCoreService;


import java.util.Map;

public class IPGeoInfo {
    boolean _is_cn;
    boolean _is_loopback;
    String _type;
    boolean _is_local;
    String _ip;

    public IPGeoInfo(
            boolean is_cn, boolean is_local, String type, boolean is_loopback, String ip) {
        this._is_cn = is_cn;
        this._is_local = is_local;
        this._type = type;
        this._is_loopback = is_loopback;
        this._ip = ip;
    }

    public static IPGeoInfo fromJson(Map<String, Object> data) {
        return new IPGeoInfo((Boolean) data.get("is_cn"), (Boolean) data.get("is_local"), String.valueOf(data.get(
                "is_local")),
                (Boolean) data.get("is_loopback"), String.valueOf(data.get("ip")));
    }

    public boolean getIsCn() {
        return this._is_cn;
    }

    public boolean getIsLoopback() {
        return this._is_loopback;
    }

    public String getIsType() {
        return this._type;
    }

    public boolean getIsLocal() {
        return this._is_local;
    }

    public String getIsIp() {
        return this._ip;
    }
}