package coreProcessor;

import coreModel.NssCoreContext;
import coreModel.NssPacket;
import coreModel.Processor;

public class BrokerProcessor extends Processor {
    public static final String id = "broker";

    NssCoreContext _context;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    @Override
    public Object process(NssPacket nssPacket) {
        // TODO: implement process
        return null;
    }

    @Override
    public void notify(NssPacket nssPacket, Object data) {
        // TODO: implement notify
    }
}

