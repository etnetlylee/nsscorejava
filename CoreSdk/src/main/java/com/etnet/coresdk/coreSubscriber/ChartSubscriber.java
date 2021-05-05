package com.etnet.coresdk.coreSubscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.etnet.coresdk.api.CommonSubscriber;
import com.etnet.coresdk.api.OnChartDataReceived;
import com.etnet.coresdk.api.OnDataSubscription;
import com.etnet.coresdk.api.OnQuoteDataReceived;
import com.etnet.coresdk.coreController.SubscriberController;
import com.etnet.coresdk.coreModel.DataState;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.Subscription;
import com.etnet.coresdk.coreStorage.model.SubscriptionParam;
import com.etnet.coresdk.coreStorage.model.Transaction;
import com.etnet.coresdk.coreSubscriber.request.ChartRequest;

import com.etnet.coresdk.coreSubscriberRequestChart.DayChartSubscriber;
import com.etnet.coresdk.coreSubscriberRequestChart.GenericChartSubscriber;
import com.etnet.coresdk.coreSubscriberRequestChart.MinuteChartSubscriber;
import com.etnet.coresdk.util.ChartHelper;
import com.etnet.coresdk.util.DateHelper;
import com.etnet.coresdk.util.SecurityCodeHelper;

public class ChartSubscriber extends CommonSubscriber
        implements OnQuoteDataReceived, OnDataSubscription {
    final Logger log = Logger.getLogger("ChartSubscriber");

    NssCoreContext _context;
    String _name;
    SubscriberJava _subscriberJava;
    Map<String, SubscriptionParam> _subscriptionParamMap = new HashMap<String, SubscriptionParam>();
    boolean _snapshot;
    List<String> _codes;
    String _period;
    boolean _tradingDayOnly;
    int _range;

    Map<String, List<Transaction>> _histTransListCache = new HashMap<String, List<Transaction>>();
    Map<String, List<Transaction>> _streamTransListCache = new HashMap<String, List<Transaction>>();
    OnChartDataReceived _onChartDataReceived;
    List<String> _noNeedAccuCodeList = Arrays.asList("CSI.HDQ", "HSIS.SDQ", "SZSE.KDQ", "HSIS.ZDQ");

    ChartSubscriber(String name) {
        _name = name;
        _range = 0; // unlimit
        _snapshot = false;
        _tradingDayOnly = false;
        _subscriberJava = new SubscriberJava("ChartSubscriber@" + name, this);
    }

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    public boolean isMinuteChart(String period) {
        return Pattern.compile("^[d]+I.*").matcher(period).matches();
    }

    public void setOnChartDataReceived(OnChartDataReceived onChartDataReceived) {
        this._onChartDataReceived = onChartDataReceived;
    }

    public void setPeriod(String period) {
        this._period = period;
    }

    public String getPeriod() {
        return this._period;
    }

    public void setRange(int range) {
        this._range = range;
    }

    public void setCodes(List<String> codes) {
        this._codes = codes;
    }

    public void setSnapshot(boolean snapshot) {
        this._snapshot = snapshot;
    }

    public void setTradingDayOnly(boolean tradingDayOnly) {
        this._tradingDayOnly = tradingDayOnly;
    }

    @Override
    public void subscribe() {
        if (_codes != null) {
            for (String code : _codes) {
                ChartRequest chartRequest =
                        _context.getController().getRequestController().createChartRequest(Arrays.asList(code)
                                , _period, _tradingDayOnly, _range, _snapshot);
                if (_histTransListCache.get(code) != null) {
                    _histTransListCache.put(code, new ArrayList<Transaction>());
                }
                if (_streamTransListCache.get(code) != null) {
                    _streamTransListCache.put(code, new ArrayList<Transaction>());
                }

                String paramHash = ChartHelper.makeClientFieldID(
                        _period, _tradingDayOnly, _range, _snapshot);
                String key = code + "(" + paramHash + ")";
                SubscriberController sc =
                        _context.getController().getSubscriberController();
                if (!sc.hasChartDataState(key)) {
                    sc.addChartDataState(key, new DataState());
                } else {
                    sc.increaseDataStateCount(key);
                }

                chartRequest.setOnDataSubscription(this);
                _subscriberJava.subscribe(chartRequest);
                _subscriptionParamMap.put(code, new SubscriptionParam(
                        code,
                        _period,
                        _tradingDayOnly,
                        _range,
                        _snapshot));
            }

            _codes = new ArrayList<String>();
        }
    }

    @Override
    public void onAddSubscription(String code, String period, int range, boolean tradingDayOnly, boolean snapshot) {
        String paramHash =
                ChartHelper.makeClientFieldID(period, tradingDayOnly, range, snapshot);
        String key = code + "(" + paramHash + ")";
        SubscriberController sc =
                _context.getController().getSubscriberController();
        log.info("try to find subscriber with key=${key}");
        if (!sc.hasChartSubscriber(key)) {
            GenericChartSubscriber genericChartSubscriber;
            if (isMinuteChart(period)) {
                genericChartSubscriber =
                        new MinuteChartSubscriber("MinuteChartSubscriber@" + _name);
            } else {
                genericChartSubscriber =
                        new DayChartSubscriber("DayChartSubscriber@" + _name);
            }
            int codeGenericType = SecurityCodeHelper.getGeneralSecurityID(code);
            String nssPeriodFormat =
                    ChartHelper.getRequestPeriod(codeGenericType, period, false);

            genericChartSubscriber.setPeriod(period);
            genericChartSubscriber.setTradingDayOnly(tradingDayOnly);
            genericChartSubscriber.setRange(range);
            genericChartSubscriber.setContext(_context);
            genericChartSubscriber.setOnQuoteDataReceived(this);
            genericChartSubscriber.codes(Arrays.asList(code));
            genericChartSubscriber.fields(Arrays.asList(nssPeriodFormat));
            genericChartSubscriber.snapshot(snapshot);

            genericChartSubscriber.subscribe();

            sc.addChartSubscriber(key, genericChartSubscriber);
        } else {
            log.info("no subscriber key=${key}");
        }
    }

    @Override
    public void onRemoveSubscription(String code, String period, int range,
                                     boolean tradingDayOnly, boolean snapshot) {
        String paramHash =
                ChartHelper.makeClientFieldID(period, tradingDayOnly, range, snapshot);
        String key = code + "(" + paramHash + ")";
        SubscriberController sc =
                _context.getController().getSubscriberController();
        if (sc.hasChartSubscriber(key)) {
            log.info("remove subscription c=${code} f=${period}");
            GenericChartSubscriber genericChartSubscriber =
                    sc.getChartSubscriber(key);
            genericChartSubscriber.unsubscribe();

            sc.removeChartSubscriber(key, genericChartSubscriber);
        } else {
            log.info("no one subscribe! c=${code} f=${period}");
        }
    }

    @Override
    public void unsubscribe() {
        if (_codes != null && _codes.size() > 0) {
            for (String code : _codes) {
                SubscriptionParam sp = _subscriptionParamMap.get(code);
                if (sp != null) {
                    ChartRequest chartRequest = _context
                            .getController()
                            .getRequestController()
                            .createRemoveChartRequest(
                                    Arrays.asList(code), sp.getPeriod(), sp.getTradingDayOnly(), sp.getRange(),
                                    sp.getSnapshot());

                    chartRequest.setOnDataSubscription(this);
                    _subscriberJava.unsubscribe(chartRequest);

                    _subscriptionParamMap.remove(code);
                    _streamTransListCache.remove(code);
                    _histTransListCache.remove(code);

                    String paramHash = ChartHelper.makeClientFieldID(
                            _period, _tradingDayOnly, _range, _snapshot);
                    String key = code + "(" + paramHash + ")";
                    _context
                            .getController()
                            .getSubscriberController()
                            .deleteChartDataState(key);
                } else {
                    log.info("code is not subscribed code=${code}");
                }
            }
        } else {
            for (Map.Entry<String, SubscriptionParam> entry : _subscriptionParamMap.entrySet()) {
                String code = entry.getKey();
                SubscriptionParam sp = entry.getValue();
                ChartRequest chartRequest = _context
                        .getController()
                        .getRequestController()
                        .createRemoveChartRequest(
                                Arrays.asList(code), sp.getPeriod(), sp.getTradingDayOnly(), sp.getRange(),
                                sp.getSnapshot());

                chartRequest.setOnDataSubscription(this);
                _subscriberJava.unsubscribe(chartRequest);

                String paramHash = ChartHelper.makeClientFieldID(
                        _period, _tradingDayOnly, _range, _snapshot);
                String key = code + "(" + paramHash + ")";
                _context
                        .getController()
                        .getSubscriberController()
                        .deleteChartDataState(key);

            }
            _subscriptionParamMap.clear();
            _streamTransListCache.clear();
            _histTransListCache.clear();
        }
    }

    @Override
    public void resubscribe() {
        unsubscribe();
        subscribe();
    }

    /**
     * Merge incoming data list into quote store
     *
     * @param code       stock code
     * @param period     chart period
     * @param cacheTrans transactions in store
     * @param newTrans   transaction will be merged
     */
    public Transaction mergeTransaction(String code, String period,
                                        Transaction cacheTrans, Transaction newTrans) {
        if (newTrans.getTransNo() > cacheTrans.getTransNo() || newTrans.getTransNo() == -1) {
            cacheTrans.setTransNo(newTrans.getTransNo()); // update this group"s last transNo
            cacheTrans.setDisplayTimestamp(newTrans.getDisplayTimestamp());
            cacheTrans.setTimestamp(newTrans.getTimestamp());
            cacheTrans.setTranType("#"); // clean
            if (_noNeedAccuCodeList.contains(code)) {
                // only day chart will replace volume directly
                cacheTrans.setVolume(newTrans.getVolume());
            } else {
                if (cacheTrans.getBaseVolume() instanceof java.lang.Double) {
                    // some old data, merge it
                    cacheTrans.setVolume(cacheTrans.getBaseVolume() + newTrans.getVolume());
                } else {
                    cacheTrans.setVolume(cacheTrans.getVolume() + newTrans.getVolume());
                }
            }

            if (newTrans.getHigh() > cacheTrans.getHigh()) {
                cacheTrans.setHigh(newTrans.getHigh());
            }

            if (newTrans.getLow() < cacheTrans.getLow()) {
                cacheTrans.setLow(newTrans.getLow());
            }

            cacheTrans.setClose(newTrans.getClose());
        } else {
            log.info("invalid transNo sequence ${newTrans} ${cacheTrans}");
        }

        return cacheTrans;
    }

    @Override
    public void onQuoteDataReceived(List<QuoteData> bundle) {
        // data From MinuteChartSubscriber and DayChartSubscriber
        QuoteData firstQuoteData = bundle.get(0);
        NssData firstNssData = firstQuoteData.getNssData();
        String code = firstQuoteData.getCode();
        boolean snapshot = firstNssData.getSnapshot();
        List<Transaction> transactionList = (List<Transaction>) firstNssData.getData();
        String paramHash = ChartHelper.makeClientFieldID(
                _period, _tradingDayOnly, _range, _snapshot);
        String key = code + "(" + paramHash + ")";
        QuoteData chartData = _context.getStorage().getQuoteData(code, paramHash);
        NssData chartNssData = chartData.getNssData();
        List<Transaction> transList = (List<Transaction>) chartNssData.getData();
        DataState dataState = _context
                .getController()
                .getSubscriberController()
                .getChartDataState(key);
        SubscriptionParam sp = _subscriptionParamMap.get(code);
        List<Transaction> codeTransList = _streamTransListCache.get(code);
        if (!dataState.getDataReady()) {
            if (snapshot) {
                _histTransListCache.get(code).addAll(transactionList);
                dataState.setDataReady(true); // next round, we will merge histTransListCache & streamTransListCache
                log.info(
                        "snapshot data received ${_histTransListCache[code].length} ${_histTransListCache}");
            } else {
                // merge same grouptime
                for (Transaction newTrans : transactionList) {
                    int pos = -1;
                    for (Transaction v : codeTransList) {
                        if (newTrans.getGroupTimestamp() == v.getGroupTimestamp()) {
                            pos = codeTransList.indexOf(v.getGroupTimestamp());
                            continue;
                        }
                    }

                    if (pos == -1) {
                        codeTransList.add(newTrans);
                    } else {
                        // merge same transNo
                        mergeTransaction(code, sp.getPeriod(), codeTransList.get(pos), newTrans);
                    }
                }
            }
        } else {
            // data (hist + stream are ready, we now save them in codeTransList for next stage)
            _streamTransListCache.put(code, transactionList);
            codeTransList = transactionList;
        }

        if (dataState.getDataReady()) {
            // get from hist
            if (_histTransListCache.get(code).size() > 0) {
                // transList is from store, update it will update list in store

                transList.addAll(_histTransListCache.get(code));
                _histTransListCache.put(code, new ArrayList<Transaction>());
            }

            // get from cached stream
            List<Transaction> updatedTrans = new ArrayList<Transaction>();
            if (codeTransList.size() > 0) {
                for (Transaction streamTrans : codeTransList) {
                    // case 1: empty list
                    // case 2: 101, 104 week
                    // case 3: 103, 105 month
                    // case 4: 100 day
                    // case 5: 1,5,15,30,60 minutes

                    if (transList.size() == 0) {
                        transList.add(streamTrans);
                        continue;
                    }

                    Transaction lastTransaction = transList.get(transList.size() - 1);
                    if (getPeriod() == "1W") {
                        if (DateHelper.isInSameYearWeek(
                                streamTrans.getGroupTimestamp(), lastTransaction.getGroupTimestamp())) {
                            // update lastTransaction
                            updatedTrans.add(mergeTransaction(
                                    code, sp.getPeriod(), lastTransaction, streamTrans));
                        } else {
                            if (!(streamTrans.getBaseVolume() instanceof java.lang.Double)) {
                                streamTrans.setBaseVolume(0.0);// new record, however, we set baseVolume to zero for
                                // accumulate volume
                            }
                            transList.add(streamTrans);
                            updatedTrans.add(streamTrans);
                        }
                    } else if (getPeriod() == "1M") {
                        if (DateHelper.isInSameYearMonth(
                                streamTrans.getGroupTimestamp(), lastTransaction.getGroupTimestamp())) {
                            // update lastTransaction
                            updatedTrans.add(mergeTransaction(
                                    code, sp.getPeriod(), lastTransaction, streamTrans));
                        } else {
                            if (!(streamTrans.getBaseVolume() instanceof java.lang.Double)) {
                                streamTrans.setBaseVolume(0.0); // new record, however, we set baseVolume to zero for
                                // accumulate volume
                            }
                            transList.add(streamTrans);
                            updatedTrans.add(streamTrans);
                        }
                    } else if (getPeriod() == "1D") {
                        // day
                        if (streamTrans.getGroupTimestamp() == lastTransaction.getGroupTimestamp()) {
                            updatedTrans.add(mergeTransaction(
                                    code, sp.getPeriod(), lastTransaction, streamTrans));
                        } else {
                            if (!(streamTrans.getBaseVolume() instanceof java.lang.Double)) {
                                streamTrans.setBaseVolume(0.0); // new record, however, we set baseVolume to zero for
                                // accumulate volume
                            }
                            transList.add(streamTrans);
                            updatedTrans.add(streamTrans);
                        }
                    } else {
                        // minutes
                        if (streamTrans.getGroupTimestamp() == lastTransaction.getGroupTimestamp()) {
                            updatedTrans.add(mergeTransaction(
                                    code, sp.getPeriod(), lastTransaction, streamTrans));
                        } else {
                            if (!(streamTrans.getBaseVolume() instanceof java.lang.Double)) {
                                streamTrans.setBaseVolume(0.0); // new record, however, we set baseVolume to zero for
                                // accumulate volume
                            }
                            transList.add(streamTrans);
                            updatedTrans.add(streamTrans);
                        }
                    }
                }
                _streamTransListCache.put(code, new ArrayList<Transaction>());
            }

            List<Integer> removeSnapshotSubscriptionList = new ArrayList<Integer>();
            List<Subscription> notifyUpdateList = chartData.getSubscription();
            if (dataState.getFirstNotify()) {
                log.info("chart notify first n= ${transList.length}  => ${transList}");
                dataState.setFirstNotify(false);
                chartNssData.setReady(true);
                chartData.setCode(code);
                List<Integer> removeList = new ArrayList<Integer>();
                for (int i = 0; i < notifyUpdateList.size(); i++) {
                    if (notifyUpdateList.get(i) == null) {
                        continue;
                    }

                    Subscription subscription = notifyUpdateList.get(i);
                    QuoteData fullChartData = new QuoteData(0, new NssData(new ArrayList<Transaction>(transList)));
                    NssData fullNssData = fullChartData.getNssData();
                    fullChartData.setCode(code);
                    fullNssData.setName(_period);
                    fullNssData.setSnapshot(snapshot);
                    fullNssData.setFieldID(paramHash);
                    fullNssData.setReady(true);
                    subscription.getSubsciber().informUpdate(Arrays.asList(fullChartData));
                    if (subscription.isSnapshot()) {
                        removeSnapshotSubscriptionList.add(i);
                    }
                }
                Collections.reverse(removeList);
                removeSnapshotSubscriptionList = new ArrayList<Integer>(removeList);
            } else {
                List<Integer> removeList = new ArrayList<Integer>();
                for (int i = 0; i < notifyUpdateList.size(); i++) {
                    if (notifyUpdateList.get(i) == null) {
                        continue;
                    }

                    Subscription subscription = notifyUpdateList.get(i);
                    QuoteData onlyUpdatedChartData = new QuoteData(0,
                            new NssData(new ArrayList<Transaction>(updatedTrans)));
                    NssData onlyUpdateNssData = onlyUpdatedChartData.getNssData();
                    onlyUpdatedChartData.setCode(code);
                    onlyUpdateNssData.setName(_period);
                    onlyUpdateNssData.setSnapshot(snapshot);
                    onlyUpdateNssData.setFieldID(paramHash);
                    onlyUpdateNssData.setReady(true);
                    subscription.getSubsciber().informUpdate(Arrays.asList(onlyUpdatedChartData));
                    if (subscription.isSnapshot()) {
                        removeList.add(i);
                    }
                }
                Collections.reverse(removeList);
                removeSnapshotSubscriptionList = new ArrayList<Integer>(removeList);
            }
            // removeList contain indexes in decending order
            for (Integer removeIndex : removeSnapshotSubscriptionList) {
                Subscription deletedSubscription = notifyUpdateList.get(removeIndex);
                notifyUpdateList.remove(removeIndex);
                log.info("remove subscription: " + deletedSubscription);
            }
        }
    }

    @Override
    public void onDataUpdated(Object data) {
        if (_onChartDataReceived != null) {
            _onChartDataReceived.onChartDataReceived((List<QuoteData>) data);
        }
    }
}

