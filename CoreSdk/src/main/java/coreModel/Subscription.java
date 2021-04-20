package coreModel;

import coreSubscriber.Subscriber;

public class Subscription {
    boolean _isSnapshot;
    Subscriber _subscriber;

    public Subscription(boolean isSnapshot, Subscriber subscriber) {
        this._isSnapshot = isSnapshot;
        this._subscriber = subscriber;
    }

    public Subscriber getSubsciber() {
        return this._subscriber;
    }

    public boolean isSnapshot() {
        return this._isSnapshot;
    }
}
