package coreStorage.model;

public class PriceUpdown {
    double _up;
    double _noChange;
    double _down;
    double _noTurnover;

    public PriceUpdown(Double up, Double noChange,
                       Double down, Double noTurnover) {
        this._up = up;
        this._noChange = noChange;
        this._down = down;
        this._noTurnover = noTurnover;
    }

    public double getUp(){
        return this._up;
    }

    public void setUp(double up){
        this._up = up;
    }

    public double getNoChange(){
        return this._noChange;
    }

    public void setNoChange(double noChange){
        this._noChange = noChange;
    }

    public double getDown(){
        return this._down;
    }

    public void setDown(double down){
        this._down = down;
    }

    public double getNoTurnover(){
        return this._noTurnover;
    }

    public void setNoTurnover(double noTurnover){
        this._noTurnover = noTurnover;
    }

}
