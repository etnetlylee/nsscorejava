package coreModel;

import api.ContextProvider;

public abstract class Processor extends ContextProvider {
    public Object process(NssPacket nssPacket) {
        return nssPacket;
    }

    public void notify(NssPacket nssPacket, Object data) {
    }
}
