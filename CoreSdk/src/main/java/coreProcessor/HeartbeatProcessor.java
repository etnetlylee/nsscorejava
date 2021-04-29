package coreProcessor;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import coreModel.NssCoreContext;
import coreModel.NssPacket;
import coreModel.Processor;

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
        Map<Integer, List<Integer>> _tem = (Map<Integer, List<Integer>>) nssPacket.getParsedBody();
        final int timestamp = Integer.parseInt(String.valueOf(_tem.get(0).get(0)));
        Calendar t = Calendar.getInstance();
        t.setTimeInMillis(timestamp * 1000);


        log.info(timestamp + " " + t);

        return timestamp;
    }

    @Override
    public void notify(NssPacket nssPacket, Object data) {
        _context.getController().getHeartbeatController().feedTheWatchDog((int) data);
    }
}
