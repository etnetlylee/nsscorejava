package com.etnet.coresdk.config;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.etnet.coresdk.coreModel.ProcessorInfo;

public class ProcessorConfig {
    final Logger log = Logger.getLogger("ProcessorConfig");
    Map<String, ProcessorInfo> _processorConfig = new HashMap<String, ProcessorInfo>();

    public Map<String, ProcessorInfo> getConfig() {
        return _processorConfig;
    }

    public void setConfig(Map<String, ProcessorInfo> config) {
        for (String key : config.keySet()) {
            if (_processorConfig.get(key) != null) {
                _processorConfig.put(key, _processorConfig.get(key));
            } else {
                _processorConfig.put(key, config.get(key));
            }
        }
        log.info("set config done: ${_processorConfig}");
    }
}
