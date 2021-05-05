package com.etnet.coresdk.coreSubscriberRequestChart;

import com.etnet.coresdk.coreSubscriber.QuoteSubscriber;

public class GenericChartSubscriber extends QuoteSubscriber {
    String _period;
    boolean _tradingDayOnly;
    boolean _isSnapshot;
    int _range;

    public GenericChartSubscriber(String name){
        super(name);
    };

    public String getPeriod() {
        return this._period;
    }

    public void setPeriod(String period) {
        this._period = period;
    }

    public void setTradingDayOnly(boolean tradingDayOnly) {
        this._tradingDayOnly = tradingDayOnly;
    }

    public boolean isTradingDayOnly() {
        return this._tradingDayOnly;
    }

    public void setRange(int limit) {
        this._range = limit;
    }

    public int getRange() {
        return this._range;
    }

    public void setSnapshot(boolean snapshot) {
        this._isSnapshot = snapshot;
    }

    public boolean getSnapshot() {
        return this._isSnapshot;
    }
}
