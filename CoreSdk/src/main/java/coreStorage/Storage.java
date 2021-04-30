package coreStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import api.ContextProvider;
import coreEnvironment.Environment;
import coreModel.DataSubscription;
import coreModel.NssCoreContext;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.SortData;
import coreModel.Subscription;
import coreSubscriber.SubscriberJava;

public class Storage extends ContextProvider {
    final Logger log = Logger.getLogger("Storage");
    NssCoreContext _context;
    int _nextSeqNo = 0;
    final Map<String, Map<String, QuoteData>> _quoteStore = new HashMap<String, Map<String, QuoteData>>(); // code-field
    final Map<Integer, DataSubscription> _seqNoStore = new HashMap<Integer, DataSubscription>();
    // final List<NewsWrappedIntent> _newsStorage = [];
    // final _newsMetaStorage = new Map<String, Map<String, List<dynamic>>>();

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    public int getNextSeqNo() {
        return _nextSeqNo++;
    }

    public QuoteData getQuoteData(String code, String fieldId) {
        if (_quoteStore.containsKey(code)) {
            Map<String, QuoteData> fieldDataList = _quoteStore.get(code);
            if (fieldDataList.containsKey(fieldId)) {
                return fieldDataList.get(fieldId);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<String> getQuoteFields(String code) {
        final Map<String, QuoteData> store = _quoteStore.get(code);
        List<String> fieldList = new ArrayList<String>(store.keySet());
        if (!_quoteStore.containsKey(code)) {
            return fieldList;
        }

        return fieldList;
    }

    public QuoteData addQuoteData(String code, String fieldId, NssData initNssData) {
        if (_quoteStore.get(code) == null) {
            _quoteStore.put(code, new HashMap<String, QuoteData>());
        }

        QuoteData temp = new QuoteData(null, null);
        log.info(
                "add a new code-field item code=" + code + " fieldId=" + fieldId);
        temp.setCode(code);
        temp.setNssData(initNssData);
        if (_quoteStore.get(code) == null) {
            _quoteStore.get(code).put(fieldId, temp);
        }

        return _quoteStore.get(code).get(fieldId);
    }

    public QuoteData updateQuoteData(String code, String fieldId, NssData nssData) {
        if (_quoteStore.containsKey(code) &&
                _quoteStore.get(code).containsKey(fieldId)) {
            log.info("update code=" + code + " fieldId=" + fieldId);
            _quoteStore.get(code).get(fieldId).setNssData(nssData);
            return _quoteStore.get(code).get(fieldId);
        } else {
            log.info("no one subscribe this code-field, ignored");

            return null;
        }

        // _quoteStore.putIfAbsent(code, () => {});
        // _quoteStore[code].putIfAbsent(fieldId, () {
        //   log.info("updateQuoteData a new code-field item code=" + code + " fieldId=" + fieldId);
        //   QuoteData temp = QuoteData();
        //   temp.code = code;
        //   temp.nssData = nssData;
        //   return temp;
        // });
        // force update if still someone need it
        // if(_quoteStore[code][fieldId].subscription.length > 0) {
        // } else {
        //   log.info("no one subscribe this code-field, ignored");
        // }
    }

    public QuoteData removeQuoteData(String code, String fieldId) {
        if (_quoteStore.containsKey(code)) {
            Map<String, QuoteData> fieldDataList = _quoteStore.get(code);
            if (fieldDataList.containsKey(fieldId)) {
                QuoteData temp = fieldDataList.get(fieldId);
                fieldDataList.remove(fieldId);
                log.info("remove quote data code=" + code + "  fieldId= " + fieldId);
                return temp;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // TODO remove this as it is for debug print in nss_core.dart
    public Map<String, Map<String, QuoteData>> getQuoteStore() {
        return this._quoteStore;
    }

    public void addQuoteListener(
            String code, String fieldId, SubscriberJava subscriberJava, boolean snap) {
        QuoteData quoteData = getQuoteData(code, fieldId);

        if (quoteData == null) {
            quoteData = addQuoteData(code, fieldId, new NssData(null));
        }

        // quoteData.subscription

        Subscription subscription =
                new Subscription(snap, subscriberJava); // every subscription is new?
        assert (quoteData != null);
        quoteData.getSubscription().add(subscription);
    }

    public int removeQuoteListener(String code, String fieldId, SubscriberJava subscriberJava) {
        QuoteData quoteData = getQuoteData(code, fieldId);
        // abnormal case
        // assert(quoteData != null);
        if (quoteData == null) {
            log.info("no code-field found code=" + code + " fieldId=" + fieldId);
            return 0; // still trigger to send remove command
        }

        for (Subscription subscription : quoteData.getSubscription()) {
            if (subscription.getSubsciber() == subscriberJava) {
                log.info(
                        "remove subscriber viewId=" + subscription.getSubsciber().getViewId());
            }
        }

        log.info("snap of subscribers count=" +
                quoteData.getSubscription().size());

        // only count non-snapshot
        int streamCount = 0;
        for (Subscription subscription : quoteData.getSubscription()) {
            if (!subscription.isSnapshot()) {
                streamCount++;
            }
        }

        // clean quoteData;
        if (quoteData.getSubscription().size() == 0) {
            removeQuoteData(code, fieldId);
            if (_quoteStore.get(code).size() == 0) {
                _quoteStore.remove(code);
                log.info("remove code from quoteStore");
            }
        }
        log.info("code=" +
                code +
                " fieldId=" +
                fieldId +
                " streamCount=" +
                streamCount);

        return streamCount;
    }

    public boolean containsQuoteListener(
            String code, String fieldId, SubscriberJava subscriberJava, boolean snap) {
        QuoteData quoteData = getQuoteData(code, fieldId);
        if (quoteData == null) {
            return false;
        }
        int count = 0;

        for (Subscription subscription : quoteData.getSubscription()) {
            if (subscription.getSubsciber() == subscriberJava && subscription.isSnapshot() == snap) {

                count++;
            }
        }
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void addSequenceListener(int seqNo, SubscriberJava subscriberJava) {
        assert (_seqNoStore.containsKey(seqNo) == false);
        if (!_seqNoStore.containsKey(seqNo)) {
            if (_seqNoStore.get(seqNo) == null) {
                SortData sortData = new SortData(seqNo);
                Subscription subscription = new Subscription(false, subscriberJava);
                sortData.getSubscription().add(subscription);
                _seqNoStore.put(seqNo, sortData);
            }
        }
    }

    // addBrokerListener

    // addNewsListener

    public SubscriberJava removeSequenceListener(int seqNo, SubscriberJava subscriberJava) {
        if (_seqNoStore.containsKey(seqNo)) {
            DataSubscription data = _seqNoStore.get(seqNo);
            SubscriberJava removeSubscriberJava = data.getSubscription().get(0).getSubsciber();
            _seqNoStore.remove(seqNo);
            return removeSubscriberJava;
        } else {
            return null;
        }
    }

    public List<SubscriberJava> getSequenceSubscribers(int seqNo) {
        final List<SubscriberJava> subscriberJavaList = new ArrayList<SubscriberJava>();
        if (_seqNoStore.containsKey(seqNo)) {
            final List<Subscription> subscriptions =
                    _seqNoStore.get(seqNo).getSubscription();
            for (Subscription subscription : subscriptions) {
                subscriberJavaList.add(subscription.getSubsciber());
            }
        }
        return subscriberJavaList;
    }

    public DataSubscription getSequenceData(int seqNo) {
        if (_seqNoStore.containsKey(seqNo)) {
            return _seqNoStore.get(seqNo);
        } else {
            return null;
        }
    }

    public DataSubscription updateSequenceData(int seqNo, DataSubscription newSortData) {
        if (_seqNoStore.containsKey(seqNo)) {
            _seqNoStore.put(seqNo, newSortData);
            return _seqNoStore.get(seqNo);
        } else {
            if (_seqNoStore.get(seqNo) == null) {
                _seqNoStore.put(seqNo, newSortData);
            }
            return _seqNoStore.get(seqNo);
        }
    }

    public boolean isChartSubscribed(String code, String paramHash) {
        return _quoteStore.containsKey(code) &&
                _quoteStore.get(code).containsKey(paramHash);
    }

    public boolean hasChartSubscription(
            SubscriberJava subscriberJava, String code, String paramHash) {
        if (isChartSubscribed(code, paramHash)) {
            QuoteData quoteData = _quoteStore.get(code).get(paramHash);
            int _count = 0;
            for (Subscription subscription : quoteData
                    .getSubscription()) {
                if (subscription.getSubsciber() == subscriberJava) {
                    _count++;
                }
            }
            if (_count == 1) {
                return true;
            } else {
                return false;
            }
        } else {
            if (Environment.isDebug()) {
                log.info("no subscriber for code=${code},paramHash=${paramHash}");
            }
            return false;
        }
    }

    public void addChartSubscription(
            SubscriberJava subscriberJava, String code, String paramHash) {
        // _addQuoteListener(code, paramHash, subscriber, false);
        if (!_quoteStore.containsKey(code)) {
            _quoteStore.put(code, new HashMap<String, QuoteData>());
        }

        if (!_quoteStore.get(code).containsKey(paramHash)) {
            _quoteStore.get(code).put(paramHash, new QuoteData(null, null));
        }

        int subCount = 0;
        for (Subscription v : _quoteStore.get(code).get(paramHash).getSubscription()) {
            if (v.getSubsciber() == subscriberJava) {
                subCount++;
            }
        }

        if (subCount > 0) {
            if (Environment.isDebug()) {
                log.info("duplicate subscriber for the chart type " +
                        paramHash +
                        "of code " +
                        code);
            }
        } else {
            Subscription subscription = new Subscription(false, subscriberJava);
            _quoteStore.get(code).get(paramHash).getSubscription().add(subscription);
        }
    }

    public SubscriberJava removeChartSubscription(
            SubscriberJava subscriberJava, String code, String paramHash) {
        if (_quoteStore.containsKey(code) &&
                _quoteStore.get(code).containsKey(paramHash)) {
            QuoteData quoteData = _quoteStore.get(code).get(paramHash);
            int i = quoteData.getSubscription().size();
            while (i-- > 0) {
                Subscription subscription = quoteData.getSubscription().get(i);
                if (subscription.getSubsciber() == subscriberJava) {
                    quoteData.getSubscription().remove(i);
                }
            }

            if (quoteData.getSubscription().size() == 0) {
                _quoteStore.get(code).remove(paramHash);
            }

            if (_quoteStore.get(code).size() == 0) {
                _quoteStore.remove(code);
            }
        } else {
            if (Environment.isDebug()) {
                log.info("[Storage] subscriber not found for the chart type " +
                        paramHash +
                        "of code " +
                        code);
            }
        }

        return subscriberJava;
    }

    public List<Subscription> getChartSubscription(String code, String paramHash) {
        List<Subscription> subscription = new ArrayList<Subscription>();
        if (_quoteStore.containsKey(code) &&
                _quoteStore.get(code).containsKey(paramHash)) {
            QuoteData quoteData = _quoteStore.get(code).get(paramHash);
            subscription = quoteData.getSubscription();
        }
        return subscription;
    }

    // getNewsStorage

    // getNewsMetaStorage
    public void getNewsMetaStorage() {
        // TODO
    }

    public void informNewsUpdate(SubscriberJava listener) {
        // TODO
    }

    // informNewsItemUpdate

    public void clear() {
        _nextSeqNo = 0;
        _quoteStore.clear();
        _seqNoStore.clear();
        // _newsStorage.clear();
        // _newsMetaStorage.clear()
    }
}
