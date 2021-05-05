package com.etnet.coresdk.coreController;

import java.util.logging.Logger;

import com.etnet.coresdk.api.ContextProvider;
import com.etnet.coresdk.coreInterfaceNetwork.Connection;
import com.etnet.coresdk.coreInterfaceNetwork.ConnectionHandler;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreNetwork.Ajax;
import com.etnet.coresdk.coreNetwork.NssConnection;
import okhttp3.Response;

public class NetworkController extends ContextProvider implements ConnectionHandler {
    final Logger log = Logger.getLogger("NetworkController");
    Connection _connection;
    Ajax _ajax = new Ajax();
    NssCoreContext _context;

    boolean isSnapshotConnection = false;

    NetworkController() {
    }

    public void initConnection() {
        _connection = new NssConnection();
        _connection.setHandler(this);
    }

    public boolean isConnectionExists() {
        return _connection != null;
    }

    public boolean isConnectionPaused() {
        if (getConnection() != null) {
            return _connection.isPaused();
        } else {
            return false;
        }
    }

    public void setContext(NssCoreContext context) {
        _context = context;
    }

    public Connection getConnection() {
        return _connection;
    }

    public void detachConnection() {
        log.info("say good by to our connection, no wait, no grace period");
        _connection.setContext(null);
        _connection.setHandler(null);
    }

    public void pauseConnection() {
        if (_connection != null) {
            _connection.pause();
        } else {
            log.fine("connection seems destroyed");
        }
    }

    public void resumeConnection() {
        if (_connection != null) {
            _connection.resume();
        } else {
            log.fine("connection seems destroyed");
        }
    }

    @Override
    public void onConnected() {
//        String nssToken = _context.getUser().getToken();
//        _context.events.fire(new NssEvent(NssEvent.NssConnect, DateTime.now()));
//        _context.getController().getCommandController().sendLoginCommand(nssToken);
    }

    @Override
    public void onDisConnected(int code, String reason) {
//        final data =NssCloseReason(code, reason);
//        NssEvent nssEvent = new NssEvent(NssEvent.NssDisconnect, data);
//        _context.events.fire(nssEvent);
    }

    @Override
    public void onDataReceived(String raw) {
//        _context.getController().getProcessorController().process(raw);
    }

    @Override
    public boolean isSessionEnd() {
        return _context.getUser() != null && _context.getUser().isSessionEnd();
    }

    public void send(String cmd) {
        if (getConnection() != null) {
            getConnection().send(cmd);
        } else {
            log.info("connection lost, drop cmd: " + cmd);
        }
    }

   public Response sendHttpGetRequest(String url) {
        return _ajax.sendRequest(url, null);
    }

   public Response sendHttpPostRequest(String url, Object postData) {
        return _ajax.sendRequest(url, postData);
    }

}
