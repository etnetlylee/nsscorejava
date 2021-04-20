package coreConfigInterface;

public class ServerUrls {
    String _wss;
    String _web;
    String _ums;

    public ServerUrls(String wss, String web, String ums) {
        this._wss = wss;
        this._web = web;
        this._ums = ums;
    }

    public String getWss() {
        return this._wss;
    }

    public void setWss(String wss) {
        this._wss = wss;
    }

    public String getWeb() {
        return this._web;
    }

    public void setWeb(String web) {
        this._web = web;
    }

    public String getUms() {
        return this._ums;
    }

    public void setUms(String ums) {
        this._ums = ums;
    }
}
