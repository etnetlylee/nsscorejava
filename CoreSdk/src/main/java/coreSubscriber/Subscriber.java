package coreSubscriber;

import java.util.List;

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

    public String getViewId(){
        return  this._viewId;
    }

    public void subscribe(Request request) {
        request.setSubscriber(this);
        request.subscribe(this);
    }

    void unsubscribe(Request request) {
        request.setSubscriber(this);
        request.unsubscribe(this);
    }

    void informUpdate(dynamic data) {
        _updateListener.onDataUpdated(data);
    }

    set nssCoreContext(NssCoreContext nssCoreContext) => _nssCoreContext = nssCoreContext;
    NssCoreContext get nssCoreContext => _nssCoreContext;

    void setInternal(bool internal) {
        _internal = internal;
    }

    bool isInternal() {
        return _internal;
    }

    void setViewId(String viewId) {
        _viewId = viewId;
    }

    List<String> getCodes() {
        return _codes;
    }

    void setCodes(List<String> codes) {
        _codes = codes;
    }

    List<String> getFields() {
        return _fields;
    }

    void setFields(List<String> fields) {
        _fields = fields;
    }
}

class DateListener {}