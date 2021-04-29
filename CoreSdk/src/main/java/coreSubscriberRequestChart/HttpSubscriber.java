package coreSubscriberRequestChart;

import java.util.List;
import java.util.Map;

import api.CommonSubscriber;
import api.OnQuoteDataReceived;
import coreModel.NssCoreContext;
import coreModel.QuoteData;
import coreSubscriber.Subscriber;
import coreSubscriber.request.HttpRequest;

public class HttpSubscriber extends CommonSubscriber {
    NssCoreContext _nssCoreContext;

    Subscriber _subscriber;
    String _code;
    String _fieldID;
    String _apiType;
    Map<String, String> _params;
    String _endPoint;

    OnQuoteDataReceived _onQuoteDataReceived;

    public HttpSubscriber(String name) {
        _subscriber = new Subscriber(name, this);
    }

    public NssCoreContext getContext() {
        return this._nssCoreContext;
    }

    @Override
    public void setContext(NssCoreContext context) {
        this._nssCoreContext = context;
        this._subscriber.setNssCoreContext(context);
    }

    public void setEndPoint(String endPoint) {
        this._endPoint = endPoint;
    }

    public void setParams(Map<String, String> params) {
        this._params = params;
    }

    public void setCode(String code) {
        this._code = code;
    }

    public void setFieldID(String fieldID) {
        this._fieldID = fieldID;
    }

    public void setOnQuoteDataReceived(OnQuoteDataReceived onQuoteDataReceived) {
        this._onQuoteDataReceived = onQuoteDataReceived;
    }

    @Override
    public void subscribe() {
        HttpRequest httpRequest =
                new HttpRequest(_endPoint, _code, _fieldID, _params);
        httpRequest.setNssCoreContext(_nssCoreContext);
        this._subscriber.subscribe(httpRequest);
    }

    @Override
    public void unsubscribe() {
        HttpRequest httpRequest =
                new HttpRequest(_endPoint, _code, _fieldID, _params);
        httpRequest.setNssCoreContext(_nssCoreContext);
        this._subscriber.unsubscribe(httpRequest);
    }

    @Override
    public void resubscribe() {
        unsubscribe();
        subscribe();
    }

    @Override
    public void onDataUpdated(Object data) {
        if (_onQuoteDataReceived != null) {
            _onQuoteDataReceived.onQuoteDataReceived((List<QuoteData>) data);
        }
    }
}
