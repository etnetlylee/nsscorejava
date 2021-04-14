package model;

public class TransItem {
    float _price;
    int _volumeAMS;
    int _volumeNonAMS;

    public TransItem(float price, int volumeAMS, int volumeNonAMS) {
        this._price = price;
        this._volumeAMS = volumeAMS;
        this._volumeNonAMS = volumeNonAMS;
    }
    public void setPrice(float value) {
        this._price = value;
    }

    public float getPrice() {
        return this._price;
    }

    public void setVolumeAMS(int value) {
        this._volumeAMS = value;
    }

    public int getVolumeAMS() {
        return this._volumeAMS;
    }

    public void setVolumeNonAMS(int value) {
        this._volumeNonAMS = value;
    }

    public int getVolumeNonAMS() {
        return this._volumeNonAMS;
    }
}
