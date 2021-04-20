package coreConfigInterface;

public class InitiatorConfig {
    boolean _useASA;
    boolean _useNews;

    public InitiatorConfig(boolean useASA, boolean useNews) {
        this._useASA = useASA;
        this._useNews = useNews;
    }

    public boolean getUseASA() {
        return this._useASA;
    }

    public void setUseASA(boolean useASA) {
        this._useASA = useASA;
    }

    public boolean getUseNews() {
        return this._useNews;
    }

    public void setUseNews(boolean useNews) {
        this._useNews = useNews;
    }
}
