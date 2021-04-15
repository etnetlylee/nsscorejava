package coreModel;

import java.util.ArrayList;
import java.util.List;

public class SortCodes {
    List<String> _codeList = new ArrayList();
    List<String> _addList = new ArrayList();
    List<String> _removeList = new ArrayList();
    List<SortChanged> _changedList = new ArrayList<SortChanged>();

    public SortCodes(List<String> codeList,
                     List<String> addList,
                     List<String> removeList,
                     List<SortChanged> changedList) {
        this._codeList = codeList;
        this._addList = addList;
        this._removeList = removeList;
        this._changedList = changedList;
    }

    public SortCodes(List<String> codeList) {
        this._codeList = codeList;
    }

    public List<String> getCodeList() {
        return this._codeList;
    }

    public List<String> getAddList() {
        return this._addList;
    }

    public List<String> getRemoveList() {
        return this._removeList;
    }

    public List<SortChanged> getChangedList() {
        return this._changedList;
    }
}
