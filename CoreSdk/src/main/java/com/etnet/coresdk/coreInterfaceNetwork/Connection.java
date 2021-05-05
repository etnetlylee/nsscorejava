package com.etnet.coresdk.coreInterfaceNetwork;

import java.util.List;

import com.etnet.coresdk.api.ContextProvider;
import com.etnet.coresdk.network.ConnectOptions;

public abstract class Connection extends ContextProvider {
    public abstract boolean isConnected();

    public abstract void resume();


    public abstract void pause();

    public abstract boolean isPaused();

    public abstract Connection retryCount(int maxCount);

    public abstract Connection withRetryStrategy(List<List<Integer>> strategy);

    public abstract void connect(ConnectOptions options);

    public abstract void disconnect(int code, String reason);

    public abstract void reconnect();

    public abstract void send(String cmd);

    public abstract void setHandler(ConnectionHandler handler);
}
