package com.etnet.coresdk.coreModel;

import java.util.ArrayList;
import java.util.List;

import static com.etnet.coresdk.constants.Order.SORT_ASC;
import static com.etnet.coresdk.constants.Order.SORT_DESC;

public class SortOrder {
    List<SortField> _sortFieldMap = new ArrayList<SortField>();

    public void addSortField(String fieldId, String order) {
        final SortField sortField = new SortField();
        sortField.setFieldId(fieldId);
        sortField.setOrder(order);
        _sortFieldMap.add(sortField);
    }

    // Reduce complicated of front-end
    public void addAscSortField(String fieldId) {
        addSortField(fieldId, SORT_ASC);
    }

    // Reduce complicated of front-end
    public void addDescSortField(String fieldId) {
        addSortField(fieldId, SORT_DESC);
    }

    public void setSortField(int index, SortField sortField) {
        _sortFieldMap.set(index, sortField);
    }

    public SortField getSortField(int index) {
        return _sortFieldMap.get(index);
    }

    public  List<String> getAllSortFieldIds() {
        final List<String> fieldIds = new ArrayList();
        for (SortField sortField : _sortFieldMap) {
            fieldIds.add(sortField.getFieldId());
        }
        return fieldIds;
    }

    public void clear() {
        _sortFieldMap = new ArrayList<SortField>();
    }

    public List<SortField> getSortFieldList() {
        return _sortFieldMap;
    }
}
