package coreModel;

public class QuoteData extends DataSubscription {
    String _code;
    NssData _nssData;

    public QuoteData(int seqNo, NssData nssData) {
        super(seqNo);
        if (nssData != null) {
            setNssData(nssData);
        }
    }

    public String getCode() {
        return this._code;
    }

    public void setCode(String code) {
        this._code = code;
    }

    public NssData getNssData() {
        return this._nssData;
    }

    public void setNssData(NssData nssData) {
        this._nssData = nssData;
    }
}
