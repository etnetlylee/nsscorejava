package com.etnet.coresdk.coreSubscriber.request;

import com.etnet.coresdk.coreController.SubscriberController;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreSubscriber.SubscriberJava;

public class Request {
    int _requestId;
    int _commandId;
    int _sequenceNo;

    NssCoreContext _nssCoreContext;
    SubscriberJava _subscriberJava;

    public Request(Integer requestId, Integer commandId, Integer sequenceNo) {
        this._requestId = requestId;
        this._commandId = commandId;
        this._sequenceNo = sequenceNo;
    }

    public int getRequestId() {
        return this._requestId;
    }

    public void setRequestId(int requestId) {
        this._requestId = requestId;
    }

    public int getCommandId() {
        return this._commandId;
    }

    public void setCommandId(int commandId) {
        this._commandId = commandId;
    }

    public int getSequenceNo() {
        return this._sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this._sequenceNo = sequenceNo;
    }

    public SubscriberJava getSubscriber() {
        return this._subscriberJava;
    }

    public void setSubscriber(SubscriberJava subscriberJava) {
        this._subscriberJava = subscriberJava;
    }

    public void subscribe(SubscriberJava subscriberJava){};

    public void unsubscribe(SubscriberJava subscriberJava){};

    public SubscriberController getSubscriberController() {
        return this._nssCoreContext.getController().getSubscriberController();
    }

    public NssCoreContext getNssCoreContext() {
        return this._nssCoreContext;
    }

    public void setNssCoreContext(NssCoreContext nssCoreContext) {
        this._nssCoreContext = nssCoreContext;
    }
}
