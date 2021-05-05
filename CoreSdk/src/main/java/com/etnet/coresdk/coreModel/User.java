package com.etnet.coresdk.coreModel;

public class User {
    boolean _isAuthed;
    boolean _isSessionEnd;
    String _userName;
    String _token;
    String _mtsToken;
    String _webToken;

    public User(String username, String webToken, String nssToken) {
        this._userName = username;
        this._webToken = webToken;
        this._mtsToken = webToken;
        this._token = nssToken;
    }

    public String getUserId() {
        return this._userName;
    }

    public void setUserId(String userId) {
        this._userName = userId;
    }

    public String getToken() {
        return this._token;
    }

    public void setToken(String token) {
        this._token = token;
    }

    public String getMtsToken() {
        return this._mtsToken;
    }

    public void setMtsToken(String mtsToken) {
        this._mtsToken = mtsToken;
    }

    public String getWebToken() {
        return this._webToken;
    }

    public void setWebToken(String webToken) {
        this._webToken = webToken;
    }

    public void setAuth(boolean auth) {
        this._isAuthed = auth;
    }

    public boolean isAuth() {
        return this._isAuthed;
    }

    public void resetSession() {
        this._isSessionEnd = false;
    }

    public boolean isSessionEnd() {
        return this._isSessionEnd;
    }

    public void endSession() {
        this._isSessionEnd = true;
    }
}
