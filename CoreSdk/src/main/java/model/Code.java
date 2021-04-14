package model;

public class Code {
    String _code;
    String _underlying;

    public String getCode() {
        return this._code;
    }

    public void setCode(String value) {
        this._code = value;
    }

    public String getUnderlying() {
        return this._underlying;
    }

    public void setUnderlying(String value) {
        this._underlying = value;
    }

    public String _toString() {
        String codes = "";
        if (this._code != null) {
            codes += this._code;
        }
        if (_underlying != null) {
            codes += "$" + this._underlying;
        }
        return codes;
    }
}
