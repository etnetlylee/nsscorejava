package com.etnet.coresdk.api;

import java.util.List;

import com.etnet.coresdk.coreModel.QuoteData;

public interface OnChartDataReceived {
    void onChartDataReceived(List<QuoteData> bundle);
}
