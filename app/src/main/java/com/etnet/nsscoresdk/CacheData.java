package com.etnet.nsscoresdk;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

abstract class CacheData {

    private static Map cacheData = new HashMap();

    public Map getCacheData() {
        return cacheData;
    }

    public Object getSpecificDataValue(String stockCode, String fieldID){
        Map sCodeData = (Map) cacheData.get(stockCode);
        if(sCodeData.get(fieldID) != null){
            return sCodeData.get(fieldID);
        } else {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCacheData(String stockCode, String fieldID, Object fieldValue) {
        if(!cacheData.containsKey(stockCode)){

            Map tempMapData= new HashMap();
            tempMapData.put(fieldID, fieldValue);
            cacheData.putIfAbsent(stockCode, tempMapData);

        } else {
            Map tempMapData= (Map) cacheData.get(stockCode);
            if(!tempMapData.containsKey(fieldID)){
                tempMapData.putIfAbsent(fieldID, fieldValue);
            } else {
                tempMapData.put(fieldID, fieldValue);
            }
        }
    }

}
