package com.etnet.coresdk.coreInterfaceNetwork;

public interface ConnectionHandler {
    public void onDataReceived(String raw);

    public boolean isSessionEnd();

    public void onConnected();

    public void onDisConnected(int code, String reason);

}
