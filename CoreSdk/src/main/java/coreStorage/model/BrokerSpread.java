package coreStorage.model;

import java.util.List;
import java.util.Map;

public class BrokerSpread {
    String _spreadNo;
    List<String> _queueBrokers;
    Map<String, BrokerInfo> _brokerMap;

    public BrokerSpread(String spreadNo, List<String> queueBrokers,
                 Map<String, BrokerInfo> brokerMap) {
        this._spreadNo = spreadNo;
        this._queueBrokers = queueBrokers;
        this._brokerMap = brokerMap;
    }

    public String getSpreadNo(){
        return this._spreadNo;
    }

    public List<String> getQueueBrokers(){
        return this._queueBrokers;
    }

    public Map<String, BrokerInfo> getBrokerMap(){
        return this._brokerMap;
    }
}
