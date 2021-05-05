package com.etnet.coresdk.coreModel;

import java.util.List;

public class ListChanges {
    List<String> _newList;
    List<String> _oldList;
    List<ListItemChanges> _ord;

    public ListChanges(List newList, List oldList, List<ListItemChanges> ord) {
        this._newList = newList;
        this._oldList = oldList;
        this._ord = ord;
    }

    public List<String> getNewList() {
        return this._newList;
    }

    public List<String> getOldList() {
        return this._oldList;
    }

    public List<ListItemChanges> getOrd() {
        return this._ord;
    }
}
