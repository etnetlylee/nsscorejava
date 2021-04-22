package config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coreConfigInterface.AsaConfigInfo;
import coreConfigInterface.AsaDecodersConfig;
import coreModel.AsaDecoder;

public class AsaConfig {
    Map<String, Map<String, AsaDecoder>> _config = new HashMap<String, Map<String, AsaDecoder>>();
    List<String> _streamList = new ArrayList<String>();

    public AsaConfigInfo getConfig() {
        return new AsaConfigInfo(_config, _streamList);
    }

    public void addConfig(AsaDecodersConfig asa) {
        _streamList.addAll(asa.getStreamOnly());
        if (_config.containsKey(asa.getField())) {
            Map<String, AsaDecoder> codeList = _config.get(asa.getField());

            for (Map.Entry<String, AsaDecoder> entry : asa.getDecoders().entrySet()) {
                codeList.put(entry.getKey(), entry.getValue());
            }
        } else {
            _config.put(asa.getField(), asa.getDecoders());
        }
    }

}
