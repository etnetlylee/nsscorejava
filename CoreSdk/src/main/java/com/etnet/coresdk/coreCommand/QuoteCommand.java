package com.etnet.coresdk.coreCommand;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.logging.Logger;

import com.etnet.coresdk.api.ContextProvider;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.util.CommandBuilder;

import static com.etnet.coresdk.constants.Command.COMMAND_REMOVE;

public class QuoteCommand extends ContextProvider {
    final Logger log = Logger.getLogger("QuoteCommand");
    NssCoreContext _nssCoreContext;

    @Override
    public void setContext(NssCoreContext context) {
        this._nssCoreContext = context;
    }

    public void sendAddQuoteCommand(
            int reqId, List<String> codes, List<String> fieldIds, int commandId) {
        final int seqNo = _nssCoreContext.getStorage().getNextSeqNo();
        final CommandBuilder cmd = new CommandBuilder(reqId, seqNo);
        cmd.append(String.valueOf(commandId));
        cmd.append(StringUtils.join(codes, "|"));
        cmd.append(StringUtils.join(fieldIds, "|"));

        assert (codes != null && codes.size() > 0);
        assert (fieldIds != null && fieldIds.size() > 0);

        _nssCoreContext.getController().getNetworkController().send(cmd.toString());
        log.info("send add quote command: " + cmd.toString().trim());
    }

    public void sendRemoveQuoteCommand(
            int reqId, List<String> codes, List<String> fieldIds) {
        final int seqNo = _nssCoreContext.getStorage().getNextSeqNo();
        final CommandBuilder cmd = new CommandBuilder(reqId, seqNo);
        cmd.append(String.valueOf(COMMAND_REMOVE));
        cmd.append(StringUtils.join(codes, "|"));
        cmd.append(StringUtils.join(fieldIds, "|"));

        assert (codes != null && codes.size() > 0);
        assert (fieldIds != null && fieldIds.size() > 0);

        _nssCoreContext.getController().getNetworkController().send(cmd.toString());
        log.info("send remove quote command: " + cmd.toString().trim());
    }
}
