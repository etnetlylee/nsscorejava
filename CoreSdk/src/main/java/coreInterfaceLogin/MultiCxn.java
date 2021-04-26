package coreInterfaceLogin;

public class MultiCxn {
    String _sector;
    String _news;
    String _chart;

    public MultiCxn(String sector, String news, String chart){
        this._sector = sector;
        this._news = news;
        this._chart = chart;
    };

    public String sector(){
        return this._sector;
    }

    public String news(){
        return this._news;
    }

    public String chart(){
        return this._chart;
    }
}
