package com.etnet.coresdk.coreController;

import java.util.List;
import java.util.Map;

import com.etnet.coresdk.api.ContextProvider;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreModel.SortOrder;
import com.etnet.coresdk.coreSubscriber.SortRequest;
import com.etnet.coresdk.coreSubscriber.request.ChartRequest;
import com.etnet.coresdk.coreSubscriber.request.HttpRequest;
import com.etnet.coresdk.coreSubscriber.request.QuoteRequest;

import static com.etnet.coresdk.constants.Command.COMMAND_ADD_BOTH;
import static com.etnet.coresdk.constants.Command.COMMAND_ADD_BROADCAST;
import static com.etnet.coresdk.constants.Command.COMMAND_ADD_SNAPSHOT;
import static com.etnet.coresdk.constants.Command.COMMAND_REMOVE;

public class RequestController extends ContextProvider {
    NssCoreContext _context;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    public QuoteRequest createStreamQuoteRequest(
            List<String> codes, List<String> fields) {
        final QuoteRequest request = new QuoteRequest(COMMAND_ADD_BOTH, codes, fields);
        request.setNssCoreContext(this._context);
        return request;
    }

    public QuoteRequest createSnapshotQuoteRequest(
            List<String> codes, List<String> fields) {
        final QuoteRequest request = new QuoteRequest(COMMAND_ADD_SNAPSHOT, codes, fields);
        request.setNssCoreContext(this._context);
        return request;
    }

    public QuoteRequest createBroadcastQuoteRequest(
            List<String> codes, List<String> fields) {
        final QuoteRequest request = new QuoteRequest(COMMAND_ADD_BROADCAST, codes, fields);
        request.setNssCoreContext(this._context);
        return request;
    }

    public QuoteRequest createRemoveQuoteRequest(
            List<String> codes, List<String> fields) {
        final QuoteRequest request = new QuoteRequest(COMMAND_REMOVE, codes, fields);
        request.setNssCoreContext(this._context);
        return request;
    }

    public HttpRequest createHttpRequest(
            String endPoint, String code, String field, Map<String, String> params) {
        HttpRequest request = new HttpRequest(endPoint, code, field, params);
        request.setNssCoreContext(_context);
        return request;
    }

    public SortRequest createStreamSortRequest(int reqId, String code,
                                               List<String> sortFields, String order, int startIndex, int numRecords,
                                               SortOrder sortOrder, List<String> filters) {
        final SortRequest request = new SortRequest(COMMAND_ADD_BOTH, code, sortFields);
        request.setNssCoreContext(_context);
        request.setOrder(order);
        request.setStartIndex(startIndex);
        request.setNumRecords(numRecords);
        request.setRequestId(reqId);
        if (sortOrder != null) {
            request.setSortOrder(sortOrder);
        }
        if (filters != null) {
            request.setFilters(filters);
        }
        return request;
    }

    public SortRequest createSnapshotSortRequest(int reqId, String code,
                                                 List<String> sortFields, String order, int startIndex, int numRecords,
                                                 SortOrder sortOrder, List<String> filters) {
        final SortRequest request =
                new SortRequest(COMMAND_ADD_SNAPSHOT, code, sortFields);
        request.setNssCoreContext(_context);
        request.setOrder(order);
        request.setStartIndex(startIndex);
        request.setNumRecords(numRecords);
        request.setRequestId(reqId);
        if (sortOrder != null) {
            request.setSortOrder(sortOrder);
        }
        if (filters != null) {
            request.setFilters(filters);
        }
        return request;
    }

    public ChartRequest createChartRequest(List<String> codes, String period,
                                           boolean tradingDayOnly, int range, boolean snapshot) {
        int commandId = COMMAND_ADD_BOTH;
        if (snapshot) {
            commandId = COMMAND_ADD_SNAPSHOT;
        }
        ChartRequest request = new ChartRequest(
                commandId, codes, period, tradingDayOnly, range);
        request.setNssCoreContext(_context);

        return request;
    }

    public ChartRequest createRemoveChartRequest(List<String> codes, String period,
                                                 boolean tradingDayOnly, int range, boolean snapshot) {
        int commandId = COMMAND_REMOVE;
        ChartRequest request = new ChartRequest(
                commandId, codes, period, tradingDayOnly, range);
        request.setNssCoreContext(_context);

        return request;
    }
}

