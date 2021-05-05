package com.etnet.coresdk.events;

public class NssCloseReason {
    int _code;
    String _reason;

    public NssCloseReason(int code, String reason) {
        this._code = code;
        this._reason = reason;
    }

    public void setCode(int code) {
        this._code = code;
    }

    public int getCode() {
        return this._code;
    }

    public void setReason(String reason) {
        this._reason = reason;
    }

    public String getReason() {
        return this._reason;
    }
}
