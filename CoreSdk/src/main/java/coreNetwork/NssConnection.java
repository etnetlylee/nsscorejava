package coreNetwork;

import java.util.List;
import java.util.logging.Logger;

import coreInterfaceNetwork.Connection;
import coreInterfaceNetwork.ConnectionHandler;
import coreInterfaceNetwork.NssCallback;
import coreModel.NssCoreContext;
import network.ConnectOptions;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

// todo need to discuss : Robin
public class NssConnection extends Connection implements NssCallback {

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public boolean isPaused() {
        return false;
    }

    @Override
    public Connection retryCount(int maxCount) {
        return null;
    }

    @Override
    public Connection withRetryStrategy(List<List<Integer>> strategy) {
        return null;
    }

    @Override
    public void connect(ConnectOptions options) {

    }

    @Override
    public void disconnect(int code, String reason) {

    }

    @Override
    public void reconnect() {

    }

    @Override
    public void send(String cmd) {

    }

    @Override
    public void setHandler(ConnectionHandler handler) {

    }

    @Override
    public void onOpen() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void onMessage(Object data) {

    }

    @Override
    public void onError(Object error, StackTraceElement stackTrace) {

    }
}
