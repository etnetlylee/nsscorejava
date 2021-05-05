package com.etnet.coresdk.model;

public class TransItem {
    Double _price;
    Double _volumeAMS;
    Double _volumeNonAMS;

    public TransItem(Double price, Double volumeAMS, Double volumeNonAMS) {
        this._price = price;
        this._volumeAMS = volumeAMS;
        this._volumeNonAMS = volumeNonAMS;
    }

    public void setPrice(Double value) {
        this._price = value;
    }

    public Double getPrice() {
        return this._price;
    }

    public void setVolumeAMS(Double value) {
        this._volumeAMS = value;
    }

    public Double getVolumeAMS() {
        return this._volumeAMS;
    }

    public void setVolumeNonAMS(Double value) {
        this._volumeNonAMS = value;
    }

    public Double getVolumeNonAMS() {
        return this._volumeNonAMS;
    }
}
