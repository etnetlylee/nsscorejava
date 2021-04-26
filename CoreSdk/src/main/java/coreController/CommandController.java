package coreController;

import java.util.List;

import api.ContextProvider;
import coreCommand.HttpCommand;
import coreCommand.LoginCommand;
import coreCommand.QuoteCommand;
import coreCommand.SortCommand;
import coreModel.NssCoreContext;
import coreModel.SortOrder;
import coreSubscriber.Subscriber;

public class CommandController extends ContextProvider {
    LoginCommand _loginCommand;
    QuoteCommand _quoteCommand;
    SortCommand _sortCommand;
    HttpCommand _httpCommand;

    NssCoreContext _nssCoreContext;

    CommandController() {
        _loginCommand = new LoginCommand();
        _quoteCommand = new QuoteCommand();
        _sortCommand = new SortCommand();
        // _brokerCommand = new BrokerCommand();
        // _newsCommand = new NewsCommand();
        // _portfolioCommand = new PortfolioCommand();
        _httpCommand = new HttpCommand();
        // _limitAlarmCommand = new LimitAlarmCommand();
    }

    @Override
    public void setContext(NssCoreContext context) {
        _loginCommand.setContext(context);
        _quoteCommand.setContext(context);
        _sortCommand.setContext(context);
        // _brokerCommand.setContext(context);
        // _newsCommand.setContext(context);
        // _portfolioCommand.setContext(context);
        _httpCommand.setContext(context);
        // _limitAlarmCommand.setContext(context);
    }

    public void sendHttpLoginCommand(String username, String password, boolean encrypted) {
        try {
            _loginCommand.sendHttpLoginCommand(username, password, encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAddQuoteCommand(
            int reqId, List<String> codes, List<String> fieldIds, int commandId) {
          _quoteCommand.sendAddQuoteCommand(reqId, codes, fieldIds, commandId);
    }

    public void sendRemoveQuoteCommand(int reqId, List<String> codes, List<String> fieldIds) {
         _quoteCommand.sendRemoveQuoteCommand(reqId, codes, fieldIds);
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
        return _sortCommand.sendAddSortCommand(reqId, seqNo, commandId, sortCode,
                sortFieldIds, order, startIndex, count, sortOrder, filters);
    }

    public int sendRemoveSortCommand(
            int reqId,
            int seqNo,
            List<String> sortFieldIds
            ) {
        return _sortCommand.sendRemoveSortCommand(reqId, seqNo, sortFieldIds);
    }

    public void sendLoginCommand(String nssToken) {
        _loginCommand.sendLoginCommand(nssToken);
    }

    public void sendHttpGetRequest(
            String url, String code, String fieldID, Subscriber subscriber) {
        _httpCommand.sendHttpGetRequest(url, code, fieldID, subscriber);
    }

    public void setCoreContext(NssCoreContext nssCoreContext) {
        _nssCoreContext = nssCoreContext;
        _loginCommand.setNssCoreContext(_nssCoreContext);
        _quoteCommand.setContext(_nssCoreContext);
        _sortCommand.setContext(_nssCoreContext);
    }
}
