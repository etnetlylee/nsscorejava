package com.etnet.coresdk.api;

import java.util.List;

import com.etnet.coresdk.coreModel.QuoteData;

public interface  OnQuoteDataReceived {
    void onQuoteDataReceived(List<QuoteData> bundle);
}
