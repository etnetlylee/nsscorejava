package com.etnet.coresdk.coreConfigInterface;

import java.util.List;
import java.util.Map;

import com.etnet.coresdk.coreModel.AsaDecoder;

public class AsaDecodersConfig {
    String _field;
    Map<String, AsaDecoder> _decoders;
    List<String> _streamOnly;

    public AsaDecodersConfig(
            String field,
            Map<String, AsaDecoder> decoders,
            List<String> streamOnly
            ) {
        this._field = field;
        this._decoders = decoders;
        this._streamOnly = streamOnly;
    }

    public String getField() {
        return this._field;
    }

    public Map<String, AsaDecoder> getDecoders() {
        return this._decoders;
    }

    public List<String> getStreamOnly() {
        return this._streamOnly;
    }
}
