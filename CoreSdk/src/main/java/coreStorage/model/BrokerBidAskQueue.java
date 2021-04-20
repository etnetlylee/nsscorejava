package coreStorage.model;

import java.util.ArrayList;
import java.util.List;

public class BrokerBidAskQueue {
    List<BrokerSpread> _bidBrokersQueue;
    List<BrokerSpread> _askBrokersQueue;

    public BrokerBidAskQueue() {
        _bidBrokersQueue = new ArrayList<BrokerSpread>();
        _askBrokersQueue = new ArrayList<BrokerSpread>();
    }

    public List<BrokerSpread> getBidBrokersQueue(){
        return this._bidBrokersQueue;
    }

    public void setBidBrokersQueue(List<BrokerSpread> bidBrokersQueue){
      this._bidBrokersQueue = bidBrokersQueue;
    }

    public List<BrokerSpread> getAskBrokersQueue(){
        return this._askBrokersQueue;
    }

    public void setAskBrokersQueue(List<BrokerSpread> askBrokersQueue){
        this._askBrokersQueue = askBrokersQueue;
    }
}
