package coreController;

import java.util.HashMap;
import java.util.Map;

import coreModel.DataState;

public class ChartListenController {
    Map<String, DataState> _dataStateMap = new HashMap<String, DataState>();

    public DataState getDataState(String code) {
        return this._dataStateMap.get(code);
    }

    public Object addDataState(String key, DataState state) {
        return _dataStateMap.put(key, state);
    }

    public void increaseDataStateCount(String key) {
        if (_dataStateMap.containsKey(key)) {
            int _tempCount = _dataStateMap.get(key).getCount();
            _dataStateMap.get(key).setCount(_tempCount + 1);
        }
    }

    public boolean hasDataState(String key) {
        return _dataStateMap.containsKey(key);
    }

    public DataState deleteDataState(String key) {
        if (_dataStateMap.containsKey(key)) {
            DataState dataState = _dataStateMap.get(key);
            int _a = dataState.getCount() - 1;
            dataState.setCount(_a);
            if (dataState.getCount() == 0) {
                return _dataStateMap.remove(key);
            } else {
                return dataState;
            }
        } else {
            return null;
        }
    }

    public void clear() {
        _dataStateMap.clear();
    }
}
