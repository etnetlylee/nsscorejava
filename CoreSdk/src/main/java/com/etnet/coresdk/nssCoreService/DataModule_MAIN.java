package com.etnet.coresdk.nssCoreService;

import com.etnet.coresdk.nssCoreService.DataCoreUtil;
import com.etnet.coresdk.nssCoreService.NssMain;

import java.util.HashMap;
import java.util.Map;

public class DataModule_MAIN {

    static Map cacheData = new HashMap();

    public static Object getCacheDataValue(String stockCode, String fieldID) {

        if (cacheData.containsKey(stockCode)) {
            Map tempStockMapData = (Map) cacheData.get(stockCode);
            if (tempStockMapData.containsKey(fieldID)) {
                System.out.print("getCacheDataValue");
                System.out.print(tempStockMapData.get(fieldID));
                return tempStockMapData.get(fieldID);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void setCacheData(String stockCode, String fieldID, Object fieldValue) {
        if (!cacheData.containsKey(stockCode)) {
            Map tempStockMapData = new HashMap();
            if (tempStockMapData.get(fieldID) != null) {
                tempStockMapData.put(fieldID, fieldValue);
            }
            if (cacheData.get(stockCode) != null) {
                cacheData.put(stockCode, tempStockMapData);
            }
        } else {
            Map tempStockMapData = (Map) cacheData.get(stockCode);
            if (!tempStockMapData.containsKey(fieldID)) {
                if (tempStockMapData.get(fieldID) != null) {
                    tempStockMapData.put(fieldID, fieldValue);
                }
            } else {
                tempStockMapData.put(fieldID, fieldValue);
            }
        }
    }

    // TODO: Inside the DataCoreUtil Library UI does not exist
    public void main() {
        final NssMain _nssMain =new NssMain();
        final DataCoreUtil dataCoreUtil =new DataCoreUtil(_nssMain);
//        if (Platform.isAndroid) {
//            // TODO: PlatformChannel FlutterSide Config &
//            final _nssMain =new NssMain();
//            WidgetsFlutterBinding.ensureInitialized();
//            final dataCoreUtil =new DataCoreUtil(nssMain:_nssMain,);
//        } else if (Platform.isIOS) {
//            // TODO: Let iOS try
//            // TODO: PlatformChannel FlutterSide Config &
//            final _nssMain =new NssMain();
//            WidgetsFlutterBinding.ensureInitialized();
//            final dataCoreUtil =new DataCoreUtil(nssMain:_nssMain,);
//        }
    }
}
