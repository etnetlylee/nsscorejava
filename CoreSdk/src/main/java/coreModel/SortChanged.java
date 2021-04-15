package coreModel;

public class SortChanged {
    String _value;
    int _from;
    int _to;

    public SortChanged(String value, int from, int to) {
        this._value = value;
        this._from = from;
        this._to = to;
    }

    public int getFrom() {
        return this._from;
    }

    public int getTo() {
        return this._to;
    }

    public String getValue() {
        return this._value;
    }
}
