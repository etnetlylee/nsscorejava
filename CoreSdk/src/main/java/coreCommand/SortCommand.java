package coreCommand;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import api.ContextProvider;
import coreModel.NssCoreContext;
import coreModel.SortField;
import coreModel.SortOrder;
import util.CommandBuilder;

import static constants.Command.COMMAND_REMOVE;

public class SortCommand extends ContextProvider {
    final Logger log = Logger.getLogger("SortCommand");
    NssCoreContext _nssCoreContext;

    @Override
    public void setContext(NssCoreContext context) {
        this._nssCoreContext = context;
    }

    public static String constructSortFilter(List<String> filters) {
        final List<String> formattedFilter = new ArrayList<String>();
        if (filters != null) {
            for (String filter : filters){
                if (filter.contains("|")) {
                    formattedFilter.add("(" + filter + ")");
                } else {
                    formattedFilter.add(filter);
                }
            }
        }
        return StringUtils.join(formattedFilter, "&");
    }

    public static String constructSortOrder(SortOrder sortOrders) {
        final List<String> formattedSortOrder = new ArrayList<String>();
        if (sortOrders != null) {
            final List<SortField> sortFieldList = sortOrders.getSortFieldList();
            for (SortField sortField : sortFieldList){
                formattedSortOrder.add(sortField.getFieldId() + sortField.getOrder());
            }
        }
        return StringUtils.join(formattedSortOrder, "|");
    }

    public int sendAddSortCommand(
            int reqId,
            int seqNo,
            int commandId,
            String sortCode,
            List<String> sortFieldIds,
            String order,
            int startIndex,
            int count,
            SortOrder sortOrder,
            List<String> filters) {
        int sendSeqNo = seqNo;
        if (sendSeqNo == -1) {
            sendSeqNo = _nssCoreContext.getStorage().getNextSeqNo();
        }
        String _sortFieldIds = sortFieldIds != null ? StringUtils.join(sortFieldIds, "|") : "";
        final String _sortOrder = constructSortOrder(sortOrder);
        final String _filters = constructSortFilter(filters);

        CommandBuilder cmd = new CommandBuilder(reqId, sendSeqNo);
        cmd.append(String.valueOf(commandId));
        cmd.append(_sortFieldIds);
        cmd.append(sortCode);
        cmd.append(order);
        cmd.append(String.valueOf(startIndex));
        cmd.append(String.valueOf(count));
        cmd.append(_sortOrder);
        cmd.append(_filters);

        log.info("send add sort command: " + cmd.toString().trim());

        _nssCoreContext.getController().getNetworkController().send(cmd.toString());

        return sendSeqNo;
    }

    public int sendRemoveSortCommand(int reqId, int seqNo, List<String> sortFieldIds) {
        String _sortFieldIds = "";
        if (sortFieldIds != null) {
            _sortFieldIds = StringUtils.join(sortFieldIds, "|");
        }
        String commandId = String.valueOf(COMMAND_REMOVE);

        CommandBuilder cmd = new CommandBuilder(reqId, seqNo);
        cmd.append(commandId);
        cmd.append(_sortFieldIds);

        log.info("send remove sort command: " + cmd.toString().trim());

        _nssCoreContext.getController().getNetworkController().send(cmd.toString());

        return seqNo;
    }
}