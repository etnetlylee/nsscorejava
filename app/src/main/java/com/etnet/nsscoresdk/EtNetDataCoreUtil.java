package com.etnet.nsscoresdk;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.etnet.coresdk.nssCoreService.DataCoreUtil;
import com.etnet.coresdk.nssCoreService.NssMain;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EtNetDataCoreUtil extends CacheData {

    // TODO: Need a event Interface

    private static final String NameOfBasicMessageChannel = "QuoteData";
    private static final String NameOfMethodChannel = "EtNetDataCoreUtil";
    private Context mContext;
    Boolean isFlutterEngineExist = false;
    Boolean isCoreInit = false; // nssCore
    private StreamingJSONDataListener streamingJSONDataListener;
    private StreamingHashDataListener streamingHashDataListener;
    static CallBackDataType callBackDataType = CallBackDataType.JSONObject;

    public static void setCallBackDataType(CallBackDataType callBackDataType) {
       EtNetDataCoreUtil.callBackDataType = callBackDataType;
    }

    public Boolean getCoreInit() {
        return isCoreInit;
    }

    public Boolean getFlutterEngineExist() {
        return isFlutterEngineExist;
    }

    static final String defStockCode = "US.AAPL";
    static final ArrayList<String> quotePageSubscribeFieldIDList = new ArrayList<String>(Arrays.asList(
//            INFO: 2021-01-06 15:05:20.193557: [            QuoteScreen] US.AAPL field - 4(enName): Apple
//    INFO: 2021-01-06 15:05:20.193841: [            QuoteScreen] US.AAPL field - 54(open): 128.89
//    INFO: 2021-01-06 15:05:20.193878: [            QuoteScreen] US.AAPL field - 49(close): 129.41
//    INFO: 2021-01-06 15:05:20.193898: [            QuoteScreen] US.AAPL field - 41(high): 131.74
//    INFO: 2021-01-06 15:05:20.193916: [            QuoteScreen] US.AAPL field - 42(low): 128.43
//    INFO: 2021-01-06 15:05:20.193932: [            QuoteScreen] US.AAPL field - 34(nominal): 131.01
//    INFO: 2021-01-06 15:05:20.193948: [            QuoteScreen] US.AAPL field - 36(perChange): 1.23638
//    INFO: 2021-01-06 15:05:20.193964: [            QuoteScreen] US.AAPL field - 39(noOfTrans): 690199.0
//    INFO: 2021-01-06 15:05:20.193980: [            QuoteScreen] US.AAPL field - 43(peRatio): 39.4543
//    INFO: 2021-01-06 15:05:20.193996: [            QuoteScreen] US.AAPL field - 18(marketCap): 2200203196820.0

            "enName", "open", "close", "high", "low", "nominal", "perChange", "noOfTrans", "peRatio", "marketCap", "change", "volume", "bid", "ask", "82S1_transQueue"
//          "close", "change", "turnover", "noOfTrans",
//            "volume","marketCap"
    ));


    static ArrayList<String> subStockCodeList = new ArrayList<String>(Arrays.asList(defStockCode));
    static ArrayList<String> subFieldIDList = quotePageSubscribeFieldIDList;

    public EtNetDataCoreUtil(@NonNull Context refContext, @NonNull String userName, @NonNull String userPassword, @NonNull Object refCallBackDataType) {
        mContext = refContext;
        sdkInit(refCallBackDataType);
        nssInit(userName, userPassword);
        dataReceiveHandler(refCallBackDataType);
    }

    public EtNetDataCoreUtil(@NonNull Context refContext, @NonNull String userName, @NonNull String userPassword, @NonNull ArrayList<String> subscribeStockCodeList, @NonNull ArrayList<String> subscribeFieldIDList, @NonNull Object refCallBackDataType) {
        mContext = refContext;
        sdkInit(refCallBackDataType);
        nssInit(userName, userPassword, subscribeStockCodeList, subscribeFieldIDList);
        dataReceiveHandler(refCallBackDataType);
    }


    private void sdkInit(@NonNull Object refDataType) {
//        flutterEngine = new FlutterEngine(mContext);
//        flutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
//        methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), NameOfMethodChannel);
//        basicMessageChannel = new BasicMessageChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), NameOfBasicMessageChannel, JSONMessageCodec.INSTANCE);
        isFlutterEngineExist = true;
        streamingJSONDataListener = (StreamingJSONDataListener) mContext;
//          try {
//                streamingJSONDataListener = (StreamingJSONDataListener) mContext;
//            } catch (ClassCastException e) {
//                throw new ClassCastException(mContext.toString() +
//                        "must implement ExampleDialogListener");
//            }
//
//        if(refDataType == CallBackDataType.JSONObject){
//            try {
//                streamingJSONDataListener = (StreamingJSONDataListener) mContext;
//            } catch (ClassCastException e) {
//                throw new ClassCastException(mContext.toString() +
//                        "must implement ExampleDialogListener");
//            }
//        } else if (refDataType == CallBackDataType.HashMap){
//            try {
//                streamingHashDataListener = (StreamingHashDataListener) mContext;
//            } catch (ClassCastException e) {
//                throw new ClassCastException(mContext.toString() +
//                        "must implement ExampleDialogListener");
//            }
//        }


    }

    private void nssInit(@NonNull String userName, @NonNull String userPassword) {
        // nssInit with defSubscribeStockCodeList & defSubscribeFieldIDList
        ArrayList<String> userInfo = new ArrayList<String>(Arrays.asList(userName, userPassword));
        HashMap<String, Object> sCorePackage = new HashMap<String, Object>() {{
            put("userInfo", userInfo);
            put("subscribeStockList", subStockCodeList);
            put("subscribeStockFieldID", subFieldIDList);
        }};
        nssCoreInitMethodChannel(sCorePackage);
    }

    private void nssInit(@NonNull String userName, @NonNull String userPassword, @NonNull ArrayList<String> subscribeStockCodeList, @NonNull ArrayList<String> subscribeFieldIDList) {
        ArrayList<String> userInfo = new ArrayList<String>(Arrays.asList(userName, userPassword));
        HashMap<String, Object> sCorePackage = new HashMap<String, Object>() {{
            put("userInfo", userInfo);
            put("subscribeStockList", subscribeStockCodeList);
            put("subscribeStockFieldID", subscribeFieldIDList);
        }};
        nssCoreInitMethodChannel(sCorePackage);
    }

    private void nssCoreInitMethodChannel(@NonNull HashMap<String, Object> refCorePackage) {
        isCoreInit = true;
//        methodChannel.invokeMethod("dataCoreInit", refCorePackage, new MethodChannel.Result() {
//            @Override
//            public void success(@Nullable Object result) {
//                System.out.print(result);
//                isCoreInit = true;
//            }
//
//            @Override
//            public void error(String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
//
//            }
//
//            @Override
//            public void notImplemented() {
//
//            }
//        });
    }


    public void quotePageSubscribe(@NonNull ArrayList<String> subscribeStockCodeList) {

        Log.i("datacore util", "quotePageSubscribe");
        subStockCodeList = subscribeStockCodeList;
        subFieldIDList = quotePageSubscribeFieldIDList;

        HashMap<String, Object> sCorePackage = new HashMap<String, Object>() {{
            put("subscribeStockList", subStockCodeList);
            put("subscribeStockFieldID", quotePageSubscribeFieldIDList);
        }};
//        methodChannel.invokeMethod("quoteSubscriberReSubscribe", sCorePackage);
    }

    public void customQuoteSubscribe(@NonNull ArrayList<String> subscribeStockCodeList, @NonNull ArrayList<String> subscribeFieldIDList) {
        subStockCodeList = subscribeStockCodeList;
        subFieldIDList = subscribeFieldIDList;

        HashMap<String, Object> sCorePackage = new HashMap<String, Object>() {{
            put("subscribeStockList", subStockCodeList);
            put("subscribeStockFieldID", subFieldIDList);
        }};
    }


    void dataReceiveHandler(@NonNull Object refDataType) {
        if (refDataType != null){
            Log.i("etnetutil msg", refDataType.toString());
            JSONObject receiveJSONObject = (JSONObject) refDataType;
            Log.i("receiveJSONObject", receiveJSONObject.toString());
            streamingJSONDataListener.onReceivedJSONData((JSONObject) refDataType);
        }
//        basicMessageChannel.setMessageHandler(new BasicMessageChannel.MessageHandler() {
//            @Override
//            public void onMessage(@Nullable Object message, @NonNull BasicMessageChannel.Reply reply) {
//                if (message != null) {
//                    Log.i("etnetutil msg", message.toString());
//                    JSONObject receiveJSONObject = (JSONObject) message;
//                    Log.i("receiveJSONObject", receiveJSONObject.toString());
//                    streamingJSONDataListener.onReceivedJSONData((JSONObject) message);
////                    switch (refDataType){
////                        case HashMap:
////                            ObjectMapper objectMapper = new ObjectMapper();
////                            try {
////                                Map<String, Object> mapData = objectMapper.readValue(receiveJSONObject.toString(), new TypeReference<HashMap<String, Object>>() {});
////                                streamingHashDataListener.onReceivedHashData(mapData);
////                            } catch (JsonProcessingException e) {
////                                e.printStackTrace();
////                            }
////                            break;
////                        case JSONObject:
////                            streamingJSONDataListener.onReceivedJSONData((JSONObject) message);
////                            break;
////                        default:
////                            break;
////
////                    }
//
//                }
//            }
//
//        });
    }

    public void dispose(@NonNull Boolean flutterDispose) {
        if (isCoreInit) {
            isCoreInit = false;
            if (flutterDispose) {
                isFlutterEngineExist = false;
            }
//            methodChannel.invokeMethod("dataCoreDestroy", null, new MethodChannel.Result() {
//                @Override
//                public void success(@Nullable Object result) {
//                    isCoreInit = false;
//                    if (flutterDispose) {
//                        flutterEngine.destroy();
//                        isFlutterEngineExist = false;
//                    }
//                }
//
//                @Override
//                public void error(String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
//                }
//
//                @Override
//                public void notImplemented() {
//                }
//            });
        } else {
            if (flutterDispose) {
//                flutterEngine.destroy();
                isFlutterEngineExist = false;
            }
        }
        if (streamingHashDataListener != null) {
            streamingHashDataListener = null;
        }
        if (streamingJSONDataListener != null) {
            streamingJSONDataListener = null;
        }
    }


    public interface StreamingJSONDataListener {
        void onReceivedJSONData(JSONObject jsonData);
    }

    public interface StreamingHashDataListener {
        void onReceivedHashData(Map mapData);
    }

}
