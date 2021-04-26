package coreSubscriber;

import java.util.List;

import coreModel.NssCoreContext;
import coreModel.QuoteData;
import coreSubscriber.listener.UpdateListener;
import coreSubscriber.request.Request;

public class Subscriber {
    String _viewId;
    NssCoreContext _nssCoreContext;
    UpdateListener _updateListener;
    List<String> _codes;
    List<String> _fields;
    boolean _internal;

    public Subscriber(String viewId, UpdateListener updateListener) {
        _viewId = viewId;
        _updateListener = updateListener;
    }

    public String getViewId() {
        return this._viewId;
    }

    public void subscribe(Request request) {
        request.setSubscriber(this);
        request.subscribe(this);
    }

    public void unsubscribe(Request request) {
        request.setSubscriber(this);
        request.unsubscribe(this);
    }

    public void informUpdate(List<QuoteData> data) {
        _updateListener.onDataUpdated(data);
    }

    public void setNssCoreContext(NssCoreContext nssCoreContext) {
        this._nssCoreContext = nssCoreContext;
    }

    public NssCoreContext getNssCoreContext() {
        return this._nssCoreContext;
    }

    public void setInternal(boolean internal) {
        this._internal = internal;
    }

    public boolean isInternal() {
        return this._internal;
    }

    public void setViewId(String viewId) {
        this._viewId = viewId;
    }

    public List<String> getCodes() {
        return this._codes;
    }

    public void setCodes(List<String> codes) {
        this._codes = codes;
    }

    public List<String> getFields() {
        return this._fields;
    }

    public void setFields(List<String> fields) {
        this._fields = fields;
    }
}

class DateListener {
}