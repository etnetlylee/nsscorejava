package com.etnet.coresdk.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
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
        List<String> _tempList = new ArrayList<String>(_strings);;
        if (value != null) {
            if (_strings.size() > 0) {
                _tempList.add(",");
            }
            _tempList.add(value);
        } else {
            _tempList.add(",");
        }
        _strings = new ArrayList<>(_tempList);
        return this;
    }

    public String toString() {
        return StringUtils.join(_strings, "") + "\n";
    }
}
