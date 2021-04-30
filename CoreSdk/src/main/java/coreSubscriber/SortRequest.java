package coreSubscriber;

import java.util.List;

import coreModel.SortOrder;
import coreSubscriber.request.Request;

import static constants.RequestType.TYPE_SORT;

public class SortRequest extends Request {
    int _seqNo;
    String _sortCode;
    List<String> _sortFields;

    String _order;
    int _startIndex;
    int _numReocrds;
    SortOrder _sortOrder; // format is field: sort
    List<String> _filters;

    public SortRequest(int commandId, String sortCode, List<String> sortFields) {
        super(TYPE_SORT, commandId, -1);
        this._sortCode = sortCode;
        this._sortFields = sortFields;
    }

    public String getSortCode() {
        return this._sortCode;
    }

    public List<String> getSortFields() {
        return this._sortFields;
    }

    public String getOrder() {
        return this._order;
    }

    public void setOrder(String order) {
        this._order = order;
    }

    public int getStartIndex() {
        return this._startIndex;
    }

    public void setStartIndex(int startIndex) {
        this._startIndex = startIndex;
    }

    public int getNumRecords() {
        return this._numReocrds;
    }

    public void setNumRecords(int numReocrds) {
        this._numReocrds = numReocrds;
    }

    public SortOrder getSortOrder() {
        return this._sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this._sortOrder = sortOrder;
    }

    public List<String> getFilters() {
        return this._filters;
    }

    public void setFilters(List<String> filters) {
        this._filters = filters;
    }

    @Override
    public void subscribe(SubscriberJava subscriberJava) {
        this._seqNo = getNssCoreContext()
                .getController()
                .getCommandController()
                .sendAddSortCommand(
                        getRequestId(),
                        -1,
                        getCommandId(),
                        _sortCode,
                        _sortFields,
                        _order,
                        _startIndex,
                        _numReocrds,
                        _sortOrder,
                        _filters);
        setSequenceNo(_seqNo);
        getSubscriberController().addSortSubscriber(_seqNo, subscriberJava);
    }

    @Override
    public void unsubscribe(SubscriberJava subscriberJava) {
        if (getSequenceNo() != -1) {
            getSubscriberController().removeSortSubscriber(_seqNo, subscriberJava);
            getNssCoreContext()
                    .getController()
                    .getCommandController()
                    .sendRemoveSortCommand(
                            getRequestId(), getSequenceNo(), getSortFields());
            setSequenceNo(-1);
        }
    }
}
