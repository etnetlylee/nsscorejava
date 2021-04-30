package coreSubscriber.request;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import api.OnDataSubscription;
import constants.Command;
import coreController.SubscriberController;
import coreEnvironment.Environment;
import coreModel.QuoteData;
import coreSubscriber.SubscriberJava;
import util.ChartHelper;

public class ChartRequest extends Request {
    final Logger log = Logger.getLogger("ChartRequest");
    List<String> _codes;
    String _period;
    boolean _tradingDayOnly;
    int _range;
    boolean _snapshot;

    OnDataSubscription _onDataSubscription;

    public ChartRequest(int commandId, List<String> codes, String period,
                        boolean tradingDayOnly, int range) {
        super(null, commandId, null);
        this._codes = codes;
        this._period = period;
        this._range = range;
        this._tradingDayOnly = tradingDayOnly;
        this._snapshot = commandId == Command.COMMAND_ADD_SNAPSHOT;
    }

    public void setOnDataSubscription(OnDataSubscription cb) {
        this._onDataSubscription = cb;
    }

    @Override
    public void subscribe(SubscriberJava subscriberJava) {
        SubscriberController subscriberController =
                getNssCoreContext().getController().getSubscriberController();
        for (String code : _codes){
            String paramHash = ChartHelper.makeClientFieldID(
                    _period, _tradingDayOnly, _range, _snapshot);
            if (subscriberController.isChartSubscribed(code, paramHash)) {
                subscriberController.addChartSubscription(subscriberJava, code, paramHash);
                QuoteData chartData =
                        getNssCoreContext().getStorage().getQuoteData(code, paramHash);
                if (chartData.getNssData().getReady()) {
                    if (Environment.isDebug()) {
                        log.info("chart data is ready, notify the subscriber");
                    }
                    subscriberJava.informUpdate(Arrays.asList(chartData));
                } else {
                    if (Environment.isDebug()) {
                        log.info("chart data is not ready yet, wait *-chart-subscriber");
                    }
                }
            } else {
                subscriberController.addChartSubscription(subscriberJava, code, paramHash);
                // we need to create new subscription as there are no same params subscriber in existing map
                if (_onDataSubscription != null) {
                    _onDataSubscription.onAddSubscription(
                            code, _period, _range, _tradingDayOnly, _snapshot);
                }
            }
        }
    }

    @Override
    public void unsubscribe(SubscriberJava subscriberJava) {
        SubscriberController subscriberController =
                getNssCoreContext().getController().getSubscriberController();
        String paramHash = ChartHelper.makeClientFieldID(
                _period, _tradingDayOnly, _range, _snapshot);
        for (String code : _codes){
            subscriberController.removeChartSubscription(subscriberJava, code, paramHash);

            if (subscriberController.getChartSubscription(code, paramHash).size() ==
                    0) {
                if (_onDataSubscription != null) {
                    _onDataSubscription.onRemoveSubscription(
                            code, _period, _range, _tradingDayOnly, _snapshot);
                }
            }
        }
    }

    public void resubscribe() {
    }
}
