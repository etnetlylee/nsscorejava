package com.etnet.coresdk.coreProcessor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreModel.NssPacket;
import com.etnet.coresdk.coreModel.Processor;

public class HeartbeatProcessor extends Processor {
    public static final String id = "heartbeat";
    final Logger log = Logger.getLogger("HeartbeatProcessor");
    NssCoreContext _context;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    @Override
    public Integer process(NssPacket nssPacket) {
        String _temp = "";
        String[] cid = (String[]) ((LinkedList) nssPacket.getParsedBody()).get(0);
        _temp = cid[0];

        final int timestamp = Integer.parseInt(String.valueOf(String.valueOf(_temp.charAt(0)).charAt(0)));
        Calendar t = Calendar.getInstance();
        t.setTimeInMillis(timestamp * 1000);


        log.info(timestamp + " " + t.getTime());

        return timestamp;
    }

    @Override
    public void notify(NssPacket nssPacket, Object data) {
        _context.getController().getHeartbeatController().feedTheWatchDog((int) data);
    }
}
