package com.etnet.coresdk.coreStorage.model;

public class BrokerInfo {
    String _no;
    String _brokerName;
    String _brokerTcName;
    String _brokerScName;
    String _brokerEnName;

    public BrokerInfo(
    String no, String brokerName,
        String brokerTcName,
        String brokerScName,
        String brokerEnName) {
        this._no = no;
        this._brokerName = brokerName;
        this._brokerTcName = brokerTcName;
        this._brokerScName = brokerScName;
        this._brokerEnName = brokerEnName;
    }

    public String getNo(){
        return this._no;
    }
    public String getBrokerName(){
        return this._brokerName;
    }
    public String getBrokerTcName(){
        return this._brokerTcName;
    }
    public String getBrokerScName(){
        return this._brokerScName;
    }
    public String getBrokerEnName(){
        return this._brokerEnName;
    }
}
