package com.etnet.coresdk.coreSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.etnet.coresdk.api.CommonSubscriber;
import com.etnet.coresdk.api.OnQuoteDataReceived;
import com.etnet.coresdk.coreEnvironment.Environment;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreSubscriber.request.QuoteRequest;

public class QuoteSubscriber extends CommonSubscriber {
    final Logger log = Logger.getLogger("QuoteScriber");
    NssCoreContext _nssCoreContext;

    SubscriberJava _subscriberJava;
    boolean _isSnapshot = false;
    boolean _isBroadcast = false;

    List<String> _currentCodeList = new ArrayList<String>();
    List<String> _currentFieldList = new ArrayList<String>();

    List<String> _willProcessCodeList = new ArrayList<String>();
    List<String> _willProcessFieldList = new ArrayList<String>();

    static OnQuoteDataReceived _onQuoteDataReceived;

    public QuoteSubscriber(String name) {
        this._subscriberJava = new SubscriberJava(name, this);
    }

    public NssCoreContext getContext() {
        return this._nssCoreContext;
    }

    @Override
    public void setContext(NssCoreContext context) {
        this._nssCoreContext = context;
        this._subscriberJava.setNssCoreContext(context);
    }

    public List<String> currentCodes() {
        return this._currentCodeList;
    }

    public List<String> currentFields() {
        return this._currentFieldList;
    }

    public List<String> willProcessCodes() {
        return this._willProcessCodeList;
    }

    public List<String> willProcessFields() {
        return this._willProcessFieldList;
    }

    public void setOnQuoteDataReceived(OnQuoteDataReceived onQuoteDataReceived) {
        _onQuoteDataReceived = onQuoteDataReceived;
    }

    public SubscriberJava getSubscriber() {
        return this._subscriberJava;
    }

    public QuoteSubscriber clear() {
        this._willProcessCodeList.clear();
        this._willProcessFieldList.clear();
        return this;
    }

    public QuoteSubscriber codes(List<String> codes) {
        // TODO examine any use case for reseting list
        // assert(_willProcessCodeList.length == 0);
        for (String code : codes) {
            if (code.trim().length() > -1 && !_willProcessCodeList.contains(code)) {
                _willProcessCodeList.add(code);
            }
        }
        return this;
    }

    public QuoteSubscriber fields(List<String> fields) {
        // TODO examine any use case for reseting list
        // assert(_willProcessFieldList.length == 0);
        for (String field : fields) {
            if (field.trim().length() > -1 && !_willProcessFieldList.contains(field)) {
                _willProcessCodeList.add(field);
            }
        }
        return this;
    }

    public QuoteSubscriber snapshot(boolean snapshot) {
        this._isSnapshot = snapshot;
        return this;
    }

    public QuoteSubscriber broadcast(boolean broadcast) {
        this._isBroadcast = broadcast;
        return this;
    }

    @Override
    public void subscribe() {
        List<String> willAddCodes = new ArrayList<String>();
        List<String> willAddFields = new ArrayList<String>();

        final boolean snapshot = _isSnapshot;
        final boolean broadcast = _isBroadcast;
        if (_willProcessCodeList != null && _willProcessCodeList.size() > 0 && _willProcessFieldList.size() > 0) {
            // add/remove both code and fields
            willAddCodes = new ArrayList<String>(_willProcessCodeList);
            willAddFields = new ArrayList<String>(_willProcessFieldList);
            final List<String> willSubscribeCodes = new ArrayList<String>();
            final List<String> willSubscribeFields = new ArrayList<String>();
            for (String code : willAddCodes) {
                if (!_currentCodeList.contains(code)) {
                    willSubscribeCodes.add(code);
                }
            }
            for (String fieldID : willAddFields) {
                if (!_currentFieldList.contains(fieldID)) {
                    willSubscribeFields.add(fieldID);
                }
            }

            if (willSubscribeFields != null && willSubscribeFields.size() == 0) {
                // subscribe same fields as previous codes
                createAndSubscribe(
                        willSubscribeCodes, _currentFieldList, snapshot, broadcast);
            } else {
                createAndSubscribe(
                        willSubscribeCodes, willSubscribeFields, snapshot, broadcast);
            }
            _currentCodeList.addAll(willSubscribeCodes);
            _currentFieldList.addAll(willSubscribeFields);
        } else if (_willProcessCodeList != null && _willProcessCodeList.size() > 0) {
            // add/remove code
            willAddCodes = new ArrayList<String>(_willProcessCodeList);
            willAddFields = new ArrayList<String>(_currentFieldList);
            final List<String> willSubscribeCodes = new ArrayList<String>();
            for (String code : willAddCodes) {
                if (!_currentCodeList.contains(code)) {
                    willSubscribeCodes.add(code);
                }
            }
            createAndSubscribe(
                    willSubscribeCodes, willAddFields, snapshot, broadcast);
            _currentCodeList.addAll(willSubscribeCodes);
        } else if (_willProcessCodeList != null && _willProcessFieldList.size() > 0) {
            // add/remove field
            willAddCodes = new ArrayList<String>(_currentCodeList);
            willAddFields = new ArrayList<String>(_willProcessFieldList);

            final List<String> willSubscribeFields = new ArrayList<String>();
            for (String fieldID : willAddFields) {
                if (!_currentFieldList.contains(fieldID)) {
                    willSubscribeFields.add(fieldID);
                }
            }
            createAndSubscribe(
                    willAddCodes, willSubscribeFields, snapshot, broadcast);
            _currentFieldList.addAll(willSubscribeFields);
        }
        _willProcessCodeList = new ArrayList<String>();
        _willProcessFieldList = new ArrayList<String>();
    }

    public void createAndSubscribe(List<String> willAddCodes, List<String> willAddFields,
                                   boolean isSnapshot, boolean isBroadcast) {
        log.info("subscribe code: " + willAddCodes.toString());
        log.info("subscribe fields: " + willAddFields.toString());

        if (willAddCodes != null && willAddCodes.size() > 0 && willAddFields.size() > 0) {
            if (isSnapshot) {
                final QuoteRequest snapQuoteRequest = _nssCoreContext
                        .getController()
                        .getRequestController()
                        .createSnapshotQuoteRequest(willAddCodes, willAddFields);
                _subscriberJava.subscribe(snapQuoteRequest);
            } else {
                if (isBroadcast) {
                    final QuoteRequest streamQuoteRequest = _nssCoreContext
                            .getController()
                            .getRequestController()
                            .createBroadcastQuoteRequest(willAddCodes, willAddFields);
                    _subscriberJava.subscribe(streamQuoteRequest);
                } else {
                    final QuoteRequest streamQuoteRequest = _nssCoreContext
                            .getController()
                            .getRequestController()
                            .createStreamQuoteRequest(willAddCodes, willAddFields);
                    _subscriberJava.subscribe(streamQuoteRequest);
                }
            }
        }
    }

    public void createAndUnsubscribe(
            List<String> willRemoveCodes, List<String> willRemoveFields) {
        if (willRemoveCodes != null && willRemoveCodes.size() > 0 && willRemoveFields.size() > 0) {
            final QuoteRequest quoteRequest = _nssCoreContext
                    .getController()
                    .getRequestController()
                    .createRemoveQuoteRequest(willRemoveCodes, willRemoveFields);
            _subscriberJava.unsubscribe(quoteRequest);
        }
    }

    @Override
    public void unsubscribe() {
        List<String> willRemoveCodes = new ArrayList<String>();
        List<String> willRemoveFields = new ArrayList<String>();

        if (_willProcessCodeList != null && _willProcessCodeList.size() > 0 && _willProcessFieldList.size() > 0) {
            // remove code and field

            willRemoveCodes = new ArrayList<String>(_willProcessCodeList);
            willRemoveFields = new ArrayList<String>(_willProcessFieldList);

            final List<String> willUnsubscribeCodes = new ArrayList<String>();
            final List<String> willUnsubscribeFields = new ArrayList<String>();

            for (String code : willRemoveCodes) {
                if (_currentCodeList.contains(code)) {
                    willUnsubscribeCodes.add(code);
                }
            }

            for (String fieldID : willRemoveFields) {
                if (_currentFieldList.contains(fieldID)) {
                    willUnsubscribeFields.add(fieldID);
                }
            }

            createAndUnsubscribe(willUnsubscribeCodes, willUnsubscribeFields);
            if (willUnsubscribeFields != null && willUnsubscribeFields.size() == _currentFieldList.size()) {
                // all fields are remove, we also remove those codes
                List<String> _temp = new ArrayList<String>();
                for (String code : _currentCodeList) {
                    if (!willUnsubscribeCodes.contains(code)) {
                        _temp.add(code);
                    }
                }
                _currentCodeList.clear();
                _currentCodeList.addAll(_temp);
            }
            if (_currentCodeList != null && _currentCodeList.size() == 0) {
                _currentFieldList = new ArrayList<String>();
            }
        } else if (_willProcessCodeList != null && _willProcessCodeList.size() > 0) {
            // remove code
            willRemoveCodes = new ArrayList<String>(_willProcessCodeList);
            willRemoveFields = new ArrayList<String>(_currentFieldList);
            final List<String> willUnsubscribeCodes = new ArrayList<String>();

            for (String code : willRemoveCodes) {
                if (_currentCodeList.contains(code)) {
                    willUnsubscribeCodes.add(code);
                }
            }

            createAndUnsubscribe(willUnsubscribeCodes, willRemoveFields);
            List<String> _temp = new ArrayList<String>(_currentCodeList);
            for (String v : _currentCodeList) {
                if (willUnsubscribeCodes.contains(v)) {
                    _temp.remove(v);
                }
            }
            _currentCodeList.clear();
            _currentCodeList.addAll(_temp);

            // _currentCodeList = _currentCodeList
            //     .where((v) => !willUnsubscribeCodes.contains(v))
            //     .toList();
            if (_currentCodeList != null && _currentCodeList.size() == 0) {
                _currentFieldList = new ArrayList<String>();
            }
        } else if (_willProcessFieldList != null && _willProcessFieldList.size() > 0) {
            // remove field
            willRemoveCodes = new ArrayList<String>(_currentCodeList);
            willRemoveFields = new ArrayList<String>(_willProcessFieldList);
            final List<String> willUnsubscribeFields = new ArrayList<String>();

            for (String fieldID : willRemoveFields) {
              if (_currentFieldList.contains(fieldID)){
                  willUnsubscribeFields.add(fieldID);
              }
            }

            createAndUnsubscribe(willRemoveCodes, willUnsubscribeFields);
            List<String> _temp = new ArrayList<String>(_currentFieldList);
            for (String v : _currentFieldList){
                if (willUnsubscribeFields.contains(v)){
                    _temp.remove(v);
                }            }
            _currentFieldList.clear();
            _currentFieldList.addAll(_temp);

            // _currentFieldList = _currentFieldList
            //     .where((v) => !willUnsubscribeFields.contains(v))
            //     .toList();
            if (_currentFieldList != null && _currentFieldList.size() == 0) {
                _currentCodeList = new ArrayList<String>();
            }
        } else {
            // all
            willRemoveCodes = new ArrayList<String>(_currentCodeList);
            willRemoveFields = new ArrayList<String>(_currentFieldList);

            if (willRemoveCodes != null && willRemoveCodes.size() > 0 && willRemoveFields.size() > 0) {
                final QuoteRequest quoteRequest = _nssCoreContext
                        .getController()
                        .getRequestController()
                        .createRemoveQuoteRequest(willRemoveCodes, willRemoveFields);
                // Single quote request to remove all current code-field in this subscriber
                if (Environment.isDebug()) {
                    log.info("unsubscribe " +
                            quoteRequest.getSerializedCode() +
                            " = " +
                            quoteRequest.getSerializedField());
                }
                _subscriberJava.unsubscribe(quoteRequest);
            } else {
                log.info("unsubscribe nothing as codes and fields are empty");
            }
            // all must be removed as storage will not has such qr anymore.
            _currentCodeList = new ArrayList<String>();
            _currentFieldList = new ArrayList<String>();
        }

        _willProcessCodeList = new ArrayList<String>();
        _willProcessFieldList = new ArrayList<String>();
    }

    @Override
    public void resubscribe() {

        List<String> tempCodeList = new ArrayList<String>(_currentCodeList);
        List<String> tempFieldList = new ArrayList<String>(_currentFieldList);

        clear();
        unsubscribe();

        codes(tempCodeList);
        fields(tempFieldList);

        subscribe();
    }

    @Override
    public void onDataUpdated(Object data) {
        if (_onQuoteDataReceived != null) {
            _onQuoteDataReceived.onQuoteDataReceived((List<QuoteData>) data);
        }
    }
}
