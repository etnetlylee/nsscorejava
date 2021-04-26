package util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class CommandBuilder {
    List<String> _strings;

    public CommandBuilder(int reqId, int seqNo) {
        _strings =  Arrays.asList("-1", ",", String.valueOf(reqId), ",", String.valueOf(seqNo));
    }

    public CommandBuilder appendChar(String c) {
        if (c != null) {
            this._strings.add(c);
        }
        return this;
    }

    public CommandBuilder append(String value) {
        if (value != null) {
            if (_strings.size() > 0) {
                _strings.add(",");
            }
            _strings.add(value);
        } else {
            _strings.add(",");
        }
        return this;
    }

    public String toString() {
        return StringUtils.join(_strings, "") + "\n";
    }
}
