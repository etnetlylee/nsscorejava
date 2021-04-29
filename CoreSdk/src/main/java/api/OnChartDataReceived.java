package api;

import java.util.List;

import coreModel.QuoteData;

public interface OnChartDataReceived {
    void onChartDataReceived(List<QuoteData> bundle);
}
