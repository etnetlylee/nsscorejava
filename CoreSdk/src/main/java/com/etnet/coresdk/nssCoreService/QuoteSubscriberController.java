package com.etnet.coresdk.nssCoreService;

import com.etnet.coresdk.api.OnQuoteDataReceived;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreSubscriber.QuoteSubscriber;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import static com.etnet.coresdk.api.ApiFields.FID_BOARDLOT;
import static com.etnet.coresdk.api.ApiFields.FID_NOMINAL;
import static com.etnet.coresdk.nssCoreService.JavaNativeMap.javaNativeMap;
import static com.etnet.coresdk.nssCoreService.NativeJavaMap.nativeJavaMap;



public class QuoteSubscriberController implements OnQuoteDataReceived {


    Map<String, Map<String, Object>> quotesHashMap = new HashMap<String, Map<String, Object>>() {{
        put("HSIS.HSI", new HashMap<String, Object>());
        put("9988", new HashMap<String, Object>());
    }};

    public String fallbackFilter(Object value) {
        if (value != null) {
            return String.valueOf(value);
        } else {
            return "";
        }
    }

    // TODO: Do not need notify to trigger the UI anymore
    static NssCoreService _nssCoreService;
    static AppContext _appContext;
    static List<String> _stockCodeList;
    static List<String> _stockSubscribeFieldID;
    static QuoteSubscriber quoteSubscriber = new QuoteSubscriber("");
//  final Function transmitCallBack;
//    static const BasicMessageChannel _basicMessageChannel = const BasicMessageChannel("QuoteData",
//    JSONMessageCodec());

//    Future transmitData ({@required Map dartData}) async{
//        final String reply = await _basicMessageChannel.send(dartData);
//    }

    public void setParameters(NssCoreService nssCoreService, AppContext appContext,
                              List<String> subscriberStockCodeList, List<String> subscriberStockFieldList) {
        System.out.print("setParameters!!");
        System.out.print("CheckStockCodeList:\t${_stockCodeList.toString()}");
        System.out.print("CheckStockFieldIDList:\t${_stockSubscribeFieldID.toString()}");
        _nssCoreService = nssCoreService;
        _appContext = appContext;
        _stockCodeList = subscriberStockCodeList;
        _stockSubscribeFieldID = subscriberStockFieldList;
    }

    public void selfInit(String quoteSubscriberName) {
        System.out.print("Running!!");
        System.out.print("CheckStockCodeList:\t${_stockCodeList.toString()}");
        System.out.print("CheckStockFieldIDList:\t${_stockSubscribeFieldID.toString()}");
        quoteSubscriber = new QuoteSubscriber(quoteSubscriberName);
    }

    public QuoteSubscriberController() {
    }

    public void QuoteSubscriberControllerSelfInit(String quoteSubscriberName) {
        System.out.print("Running!!");
        System.out.print("CheckStockCodeList:\t${_stockCodeList.toString()}");
        System.out.print("CheckStockFieldIDList:\t${_stockSubscribeFieldID.toString()}");


        quoteSubscriber = new QuoteSubscriber(quoteSubscriberName);
        // debugPrint("HelloWorld");
        quoteSubscriberInit();

    }


    //    List<StreamSubscription> _streamSubscription = new List<StreamSubscription>();
    CompositeDisposable _streamSubscription = new CompositeDisposable();

    public void QuoteSubscriberControllerInit(String quoteSubscriberName, NssCoreService nssCoreService,
                                                     AppContext appContext, List<String> subscriberStockCodeList,
                                                     List<String> subscriberStockFieldList) {
        quoteSubscriber = new QuoteSubscriber(quoteSubscriberName);
        _nssCoreService = nssCoreService;
        _appContext = appContext;
        _stockCodeList = subscriberStockCodeList;
        _stockSubscribeFieldID = subscriberStockFieldList;
        System.out.println("Checked StockCodeList: " + subscriberStockCodeList.toString());
        System.out.println("Checked StockCodeList: " + subscriberStockFieldList.toString());

        // rNFMap(cList: subscriberStockFieldList);
        // _stockSubscribeFieldID.clear();
        // subscriberStockFieldList.forEach((keyName) {
        //   _stockSubscribeFieldID.add(nativeFlutterMap[keyName]);
        // });
        // debugPrint("Checked SubscribeFieldLis: " + subscriberStockFieldList.toString());
        System.out.println("HelloWorld");
        quoteSubscriberInit();
    }

    public void rNFMap(Object cList) {
        // TODO: Clear Previous subscribeFieldID
        _stockSubscribeFieldID.clear();
        Map<String, String> _temp = (Map<String, String>) cList;
        for (String keyName : _temp.keySet()) {
            _stockSubscribeFieldID.add(nativeJavaMap.get(keyName));
        }
        System.out.print("rNFMap:\t" + _stockSubscribeFieldID.toString());
    }

    public void quoteSubscriberInit() {
        if (quoteSubscriber != null) {
            quoteSubscriber.setOnQuoteDataReceived(this);
            quoteSubscriber.setContext(_nssCoreService.getNssCore().getContext());
        }

        _appContext.asaLoadCompleted.subscribe(new Observer<Boolean>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean completed) {
                if (completed) {
                    // TODO: Further Checking
                    if (_stockCodeList != null && _stockSubscribeFieldID != null) {
                        onAsaReady();
                    } else {
                        System.out.println("StockCodeList or StockSubscribeFieldID is null !!!");
                    }
                } else {
                    if (quoteSubscriber != null) {
                        quoteSubscriber.unsubscribe();
                    }
                }

            }

            @Override
            public void onError(Throwable e) {


            }

            @Override
            public void onComplete() {


            }
        });
//        _streamSubscription.add();
    }


    public static void onAsaReady() {
        quoteSubscribeInit(null, null);
    }

    public static void quoteSubscribeInit(
            List subscribeCodeList, List subscribeField
    ) {
        quoteSubscriber.codes(_stockCodeList);
        quoteSubscriber.fields(_stockSubscribeFieldID);
        quoteSubscriber.subscribe();
//        quoteSubscriber..codes(_stockCodeList)..fields(_stockSubscribeFieldID)..subscribe();
    }

    // TODO: Need to manually handling by ourselves
    public void dispose() {
        if (quoteSubscriber != null) {
            quoteSubscriber.unsubscribe();
        }
//        _streamSubscription.forEach((subscription) {
//                subscription.cancel();
//    });
        _streamSubscription.clear();
    }

    public void quoteReSubscribe(
            List<String> updatedStockCodeList, List<String> updatedStockSubscribeFieldID) {
        System.out.print("ReSubscribing !!");
//    shouldNotify = false;
        quoteSubscriber.unsubscribe();
        // TODO: Add to currentList
        if (updatedStockCodeList != null) {
            _stockCodeList = updatedStockCodeList;
        }

        if (updatedStockSubscribeFieldID != null) {
            _stockSubscribeFieldID = updatedStockSubscribeFieldID;
        }
//    _stockCodeList.replaceRange(1,_stockCodeList.length,updatedStockCodeList);
        quoteSubscriber.codes(_stockCodeList);
        quoteSubscriber.fields(_stockSubscribeFieldID);
        quoteSubscriber.subscribe();
//        quoteSubscriber..codes(_stockCodeList)..fields(_stockSubscribeFieldID)..subscribe();
//    shouldNotify = true;
        // QuoteWidgetNotifier().notifyListeners();
    }
//  [];
//  void quoteRemoveMoreFieldID({@required List<String> comingFieldID}){
//    quoteSubscriber
//      ..fields(comingFieldID)
//      ..unsubscribe();
//  }
//
//  void quoteAddMoreFieldID({@required List<String> comingFieldID}){
//    quoteSubscriber
//      ..fields(comingFieldID)
//      ..subscribe();
//  }

    public String bidAskDataHandling(Integer index, String bidAskFlag, String stockCode, Map assignedHashMap) {
        Object fidSpread, fidSummaryQueue;
        Double spread = 0.0;
        List<String> SpreadList;
        String summaryQueuePrice, summaryQueueVolumnNo;
        summaryQueuePrice = " ";
        summaryQueueVolumnNo = " ";
        DecimalFormat df = new DecimalFormat("###.000");
        if (DataModule_MAIN.getCacheDataValue(stockCode, "spread") != null) {
            fidSpread = DataModule_MAIN.getCacheDataValue(stockCode, "spread");
            System.out.print("Checking Data Of FID_SPREAD: \t" + summaryQueuePrice);
            spread = 0.0;
            SpreadList = Arrays.asList(String.valueOf(fidSpread).split("|"));
            if (SpreadList.size() > 1) {
                if (bidAskFlag == "B") {
                    spread = Double.parseDouble(SpreadList.get(0));
                } else if (bidAskFlag == "A") {
                    spread = Double.parseDouble(SpreadList.get(1));
                }
            }
            if (bidAskFlag == "B") {
                summaryQueuePrice =
                        df.format(((Double) (DataModule_MAIN.getCacheDataValue(stockCode, "bid")) - spread * (index)));
            } else if (bidAskFlag == "A") {
                summaryQueuePrice =
                        df.format(((Double) (DataModule_MAIN.getCacheDataValue(stockCode, "ask")) + spread * (index)));
            }
            Map _temp = (Map) assignedHashMap.get(stockCode);
            if (_temp.get("summaryQueue") != null) {
                fidSummaryQueue = _temp.get("summaryQueue");
                Map<String, List<Map>> _tempQueue = (Map<String, List<Map>>) fidSummaryQueue;
                if (bidAskFlag == "A") {
                    String volume = fallbackFilter(
                            _tempQueue.get("askQueue").get(index).get("volume"));
                    String volumeFormatted = fallbackFilter(volume);
                    String number = fallbackFilter(_tempQueue.get("askQueue").get(index).get("no"));
                    number = String.valueOf(Math.max(Integer.parseInt(number), 0));
                    summaryQueueVolumnNo = volumeFormatted + " (" + number + " )";

                } else {
                    String volume = fallbackFilter(_tempQueue.get("bidQueue").get(index).get("volume"));
                    String volumeFormatted = fallbackFilter(volume);
                    String number = fallbackFilter(_tempQueue.get("bidQueue").get(index).get("no"));
                    number = String.valueOf(Math.max(Integer.parseInt(number), 0));
                    summaryQueueVolumnNo = volumeFormatted + " (" + number + ")";
                }
            }
        }
        // print("CheckingDataChecking: \t" +summaryQueuePrice );
        // print("CheckingDataChecking: \t" +summaryQueueVolumnNo );
        return "${(index +1).toString()}\t$summaryQueuePrice\t$summaryQueueVolumnNo";
//        return index.toString() + summaryQueuePrice + "\t" + summaryQueueVolumnNo;
    }

    // TODO: Handling TransactionData
    String transactionDataHandling(Integer transactionIndex, Object transqueueData) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String fidTransQueue, transactionTime, transactionPrice, transactionVolume, transactionType;
        transactionTime = " ";
        transactionPrice = " ";
        transactionVolume = " ";
        transactionType = " ";
        if (transqueueData != null) {
            fidTransQueue = (String) transqueueData;
            int length = fidTransQueue.length();
            if (length - transactionIndex > 0) {
                // --> transactionTime

//                transactionTime = fallbackFilter(fidTransQueue[length - transactionIndex - 1]["time"], (v) {
//                return dateFormat.format(DateTime.fromMillisecondsSinceEpoch(v)).toString() + "  ";
//        });
                // --> transactionPrice

                ////  transactionPrice = fallbackFilter<double>(fidTransQueue[length - transactionIndex -
                // 1]["price"], (v) {
                //     return v.toStringAsFixed(3) + "  ";
                //   });
//                transactionPrice = fallbackFilter < int>(fidTransQueue[length - transactionIndex - 1]["transNo"],
//                (v) {
//                return v.toString() + "  ";
//        });

                // --> transactionVolume
//                transactionVolume = fallbackFilter < double>
//                (fidTransQueue[length - transactionIndex - 1]["volume"], (v) {
//                return "  " + v.toStringAsFixed(0);
//        });
                // --> transactionType
//                transactionType = "  " + fidTransQueue[length - transactionIndex - 1]["transType"];
            }
        }
        return transactionTime + transactionPrice + transactionVolume + transactionType;
    }


    public void assignValueToCustomKey_sCode(Map<String, Map> assignedHashMap, String stockCode, String assignedKey,
                                             Map assignedValue) {
        if (!assignedHashMap.get(stockCode).containsKey(assignedKey)) {
            if (assignedHashMap.get(stockCode) != null) {
                assignedHashMap.get(stockCode).put(assignedKey, assignedValue);
            }
        } else {
            assignedHashMap.get(stockCode).put(assignedKey, assignedValue);
        }
    }


    public void assignValueToCorrespondingKey(Map<String, Map<String, Object>> assignedHashMap, QuoteData quoteData,
                                              Map assignedValue) {
        if (!assignedHashMap.get(quoteData.getCode()).containsKey(quoteData.getNssData().getFieldID())) {
            if (assignedHashMap.get(quoteData.getCode()) != null) {
                assignedHashMap.get(quoteData.getCode()).put(rFNMap(
                        quoteData.getNssData().getFieldID()), assignedValue);
            }
        } else {
            assignedHashMap.get(quoteData.getCode()).put(rFNMap(quoteData.getNssData().getFieldID()), assignedValue);
        }
    }

    void assignValueToCustomKey(Map<String, Map<String, Object>> assignedHashMap, QuoteData quoteData,
                                String assignedValue,
                                String customKey) {
        if (!assignedHashMap.get(quoteData.getCode()).containsKey(customKey)) {
            if (assignedHashMap.get(quoteData.getCode()) != null) {
                assignedHashMap.get(quoteData.getCode()).put(customKey, assignedValue);
            }
        } else {
            assignedHashMap.get(quoteData.getCode()).put(customKey, assignedValue);
        }
    }

    String rFNMap(String checkString) {
        if (javaNativeMap.containsKey(checkString)) {
            return javaNativeMap.get(checkString);
        } else {
            return checkString;
        }
    }

    @Override
    public void onQuoteDataReceived(List<QuoteData> bundle) {
        final Map<String, Map<String, Object>> dataStorageHashMap = new HashMap<String, Map<String, Object>>();
        // TODO: implement onQuoteDataReceived
        System.out.print("onQuoteDataReceived!!!");
        for (QuoteData quoteData : bundle) {
            System.out.print("DataReceived!!! getData" + quoteData.getNssData().getData().toString());
            System.out.print("DataReceived!!! getCode" + quoteData.getCode());
            System.out.print("DataReceived!!! getFieldID()" + quoteData.getNssData().getFieldID());
            // print("DataReceived!!! getName"+quoteData.getNssData().getName().toString());
            // TODO: Check Whether the QuoteHashMap exist a stockCode hashMap or not
            if (!dataStorageHashMap.containsKey(quoteData.getCode())) {
                if (dataStorageHashMap.get(quoteData.getCode()) != null) {
                    dataStorageHashMap.put(quoteData.getCode(), new HashMap<String, Object>());
                }
            }
            if (quoteData.getNssData().getFieldID() == "82S1") {
                System.out.print("82S1 setCacheData!! NSS getData" + quoteData.getNssData().getData().toString());
                DataModule_MAIN.setCacheData(quoteData.getCode(), "82S1_transQueue", quoteData.getNssData().getData());
            }
            if (quoteData.getNssData().getFieldID() == "53" || quoteData.getNssData().getFieldID() == "52" || quoteData.getNssData().getFieldID() == "86" || quoteData.getNssData().getFieldID() == "2") {
                // TODO: For bidAskSection)
                if (quoteData.getNssData().getFieldID() == "53") {
                    DataModule_MAIN.setCacheData(quoteData.getCode(), "bid",
                            quoteData.getNssData().getData());
                } else if (quoteData.getNssData().getFieldID() == "52") {
                    DataModule_MAIN.setCacheData(quoteData.getCode(), "ask",
                            quoteData.getNssData().getData());
                } else if (quoteData.getNssData().getFieldID() == "86") {
                    DataModule_MAIN.setCacheData(quoteData.getCode(), "spread",
                            quoteData.getNssData().getData());
                } else if (quoteData.getNssData().getFieldID() == "2") {
                    DataModule_MAIN.setCacheData(quoteData.getCode(), "tcName",
                            quoteData.getNssData().getData());
                }

            }


            //get US Quote data
            //n# trans 39,marketCap 18,volume 38,turnover 37
            if (quoteData.getNssData().getFieldID() == "37" || quoteData.getNssData().getFieldID() == "38" || quoteData.getNssData().getFieldID() == "39" || quoteData.getNssData().getFieldID() == "18" || quoteData.getNssData().getFieldID() == "49") {
                if (!dataStorageHashMap.get(quoteData.getCode()).containsKey(rFNMap(quoteData.getNssData().getFieldID()))) {
                    if (dataStorageHashMap.get(quoteData.getCode()) != null) {
                        dataStorageHashMap.get(quoteData.getCode()).put(rFNMap(
                                quoteData.getNssData().getFieldID()), quoteData.getNssData().getData());
                    }
                } else {
                    dataStorageHashMap.get(quoteData.getCode()).put(rFNMap(
                            quoteData.getNssData().getFieldID()), quoteData.getNssData().getData());
                }
            }
            // TODO: New Approach -> Replace the fieldID -> fieldKeyName


            //TODO: US Stock
            if (quoteData.getNssData().getFieldID() == "18") {
                DataModule_MAIN.setCacheData(quoteData.getCode(), "marketCap", quoteData.getNssData().getData());
            }
            if (quoteData.getNssData().getFieldID() == "39") {
                DataModule_MAIN.setCacheData(quoteData.getCode(), "noOfTrans", quoteData.getNssData().getData());
            }


            // TODO: Check stockCode HashMap exist a FID_Field or not
            if (quoteData.getNssData().getFieldID() == "82S1" || quoteData.getNssData().getFieldID() == "36") {
                // TODO: Transaction Handler
                if (quoteData.getNssData().getFieldID() == "82S1") {
                    List<String> tempList = new ArrayList<String>();
                    for (int i = 0; i < 15; i++) {
                        tempList.add(transactionDataHandling(i, quoteData.getNssData().getData()));
                    }

                    // TODO: New Approach -> Replace the fieldID -> fieldKeyName
                    if (!dataStorageHashMap.get(quoteData.getCode()).containsKey(rFNMap(
                            quoteData.getNssData().getFieldID()))) {
                        if (dataStorageHashMap.get(quoteData.getCode()) != null) {
                            dataStorageHashMap.get(quoteData.getCode()).put(rFNMap(
                                    quoteData.getNssData().getFieldID()), tempList);
                        }
                    } else {
                        dataStorageHashMap.get(quoteData.getCode()).put(rFNMap(
                                quoteData.getNssData().getFieldID()), tempList);
                    }


                    // TODO: OldApproach
                    // if(!dataStorageHashMap[quoteData.getCode()].containsKey(quoteData.getNssData().getFieldID())){
                    //   dataStorageHashMap[quoteData.getCode()].putIfAbsent(quoteData.getNssData().getFieldID(), ()
                    //   => tempList);
                    // } else {
                    //   dataStorageHashMap[quoteData.getCode()].update(quoteData.getNssData().getFieldID(),
                    //   (currentValue) =>tempList);
                    // }
//          dataStorageHashMap
//          assignValueToCorrespondingKey(assignedHashMap:  dataStorageHashMap, quoteData: quoteData, assignedValue:
//          tempList);
                } else if (quoteData.getNssData().getFieldID() == "36") {
                    // TODO: Default true = 1 (positive); Default false = 0 (); Default Unknown = 2;
                    String stockChangeStatus = "2"; // Default Case is unknown
                    if ((Integer) quoteData.getNssData().getData() > 0) {
                        stockChangeStatus = "1";
                    } else if ((Integer) quoteData.getNssData().getData() < 0) {
                        stockChangeStatus = "0";
                    } else {
                        stockChangeStatus = "2";
                    }

                    if (!dataStorageHashMap.get(quoteData.getCode()).containsKey(rFNMap("stockStatus"))) {
                        if (dataStorageHashMap.get(quoteData.getCode()) != null) {
                            dataStorageHashMap.get(quoteData.getCode()).put(rFNMap("stockStatus"), stockChangeStatus);
                        }
                    } else {
                        dataStorageHashMap.get(quoteData.getCode()).put(rFNMap("stockStatus"), stockChangeStatus);
                    }
                    assignValueToCorrespondingKey(dataStorageHashMap, quoteData,
                            (Map) quoteData.getNssData().getData());
                    assignValueToCustomKey(dataStorageHashMap, quoteData, stockChangeStatus, "stockStatus");
                }

                // TODO: Take out the dataAssignApproach as the func outside for reuse


                // TODO: Normal Case to handle another fieldID
            } else {

                // TODO: Take out the dataAssignApproach as the func outside for reuse
//        assignValueToCorrespondingKey(assignedHashMap:  dataStorageHashMap, quoteData: quoteData, assignedValue:
//        quoteData.getNssData().getData());


                // TODO: Old Approach
                if (!dataStorageHashMap.get(quoteData.getCode()).containsKey(rFNMap(quoteData.getNssData().getFieldID()))) {
                    if (dataStorageHashMap.get(quoteData.getCode()) != null) {
                        dataStorageHashMap.get(quoteData.getCode()).put(rFNMap(
                                quoteData.getNssData().getFieldID()), quoteData.getNssData().getData());
                    }
                } else {
                    dataStorageHashMap.get(quoteData.getCode()).put(rFNMap(
                            quoteData.getNssData().getFieldID()), quoteData.getNssData().getData());
                }
            }

            // if(dataStorageHashMap[quoteData.getCode()]["summaryQueue"] != null && dataStorageHashMap[quoteData
            // .getCode()]["bid"] && )


            // TODO: Some FID_Field need to manually cal or fill through existing FID_Field
            /// e.g : FID_Fee does not exist, FID_Fee = FID_BOARDLOT * FID_NOMINAL, you also need to create a new
            // field add to the existing stockCode HashMap
            if (quoteData.getCode() != "HSIS.HSI") {
                if (dataStorageHashMap.get(quoteData.getCode()).get(FID_BOARDLOT) != null && dataStorageHashMap.get(quoteData.getCode()).get(FID_NOMINAL) != null) {
                    double tempFID_Fee =
                            ((Double.parseDouble(fallbackFilter(dataStorageHashMap.get(quoteData.getCode()).get(FID_BOARDLOT)))) * Double.parseDouble((fallbackFilter(dataStorageHashMap.get(quoteData.getCode()).get(FID_NOMINAL)))));
                    if (!dataStorageHashMap.get(quoteData.getCode()).containsKey("FEE")) {
                        if (dataStorageHashMap.get(quoteData.getCode()) != null) {
                            dataStorageHashMap.get(quoteData.getCode()).put("FEE", tempFID_Fee);
                        }
                    } else {
                        dataStorageHashMap.get(quoteData.getCode()).put("FEE", tempFID_Fee);
                    }
                }
                // print("HelloWorld");
                // print(quotesHashMap[codeList[codeList.length -1]][FID_D_SUMMARY_QUEUE]);
            }
//      if(quoteData.getCode() != "HSIS.HSI"){
//        print("HelloWorld");
//        print(quoteData.getNssData().getFieldID().toString() + "   " + quoteData.getNssData().getData().toString());
//      }
            // TODO: Move TransmitDataToHere would be faster
        }

        // TODO: Handling all the stokCode
        HashMap _tempBidAskMap = new HashMap();
        HashMap _tempHashMap = new HashMap();
        for (String element : _stockCodeList) {
            System.out.print("looker_1");
            // print("checktBid:\t${getCacheDataValue(stockCode: element, fieldID: "")}")
            if (dataStorageHashMap.get(element).get("summaryQueue") != null) {
                // TODO: Bid
                _tempBidAskMap = new HashMap();
                System.out.print("looker_2");
                _tempHashMap = new HashMap();
                for (int i = 0; i < 10; i++) {
                    System.out.print("bid(${i.toString()}):\t${bidAskDataHandling(index: i, bidAskFlag: \"B\", " +
                            "stockCode: element, " +
                            "assignedHashMap: dataStorageHashMap)}");
                    if (_tempHashMap.get(String.valueOf(i)) != null) {
                        _tempHashMap.put(String.valueOf(i), bidAskDataHandling(i, "B",
                                element, dataStorageHashMap));
                    }
                }
                // _tempBidAskMap.putIfAbsent("Bid", () => _tempHashMap);
                if (!dataStorageHashMap.get(element).containsKey("bidList")) {
                    if (dataStorageHashMap.get(element) != null) {
                        dataStorageHashMap.get(element).put("bidList", _tempHashMap);
                    }
                } else {
                    dataStorageHashMap.get(element).put("bidList", _tempHashMap);
                }

                // TODO: Ask
                _tempHashMap = new HashMap();
                for (int i = 0; i < 10; i++) {
                    System.out.print("ask(${i.toString()}):\t${bidAskDataHandling(index: i, bidAskFlag: \"A\", " +
                            "stockCode: element," +
                            " " +
                            "assignedHashMap: dataStorageHashMap)}");
                    if (_tempHashMap.get(String.valueOf(i)) != null) {
                        _tempHashMap.put(String.valueOf(i), bidAskDataHandling(i, "A",
                                element, dataStorageHashMap));
                    }
                }

                if (!dataStorageHashMap.get(element).containsKey("askList")) {
                    if (dataStorageHashMap.get(element) != null) {
                        dataStorageHashMap.get(element).put("askList", _tempHashMap);
                    }
                } else {
                    dataStorageHashMap.get(element).put("askList", _tempHashMap);
                }
                // _tempBidAskMap.putIfAbsent("Ask", () => _tempHashMap);
                //
                // print("checkHashMap:\t${_tempBidAskMap.toString()}");
                // TODO: Add to existing hashMap
                // if(!dataStorageHashMap[element].containsKey("bidAskMap")){
                //   dataStorageHashMap[element].putIfAbsent("bidAskMap", () => _tempBidAskMap);
                // } else {
                //   dataStorageHashMap[element].update("bidAskMap", (currentValue) => _tempBidAskMap);
                // }

            }
        }

        // TODO: Transmit to NativeSide
        // ignore: unnecessary_statements
//
//    dataStorageHashMap.forEach((key, value) {
//      Map tempValue = value;
//      if (tempValue[key][FID_SPREAD] != null && quotesHashMap[key][FID_BID] != null && quotesHashMap[key][FID_ASK]
//      != null ){
//
//      }
//    });
//
//    if(dataStorageHashMap[quoteData.getCode()][FID_SPREAD] != null && quotesHashMap[quoteData.getCode()][FID_BID]
//    != null &&  quotesHashMap[quoteData.getCode()][FID_ASK] != null &&  quotesHashMap[quoteData.getCode()][FID_ASK]){
//      List<String> bidDataList = new List<String>();
//      List<String> askDataList = new List<String>();
//      for(int i = 0; i < 10; i ++){
//        bidDataList.add(bidAskDataHandling(index: i, bidAskFlag: "B", stockCode: quoteData.getCode(),
//        assignedHashMap: dataStorageHashMap, quoteData: quoteData));
//        askDataList.add(bidAskDataHandling(index: i, bidAskFlag: "A", stockCode: quoteData.getCode(),
//        assignedHashMap: dataStorageHashMap, quoteData: quoteData));
//      }
//      Map<String,List<String>> bidAskHashMap = {"BidDataList": bidDataList, "AskDataList": askDataList};
//      assignValueToCustomKey(assignedHashMap:  dataStorageHashMap, quoteData: quoteData, assignedValue:
//      bidAskHashMap, customKey: "9787999");
//      print("Checking the bidAskHashMap: \t" + bidAskHashMap.toString());
//

        // TODO: Before transmit the data to nativeSide, check
        System.out.print("DataStorageHashMapData: " + dataStorageHashMap.toString());
//        transmitData(dataStorageHashMap);
        return;
    }
}