package coreConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coreModel.DecoderInfo;

public class DecoderConfig {
    Map<String, DecoderInfo> _config = new HashMap<String, DecoderInfo>();
    Map<String, Map<String, DecoderInfo>> _reversedConfig = new HashMap<String, Map<String, DecoderInfo>>();
    Map<String, List<String>> _reversedKeyMap = new HashMap<String, List<String>>();

    public Map<String, DecoderInfo> getConfig() {
        return this._config;
    }

    public DecoderInfo getConfigDecoder(String fieldId) {
        return this._config.get(fieldId);
    }

    public Map<String, Map<String, DecoderInfo>> getReversedConfig() {
        return this._reversedConfig;
    }

    public void setConfig(Map<String, DecoderInfo> config) {

        for (Map.Entry<String, DecoderInfo> entry : config.entrySet()) {
            String aliasFieldID = entry.getKey();
            DecoderInfo decoderInfo = entry.getValue();
            _config.put(aliasFieldID, decoderInfo);
            final List<String> serverFieldIds = decoderInfo.getServerFieldIds();
            if (serverFieldIds.size() == 1 && serverFieldIds.get(0) == aliasFieldID) {
                // skip
            } else {
                Collections.sort(serverFieldIds);
                _reversedKeyMap.put(aliasFieldID, serverFieldIds);

                for (String fieldID : serverFieldIds) {
                    if (_reversedConfig.get(fieldID) == null) {
                        _reversedConfig.put(fieldID, new HashMap<String, DecoderInfo>());
                    }

                    final Map<String, DecoderInfo> aliasFieldIDsList = _reversedConfig.get(fieldID);
                    aliasFieldIDsList.put(aliasFieldID, decoderInfo);
                    if (aliasFieldIDsList.get(fieldID) == null) {
                        aliasFieldIDsList.put(fieldID, decoderInfo);
                    }
                }
            }
        }
    }
}
