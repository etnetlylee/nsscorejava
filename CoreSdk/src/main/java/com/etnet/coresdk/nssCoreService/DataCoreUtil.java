package com.etnet.coresdk.nssCoreService;

import java.util.logging.Logger;

public class DataCoreUtil {
    final Logger log = Logger.getLogger("DataCoreUtil");
    final NssMain nssMain;

//    static const MethodChannel _mChannel =  const MethodChannel("EtNetDataCoreUtil");
//    static const BasicMessageChannel _bMessageChannel = const BasicMessageChannel("QuoteData",  JSONMessageCodec());

//    get methodChannel => _mChannel;

    // TODO: Constructor
    public DataCoreUtil(NssMain nssMain) {
        this.nssMain = nssMain;
//        test();
//        receiveNssCoreHandler();
    }

//    public void test() {
//
//        // Handling the packed stockList
//        sStockCodeList(sCodePackage["subscribeStockList"]);
//        // Handling the packed stockFieldIDList
//        sStockFieldIDList(
//                rFieldIDList:sCodePackage["subscribeStockFieldID"]);
//        // QuoteSubscriber ---> Resubscribe
//        quoteSubscriberControllerHK.quoteReSubscribe(
//                updatedStockCodeList:sCodeList,
//                updatedStockSubscribeFieldID:sFieldIDList);
//
//    }

    // Example : Get info Data from platformSide
    // Future<String> get platformVersion async {
    //   final String version = await _mChannel.invokeMethod("getPlatformVersion");
    //   return version;
    // }

    // Example : Send Message to platformSide and Receive Reply
    //  Future transmitData ({@required dynamic dartData}) async{
    //    final String reply = await _basicMessageChannel.send(dartData);
    //  }

    // TODO: Received Message from platformSide
//    Future<dynamic> receivedData () async{
//        return _bMessageChannel.setMessageHandler((message) => message);
//    }

    // TODO: MethodChannelHandler
//    Future<void> receiveNssCoreHandler ()async {
//        MethodChannel dataCoreMethodChannel = methodChannel;
//
//        dataCoreMethodChannel.setMethodCallHandler((MethodCall methodCall) async{
//            switch (methodCall.method){
//
//                case "dataCoreDestroy":
//                    if(quoteSubscriberControllerHK != null)
//                        quoteSubscriberControllerHK.dispose();
//                    if(quoteSubscriberControllerUS != null)
//                        quoteSubscriberControllerUS.dispose();
//                    if(nssMain.getNssCoreServiceHK != null||nssMain.getNssCoreServiceUS)
//                        nssMain.dispose();
//                    break;
//
//
//                case "dataCoreInit":
//                    //  NssCoreService nssCoreServiceHK = nssMain.getNssCoreServiceHK;
//                    NssCoreService nssCoreServiceUS = nssMain.getNssCoreServiceUS;
//                    /**
//                     * methodCall.arguments (HashMap<String, Object>)
//                     *  ->  methodCall.arguments["userInfo"]  ->  HashMap<String, String> -> {"username": username,
//                     "password": password}
//                     *  ->  methodCall.arguments["subscribeStockList"]  ->  ArrayList<String>
//                     *  ->  methodCall.arguments["subscribeStockFieldID"] ->  ArrayList<String>
//                     */
//                    if(methodCall.arguments == null){
//                        // TODO: Send Back the Error to nativeSide ?
//                    } else {
//                        final sCodePackage =  methodCall.arguments;
//                        // Handling the packed stockList
//                        sStockCodeList(rStockList: sCodePackage["subscribeStockList"]);
//                        // Handling the packed stockFieldIDList
//
//                        //print("debug sub"+ sCodePackage["subscribeStockFieldID"]);
//                        sStockFieldIDList(rFieldIDList: sCodePackage["subscribeStockFieldID"]);
//
//                        nssCoreServiceUS.nssCore.login(sCodePackage["userInfo"][0], sCodePackage["userInfo"][1],
//                        false);
//                        // nssCoreServiceHK.nssCore.login("DEV49", "IQDEV49", false);
//                        // nssCoreServiceUS.nssCore.login("uscomp1", "uscomp1", false);
//                    }
//                    break;
//
//                case "quoteSubscriberReSubscribe":
//                    log.info("quoteSubscriberReSubscribe");
//                    if(methodCall.arguments == null){
//                        // TODO: Send Back the Error to nativeSide ?
//                    } else {
//                        final sCodePackage = methodCall.arguments;
//                        // Handling the packed stockList
//                        sStockCodeList(rStockList: sCodePackage["subscribeStockList"]);
//                        // Handling the packed stockFieldIDList
//                        sStockFieldIDList(
//                                rFieldIDList: sCodePackage["subscribeStockFieldID"]);
//                        // QuoteSubscriber ---> Resubscribe
//                        quoteSubscriberControllerUS.quoteReSubscribe(
//                                updatedStockCodeList: sCodeList,
//                                updatedStockSubscribeFieldID: sFieldIDList);
//                    }
//
//
//                    break;
//
//                case "quoteSubscriberReSubscribeHK":
//                    log.info("quoteSubscriberReSubscribeHK");
//                    if(methodCall.arguments == null){
//                        // TODO: Send Back the Error to nativeSide ?
//                    } else {
//                        final sCodePackage = methodCall.arguments;
//                        // Handling the packed stockList
//                        sStockCodeList(rStockList: sCodePackage["subscribeStockList"]);
//                        // Handling the packed stockFieldIDList
//                        sStockFieldIDList(
//                                rFieldIDList: sCodePackage["subscribeStockFieldID"]);
//                        // QuoteSubscriber ---> Resubscribe
//                        quoteSubscriberControllerHK.quoteReSubscribe (
//                                updatedStockCodeList: sCodeList,
//                                updatedStockSubscribeFieldID: sFieldIDList);
//                    }
//                    break;
//                default:
//                    throw MissingPluginException();
//            }
//        });
//    }
}
