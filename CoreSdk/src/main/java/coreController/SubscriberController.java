package coreController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.ContextProvider;
import coreModel.DataState;
import coreModel.NssCoreContext;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.Subscription;
import coreSubscriber.Subscriber;
import coreSubscriberRequestChart.GenericChartSubscriber;

public class SubscriberController extends ContextProvider {
    NssCoreContext _context;
    // NewsListenController _newsListenerController;
    ChartListenController _chartListenController;
    Map<String, GenericChartSubscriber> _chartSubscribers = new HashMap<String, GenericChartSubscriber>();

    SubscriberController() {
        // _newsListenerController = new NewsListenController();
        this._chartListenController = new ChartListenController();
    }

    @Override
    public void setContext(NssCoreContext context) {
        _context = context;
    }

    boolean containsQuoteSubscriber(
            String code, String fieldId, Subscriber subscriber, boolean snap) {
        return this._context
                .getStorage()
                .containsQuoteListener(code, fieldId, subscriber, snap);
    }

    public QuoteData createQuoteData(String code, String fieldId) {
        return this._context.getStorage().addQuoteData(code, fieldId, new NssData(null));
    }

    public QuoteData getQuoteData(String code, String fieldId) {
        return this._context.getStorage().getQuoteData(code, fieldId);
    }

    public List<String> getQuoteFields(String code) {
        return this._context.getStorage().getQuoteFields(code);
    }

    public boolean hasQuoteData(String code, String fieldId) {
        return this._context.getStorage().getQuoteData(code, fieldId) != null;
    }

    public QuoteData addCompositeDeps(String code, String fieldId) {
        final QuoteData quoteData = this._context.getStorage().getQuoteData(code, fieldId);
        quoteData.plusCompositeDepsCount();
        return quoteData;
    }

    public QuoteData removeCompositeDeps(String code, String fieldId) {
        final QuoteData quoteData = this._context.getStorage().getQuoteData(code, fieldId);
        quoteData.minusCompositeDepsCount();
        return quoteData;
    }

    public void addQuoteSubscriber(
            String code, String fieldId, Subscriber subscriber, boolean snap) {
        this._context.getStorage().addQuoteListener(code, fieldId, subscriber, snap);
    }

    public int removeQuoteSubscriber(
            String code, String fieldId, Subscriber subscriber) {
        return this._context.getStorage().removeQuoteListener(code, fieldId, subscriber);
    }

    public void addSortSubscriber(int seqNo, Subscriber subscriber) {
        this._context.getStorage().addSequenceListener(seqNo, subscriber);
    }

    public Subscriber removeSortSubscriber(int seqNo, Subscriber subscriber) {
        return this._context.getStorage().removeSequenceListener(seqNo, subscriber);
    }

    List<Subscriber> getSequenceSubscribers(int seqNo) {
        return this._context.getStorage().getSequenceSubscribers(seqNo);
    }

    public void addChartSubscription(Subscriber subscriber, String code, String paramHash) {
        this._context.getStorage().addChartSubscription(subscriber, code, paramHash);
    }

    public boolean isChartSubscribed(String code, String paramHash) {
        return this._context.getStorage().isChartSubscribed(code, paramHash);
    }

    public boolean hasChartSubscription(
            Subscriber subscriber, String code, String paramHash) {
        return this._context
                .getStorage()
                .hasChartSubscription(subscriber, code, paramHash);
    }

    Subscriber removeChartSubscription(
            Subscriber subscriber, String code, String paramHash) {
        return this._context
                .getStorage()
                .removeChartSubscription(subscriber, code, paramHash);
    }

    public List<Subscription> getChartSubscription(String code, String paramHash) {
        return this._context.getStorage().getChartSubscription(code, paramHash);
    }

    public boolean hasChartSubscriber(String key) {
        return _chartSubscribers.containsKey(key);
    }

    public GenericChartSubscriber getChartSubscriber(String key) {
        return this._chartSubscribers.get(key);
    }

    public void addChartSubscriber(String key, GenericChartSubscriber subscriber) {
       this._chartSubscribers.put(key, subscriber);
    }

    public void removeChartSubscriber(String key, GenericChartSubscriber subscriber) {
        GenericChartSubscriber s = this._chartSubscribers.get(key);
        if (s == subscriber) {
            this._chartSubscribers.remove(key);
        }
    }

    public DataState getChartDataState(String key) {
        return this._chartListenController.getDataState(key);
    }

    public Object addChartDataState(String key, DataState state) {
       return this._chartListenController.addDataState(key, state);
    }

    public void increaseDataStateCount(String key) {
        this._chartListenController.increaseDataStateCount(key);
    }

    public boolean hasChartDataState(String key) {
        return this._chartListenController.hasDataState(key);
    }

    public DataState deleteChartDataState(String key) {
        return this._chartListenController.deleteDataState(key);
    }

    public void clearDataState() {
        this._chartListenController.clear();
    }
}
