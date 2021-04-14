package coreModel;

import coreSubscriber.Subscriber;

public class Subscription {
    boolean _isSnapshot;
    Subscriber _subscriber;

    public Subscription(boolean isSnapshot, Subscriber subscriber) {
        this._isSnapshot = isSnapshot;
        this._subscriber = subscriber;
    }

    Subscriber getSubsciber() {
        return _subscriber;
    }

    boolean isSnapshot() {
        return _isSnapshot;
    }
}
