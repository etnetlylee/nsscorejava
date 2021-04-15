package coreModel;

public class BrokerData extends DataSubscription {
    BrokerData(int seqNo) {
        super(seqNo);
    }

    public NssData _nssData;


    public NssData getNssData() {
        return this._nssData;
    }

    public void setNssData(NssData nssData) {
        this._nssData = nssData;
    }
}
