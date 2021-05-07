package com.etnet.coresdk.nssCoreService;

import java.util.ArrayList;
import java.util.List;

import static com.etnet.coresdk.nssCoreService.NativeJavaMap.nativeJavaMap;

public class Util {
    public static final List<String> sCodeList = new ArrayList<String>();
    public static final List<String> sFieldIDList = new ArrayList<String>();
    public static final QuoteSubscriberController quoteSubscriberControllerHK = null;
    public static final QuoteSubscriberController quoteSubscriberControllerUS = null;

    public void sStockCodeList(List rStockList){
        sCodeList.clear();
        for(int i = 0; i < rStockList.size(); i ++){
            sCodeList.add(rStockList.get(i).toString());
        }
    }

    public void sStockFieldIDList(List rFieldIDList){
        sFieldIDList.clear();
        for(int i = 0; i < rFieldIDList.size(); i ++){
            sFieldIDList.add(nativeJavaMap.get(rFieldIDList.get(i).toString()));
        }
    }

}
