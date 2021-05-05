package com.etnet.coresdk.coreConfigInterface;

import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.AsaDecoder;

public class AsaConfigInfo {
    Map<String, Map<String, AsaDecoder>> _configs;
    List<String> _streamList;

    public AsaConfigInfo(
            Map<String, Map<String, AsaDecoder>> configs, List<String> streamList) {
        this._configs = configs;
        this._streamList = streamList;
    }
    public Map<String, Map<String, AsaDecoder>> getConfigs(){
        return this._configs;
    }

    public void setConfigs(Map<String, Map<String, AsaDecoder>> configs){
        this._configs = configs;
    }

    public List<String> getStreamList(){
        return this._streamList;
    }

    public void setStreamList(List<String> streamList){
        this._streamList = streamList;
    }
}
