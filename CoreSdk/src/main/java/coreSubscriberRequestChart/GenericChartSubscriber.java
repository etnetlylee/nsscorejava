package coreSubscriberRequestChart;

public class GenericChartSubscriber extends QuoteSubscriber {
    String _period;
    boolean _tradingDayOnly;
    boolean _isSnapshot;
    int _range;

    GenericChartSubscriber(String name) : super(name);

    String getPeriod() {
        return _period;
    }

    void setPeriod(String period) {
        _period = period;
    }

    void setTradingDayOnly(bool tradingDayOnly) {
        _tradingDayOnly = tradingDayOnly;
    }

    bool isTradingDayOnly() {
        return _tradingDayOnly;
    }

    void setRange(int limit) {
        _range = limit;
    }

    int getRange() {
        return _range;
    }

    void setSnapshot(bool snapshot) {
        _isSnapshot = snapshot;
    }

    bool getSnapshot() {
        return _isSnapshot;
    }
}
