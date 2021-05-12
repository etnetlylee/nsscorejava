package com.etnet.coresdk.nssCoreService;


import java.util.Map;

public class IPGeoInfo {
    boolean is_cn;
    boolean is_loopback;
    String type;
    boolean is_local;
    String ip;

    public IPGeoInfo(
            boolean _is_cn, boolean _is_local, String _type, boolean _is_loopback, String _ip) {
        this.is_cn = _is_cn;
        this.is_local = _is_local;
        this.type = _type;
        this.is_loopback = _is_loopback;
        this.ip = _ip;
    }

    public static IPGeoInfo fromJson(Map<String, Object> data) {
        return new IPGeoInfo((Boolean) data.get("is_cn"), (Boolean) data.get("is_local"), String.valueOf(data.get(
                "is_local")),
                (Boolean) data.get("is_loopback"), String.valueOf(data.get("ip")));
    }

    public boolean getIsCn() {
        return this.is_cn;
    }

    public boolean getIsLoopback() {
        return this.is_loopback;
    }

    public String getIsType() {
        return this.type;
    }

    public boolean getIsLocal() {
        return this.is_local;
    }

    public String getIsIp() {
        return this.ip;
    }
}