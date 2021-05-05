package com.etnet.coresdk.coreCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.etnet.coresdk.api.ContextProvider;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreSubscriber.SubscriberJava;
import okhttp3.Response;
import com.etnet.coresdk.util.DecodeHelper;

public class HttpCommand extends ContextProvider {
    final Logger log = Logger.getLogger("HttpCommand");
    NssCoreContext _context;

    public void sendHttpGetRequest(
            String url, String code, String fieldID, SubscriberJava subscriberJava) {
        Response result = _context
                .getController()
                .getNetworkController()
                .sendHttpGetRequest(url);
            if (result.code() == 200){
                String _resultString = String.valueOf(result.body());
                List<String> raw = Arrays.asList(_resultString.split("\n"));
                String normalizedCode = DecodeHelper.normalizeCode(code);
                RawData rawData = new RawData(code);
                rawData.setData(raw);
                rawData.setFieldID(fieldID);
                rawData.setServerFieldId(fieldID);
                rawData.setSnapshot(true);
                NssData nssData =
                        _context.getStorageDecoderDispatcher().decode(code, rawData);
                QuoteData quoteData = new QuoteData(null, null);
                quoteData.setNssData(nssData);
                quoteData.setCode(normalizedCode);
                nssData.setSnapshot(true);
                nssData.setReady(true);
                List<QuoteData> _tempList = Arrays.asList(quoteData);
                subscriberJava.informUpdate(_tempList);
            } else {
                log.info("status code != 200 (" + String.valueOf(result.code()) + ")");
                subscriberJava.informUpdate(new ArrayList<QuoteData>());
            }
    }

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }
}
