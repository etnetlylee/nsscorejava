package com.etnet.coresdk.coreConfigInterface;

import java.util.Map;

public class CoreConfig {
    String _productName;
    String _lang;
    ServerUrls _server;
    InitiatorConfig _initiator;
    String _region;
    boolean _enableCensor;
    Map<String, String> _apiEndpoints;
    boolean _useRandomWSIP;
    String _market;
    String _timezone;
    String _corpName;
    public boolean _debug;

    public CoreConfig(
            String productName,
            String lang,
            ServerUrls server,
            InitiatorConfig initiator,
            String region,
            boolean enableCensor,
            Map<String, String> apiEndpoints,
            boolean useRandomWSIP,
            String market,
            String timezone,
            String corpName,
            boolean debug) {
        this._productName = productName;
        this._lang = lang;
        this._server = server;
        this._initiator = initiator;
        this._region = region;
        this._enableCensor = enableCensor;
        this._apiEndpoints = apiEndpoints;
        this._useRandomWSIP = useRandomWSIP;
        this._market = market;
        this._timezone = timezone;
        this._corpName = corpName;
        this._debug = debug;
    }

    public String getProductName() {
        return this._productName;
    }

    public void setUseASA(String productName) {
        this._productName = productName;
    }

    public String getLang() {
        return this._lang;
    }

    public void setLang(String lang) {
        this._lang = lang;
    }

    public ServerUrls getServer() {
        return this._server;
    }

    public void setServer(ServerUrls server) {
        this._server = server;
    }

    public InitiatorConfig getInitiator() {
        return this._initiator;
    }

    public void setInitiator(InitiatorConfig initiator) {
        this._initiator = initiator;
    }

    public String getRegion() {
        return this._region;
    }

    public void setRegion(String region) {
        this._region = region;
    }

    public boolean getEnableCensor() {
        return this._enableCensor;
    }

    public void setEnableCensor(boolean enableCensor) {
        this._enableCensor = enableCensor;
    }

    public Map<String, String> getApiEndpoints() {
        return this._apiEndpoints;
    }

    public void setApiEndpoints(Map<String, String> apiEndpoints) {
        this._apiEndpoints = apiEndpoints;
    }

    public boolean getUseRandomWSIP() {
        return this._useRandomWSIP;
    }

    public void setUseRandomWSIP(boolean useRandomWSIP) {
        this._useRandomWSIP = useRandomWSIP;
    }

    public String getMarket() {
        return this._market;
    }

    public void setMarket(String market) {
        this._market = market;
    }

    public String getTimezone() {
        return this._timezone;
    }

    public void setTimezone(String timezone) {
        this._timezone = timezone;
    }

    public String getCorpName() {
        return this._corpName;
    }

    public void setCorpName(String corpName) {
        this._corpName = corpName;
    }

    public boolean getDebug() {
        return this._debug;
    }

    public void setDebug(boolean debug) {
        this._debug = debug;
    }

}
