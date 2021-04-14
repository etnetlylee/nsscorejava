package network;

import coreModel.User;

public class ConnectOptions {
    User _user;
    String _market;
    String _defaultServer;
    boolean _useRandomServer;

    public ConnectOptions(User user, String market, String defaultServer, boolean useRandomServer) {
        this._user = user;
        this._market = market;
        this._defaultServer = defaultServer;
        this._useRandomServer = useRandomServer;
    }

    public void setUser(User user) {
        this._user = user;
    }

    public User getUser() {
        return this._user;
    }

    public void setMarket(String market) {
        this._market = market;
    }

    public String getMarket() {
        return this._market;
    }

    public void setDefaultServer(String user) {
        this._defaultServer = user;
    }

    public String getDefaultServer() {
        return this._defaultServer;
    }

    public void setUseRandomServer(boolean useRandomServer) {
        this._useRandomServer = useRandomServer;
    }

    public boolean getUseRandomServer() {
        return this._useRandomServer;
    }

}
