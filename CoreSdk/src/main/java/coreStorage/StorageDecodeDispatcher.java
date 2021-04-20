package coreStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import api.ContextProvider;
import coreModel.Decoder;
import coreModel.DecoderInfo;
import coreModel.NssCoreContext;
import coreStorage.data.decoder.StringDecoder;

import static coreConfig.DefaultDecoderConfig.DefaultDecoderConfig;

public class StorageDecodeDispatcher extends ContextProvider {
    final Logger log = Logger.getLogger(""StorageDecodeDispatcher"");

    NssCoreContext _context;
    Map<String, Decoder> decoderPool = new HashMap<String, Decoder>(); // fieldId => Decoder
    Map<String, DecoderInfo> decoders = DefaultDecoderConfig;
    Decoder defaultDecoder = new StringDecoder();

    StorageDecodeDispatcher() {
        log.info("supported decoders: " + decoders.length.toString());
        decoders.forEach((String key, DecoderInfo decoderInfo) {
            log.fine("   - key: " +
                    key +
                    " , processor_type: " +
                    decoderInfo.getUniqueId());
        });
    }

    Decoder getDecoder(String code, String fieldId) {
        Decoder decoder;
        if (decoders.containsKey(fieldId)) {
            DecoderInfo decoderInfo = decoders[fieldId];
            if (decoderPool.containsKey(decoderInfo.getUniqueId())) {
                decoder = decoderPool[decoderInfo.getUniqueId()];
            } else {
                decoder = decoders[fieldId].ifAbsent();
                decoder.setContext(_context);
                decoderPool.putIfAbsent(fieldId, () = > decoder);
            }
        } else {
            decoder = defaultDecoder;
        }
        return decoder;
    }

    NssData decode(String code, RawData rawData) {
        Decoder decoder = getDecoder(code, rawData.getFieldID());
        DecoderInfo decoderInfo =
                _context.getDecoderConfig().getConfig()[rawData.getFieldID()];
        dynamic rawValue = rawData.getData();
        NssData nssData;

        try {
            if (rawValue is String){
                nssData = decoder.decodeStream(code, rawData);
            } else if (rawValue is List){
                nssData = decoder.decodeSnapshot(code, rawData);
            }

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
        } catch (e) {
            if (Environment.isDebug()) {
                log.info(e);
            }
        }

        return nssData;
    }

    @override
    void setContext(NssCoreContext context) {
        _context = context;
        defaultDecoder.setContext(context);
    }
}
