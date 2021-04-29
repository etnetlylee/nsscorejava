package api;

import java.util.List;

import coreModel.QuoteData;

public interface  OnQuoteDataReceived {
    void onQuoteDataReceived(List<QuoteData> bundle);
}
