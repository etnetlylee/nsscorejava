package coreProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import coreEnvironment.Environment;
import coreModel.NssCoreContext;
import coreModel.NssPacket;
import coreModel.Processor;
import coreModel.SortChanged;
import coreModel.SortCodes;
import coreModel.SortData;
import coreSubscriber.Subscriber;

public class SortProcessor extends Processor {
    public static final String id = "sort";
    final Logger log = Logger.getLogger("SortProcessor");

    NssCoreContext _context;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    @Override
    public Object process(NssPacket nssPacket) {
        final int size = nssPacket.getSize();
        final int seqNo = nssPacket.getSeqNo();
        final String body = nssPacket.getBody();
        final List<String> codeList = body.length() == 0 ? new ArrayList<>() : Arrays.asList(body.split(","));
        SortData sortData =
                (SortData) _context.getStorage().getSequenceData(seqNo);

        if (sortData != null && sortData.getSubscription().size() > 0) {
            List<String> addList = new ArrayList<String>();
            List<String> removeList = new ArrayList<String>();
            List<SortChanged> changedList = new ArrayList<SortChanged>();

            List<String> previousCodeList = sortData.getSortCodes().getCodeList();
            if (previousCodeList != null) {
                for (String code : previousCodeList){
                    if (!previousCodeList.contains(code)){
                        addList.add(code);
                    }
                }

                for (String code : previousCodeList){
                    if (!codeList.contains(code)){
                        removeList.add(code);
                    }
                }

                // Find order changed
                changedList = new ArrayList<SortChanged>();
                for (int i = 0; i < codeList.size(); i++) {
                    final String code = codeList.get(i);
                    int pos = previousCodeList.indexOf(code);
                    if (pos != -1 && pos != i) {
                        changedList.add(new SortChanged(code, pos, i));
                    }
                }
            } else {
                addList = new ArrayList<String>(codeList);
            }
            final SortCodes updatedSortCodes =
                    new SortCodes(codeList, addList, removeList, changedList);
            sortData.setSortCodes(updatedSortCodes);
            sortData.setFullListSize(size);
            _context.getStorage().updateSequenceData(seqNo, sortData);
            return sortData;
        } else {
            if (Environment.isDebug()) {
                log.info("no subscriber, forgot to unsubscribe?");
            }
            return null;
        }
    }

    @Override
    public void notify(NssPacket nssPacket, Object sortData) {
        List<Subscriber> subscribers = _context
                .getController()
                .getSubscriberController()
                .getSequenceSubscribers(nssPacket.getSeqNo());
        if (sortData != null) {
            if (subscribers != null) {
                for (Subscriber subscriber : subscribers){
                    subscriber.informUpdate(sortData);
                }
            } else {
                log.info("no subscriber(s) found");
            }
        } else {
            log.info("sortdata is null");
        }
    }
}

