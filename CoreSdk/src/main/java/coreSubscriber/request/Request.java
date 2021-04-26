package coreSubscriber.request;

import coreController.SubscriberController;
import coreModel.NssCoreContext;
import coreSubscriber.Subscriber;

public class Request {
    int _requestId;
    int _commandId;
    int _sequenceNo;

    NssCoreContext _nssCoreContext;
    Subscriber _subscriber;

    public Request(int requestId, int commandId, int sequenceNo) {
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

    public Subscriber getSubscriber() {
        return this._subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this._subscriber = subscriber;
    }

    public void subscribe(Subscriber subscriber){};

    public void unsubscribe(Subscriber subscriber){};

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
