package com.etnet.coresdk.api;

public interface OnDataSubscription {
    void onAddSubscription(String code, String period, int range,
                           boolean tradingDayOnly, boolean snapshot);

    void onRemoveSubscription(String code, String period, int range,
                              boolean tradingDayOnly, boolean snapshot);
}
