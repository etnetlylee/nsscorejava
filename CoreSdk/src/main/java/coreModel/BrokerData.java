package coreModel;

public class BrokerData extends DataSubscription{
    BrokerData(int seqNo, NssData nssData) {
        super(seqNo);
        _nssData = nssData;
    }
    public NssData _nssData;


    public NssData getNssData() {
        return this._nssData;
    }

    public void setNssData(NssData nssData) {
        _nssData = nssData;
    }
}
