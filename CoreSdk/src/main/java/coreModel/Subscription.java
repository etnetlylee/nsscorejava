package coreModel;

import coreSubscriber.SubscriberJava;

public class Subscription {
    boolean _isSnapshot;
    SubscriberJava _subscriberJava;

    public Subscription(boolean isSnapshot, SubscriberJava subscriberJava) {
        this._isSnapshot = isSnapshot;
        this._subscriberJava = subscriberJava;
    }

    public SubscriberJava getSubsciber() {
        return this._subscriberJava;
    }

    public boolean isSnapshot() {
        return this._isSnapshot;
    }
}
