package com.etnet.coresdk.coreStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.etnet.coresdk.api.ContextProvider;
import com.etnet.coresdk.coreEnvironment.Environment;
import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.DecoderInfo;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.data.decoder.StringDecoder;

import static com.etnet.coresdk.coreConfig.DefaultDecoderConfig.DefaultDecoderConfig;

public class StorageDecodeDispatcher extends ContextProvider {
    final Logger log = Logger.getLogger("StorageDecodeDispatcher");

    NssCoreContext _context;
    Map<String, Decoder> decoderPool = new HashMap<String, Decoder>(); // fieldId => Decoder
    Map<String, DecoderInfo> decoders = DefaultDecoderConfig;
    Decoder defaultDecoder = new StringDecoder();

    public StorageDecodeDispatcher() {
        log.info("supported decoders: " + decoders.size());
        for (Map.Entry<String, DecoderInfo> entry : decoders.entrySet()) {
            log.fine("   - key: " + entry.getKey() + " , processor_type: " + entry.getValue().getUniqueId());
        }
    }

    Decoder getDecoder(String code, String fieldId) {
        Decoder decoder;
        if (decoders.containsKey(fieldId)) {
            DecoderInfo decoderInfo = decoders.get(fieldId);
            if (decoderPool.containsKey(decoderInfo.getUniqueId())) {
                decoder = decoderPool.get(decoderInfo.getUniqueId());
            } else {
                decoder = decoders.get(fieldId).ifAbsent();
                decoder.setContext(_context);
                if (decoderPool.get(fieldId) == null) {
                    decoderPool.put(fieldId, decoder);
                }
            }
        } else {
            decoder = defaultDecoder;
        }
        return decoder;
    }

    public NssData decode(String code, RawData rawData) {
        Decoder decoder = getDecoder(code, rawData.getFieldID());
        DecoderInfo decoderInfo =
                _context.getDecoderConfig().getConfig().get(rawData.getFieldID());
        RawData rawValue = (RawData) rawData.getData();
        NssData nssData = new NssData(null);

        try {
            nssData = decoder.decodeStream(code, rawData);

            if (nssData == null) {
                nssData = decoder.decodeRaw(code, rawData);
                log.info("cannot determine decoder method: ${rawData.getFieldID()}");
            }

            if (nssData != null) {
                if (nssData.getFieldID() == null) {
                    nssData.setFieldID(rawData.getFieldID());
                }
                if (decoderInfo != null) {
                    nssData.setName(decoderInfo.getName());
                }
            } else {
                log.info("please return NssData for field: ${rawData.getFieldID()}");
            }
        } catch (Exception e) {
            if (Environment.isDebug()) {
                log.info(e.getMessage());
            }
        }

        return nssData;
    }

    @Override
    public void setContext(NssCoreContext context) {
        _context = context;
        defaultDecoder.setContext(context);
    }
}
