package coreProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

import coreModel.DecoderInfo;
import coreModel.NssCoreContext;
import coreModel.NssData;
import coreModel.NssPacket;
import coreModel.Processor;
import coreModel.QuoteData;
import coreModel.RawData;
import coreModel.Subscription;
import coreSubscriber.Subscriber;
import util.SecurityCodeHelper;

public class QuoteProcessor extends Processor {
    public static final String id = "quote";
    final Logger log = Logger.getLogger("QuoteProcessor");

    NssCoreContext _context;
    //    Function equalLists = ListEquality().equals;
    Function equalLists;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    @Override
    public Map<String, Map<String, String>> process(NssPacket nssPacket) {
        Map<String, List<String>> _tempList = (Map<String, List<String>>) nssPacket.getParsedBody();
        final List<String> fieldIds = new ArrayList<String>(_tempList.get(0));
        final List<List<String>> contents = new ArrayList<List<String>>();

        for (int k = 1; k < _tempList.size(); k++) {
            if (_tempList.get(k).size() > 0) {
                contents.add(new ArrayList<String>(_tempList.get(k)));
            }
        }

        final Map<String, Map<String, String>> stockBundle = new HashMap<String, Map<String, String>>();
        final int codeIndex = fieldIds.indexOf("1");
        for (List<String> stock : contents) {
            final String code = stock.get(codeIndex);

            if (code != null) {
                Map<String, String> fields = new HashMap<String, String>();
                if (stockBundle.get(code) != null) {
                    stockBundle.put(code, new HashMap<String, String>());
                }

                Map<String, String> fieldValue = new HashMap<String, String>();
                if (fieldValue.get("1") != null) {
                    fieldValue.put("1", code);
                }

                for (int i = 0; i < fieldIds.size(); i++) {
                    if (fieldIds.get(i) == "1") {
                        continue;
                    }
                    final String fieldId = fieldIds.get(i);
                    fields.put(fieldId, stock.get(i));
                }
            }
        }

        return stockBundle;
    }

    @Override
    public void notify(NssPacket nssPacket, Object data) {
        Map<Subscriber, List<QuoteData>> subscriberUpdateList = new HashMap<Subscriber, List<QuoteData>>();
        Map<String, Map<String, String>> instrumentMap = (Map<String, Map<String, String>>) data;
        Map<String, Map<String, DecoderInfo>> reversedConfig =
                _context.getDecoderConfig().getReversedConfig();

        for (Map.Entry<String, Map<String, String>> entry : instrumentMap.entrySet()) {
            String instrumentCode = entry.getKey();
            Map<String, String> fieldValueMap = entry.getValue();
            String normalizedCode = SecurityCodeHelper.getNormalizedSecurityCode(instrumentCode);
            List<String> fieldIds = _context.getStorage().getQuoteFields(normalizedCode);
            log.info("process code: " + normalizedCode);

            for (Map.Entry<String, String> entry2 : fieldValueMap.entrySet()) {  // each field and its value
                String serverFieldId = entry2.getKey();
                String rawFieldValue = entry2.getValue();
                Map<String, DecoderInfo> aliasFieldIDs = reversedConfig.get(serverFieldId);
                if (aliasFieldIDs == null) {
                    Map<String, DecoderInfo> single = new HashMap<String, DecoderInfo>();
                    DecoderInfo decoderInfo = _context.getDecoderConfig().getConfig().get(serverFieldId);
                    single.put(serverFieldId, decoderInfo);

                    aliasFieldIDs = single;
                }

                for (Map.Entry<String, DecoderInfo> entry3 : aliasFieldIDs.entrySet()) {
                    String aliasFieldID = entry3.getKey();
                    DecoderInfo decoder = entry3.getValue();
                    if (fieldIds.contains(aliasFieldID)) {
                        boolean isOptionCode = nssPacket.getSeqNo() == -1;
                        RawData rawData = new RawData(instrumentCode);
                        rawData.setFieldID(aliasFieldID);
                        rawData.setServerFieldId(serverFieldId);
                        rawData.setData(rawFieldValue);
                        rawData.setSnapshot(isOptionCode);

                        NssData nssData = _context.getStorageDecoderDispatcher().decode(normalizedCode, rawData);
                        if (nssData == null) {
                            return;
                        }
                        QuoteData quoteData = _context
                                .getStorage()
                                .updateQuoteData(normalizedCode, nssData.getFieldID(), nssData);

                        if (quoteData != null && quoteData.getNssData().getReady()) {
                            List<Subscription> subscriptionList = quoteData.getSubscription();
                            for (Subscription subscription : subscriptionList) {
                                Subscriber subscriber = subscription.getSubsciber();
                                if (subscriberUpdateList.containsKey(subscriber)) {
                                    if (!subscriberUpdateList.get(subscriber).contains(quoteData)) {
                                        subscriberUpdateList.get(subscriber).add(quoteData);
                                    }
                                } else {
                                    subscriberUpdateList.put(subscriber, Arrays.asList(quoteData));
                                }
                                if (subscription.isSnapshot()) {
                                    _context.getStorage()
                                            .removeQuoteListener(normalizedCode, nssData.getFieldID(),
                                                    subscriber);
                                }
                            }
                        }
                    }

                }
            }
        }

        for (Map.Entry<Subscriber, List<QuoteData>> entry4 : subscriberUpdateList.entrySet()) {
            Subscriber subscriber = entry4.getKey();
            List<QuoteData> data4 = entry4.getValue();
            subscriber.informUpdate(data4);
        }
    }
}
