package com.etnet.coresdk.coreModel;

import com.etnet.coresdk.api.ContextProvider;

public abstract class Processor extends ContextProvider {
    public Object process(NssPacket nssPacket) {
        return nssPacket;
    }

    public void notify(NssPacket nssPacket, Object data) {
    }
}
