package coreModel;

public class NssPacket {
    int _length = 0;
    int _reqId = 0;
    int _seqNo = 0;
    int _size = 0;

    String _body = "";
    String _raw = "";

    Object _parsedBody;

    public NssPacket(int length, int reqId, int seqNo, int size, String body,
                     String raw) {
        this._length = length;
        this._reqId = reqId;
        this._seqNo = seqNo;
        this._size = size;
        this._body = body;
        this._raw = raw;
    }

    public int getSeqNo() {
        return this._seqNo;
    }

    public int getReqId() {
        return this._reqId;

    }

    public int getLength() {
        return this._length;
    }

    public int getSize() {
        return this._size;

    }

    public String getBody() {
        return this._body;
    }

    public String getRaw() {
        return this._raw;
    }

    public Object getParsedBody() {
        return this._parsedBody;
    }

    public void setParsedBody(Object body) {
        this._parsedBody = body;
    }


}
