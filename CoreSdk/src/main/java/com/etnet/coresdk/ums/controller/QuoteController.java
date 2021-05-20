package com.etnet.coresdk.ums.controller;

import com.etnet.coresdk.ums.data.EtnetProductUser;
import com.etnet.coresdk.ums.service.QuoteService;

public class QuoteController {
    QuoteService quoteService = new QuoteService();

    public String getQuote(EtnetProductUser etnetProductUser, String stockCode){
        String jsonString = quoteService.getQuote(etnetProductUser, stockCode);
        return jsonString;
    }
}
