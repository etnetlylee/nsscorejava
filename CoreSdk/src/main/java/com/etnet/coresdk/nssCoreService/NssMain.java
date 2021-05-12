package com.etnet.coresdk.nssCoreService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.etnet.coresdk.events.NssEvent;
import com.etnet.coresdk.events.UserEvent;
import com.etnet.coresdk.marketConfig.StockMarket;

import org.apache.commons.lang3.StringUtils;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;

public class NssMain {
    final Logger log = Logger.getLogger("Main");
//    final List<StreamSubscription> subscription_hk = [];
//    final List<StreamSubscription> subscription_us = [];

    CompositeDisposable subscription_hk = new CompositeDisposable();
    CompositeDisposable subscription_us = new CompositeDisposable();

    AppContext _appContextUS;
    AppContext _appContextHK;
    //    EventBus _eventBus_hk;
//    EventBus _eventBus_us;
    Observable _eventBus_hk;
    Observable _eventBus_us;
    NssCoreService _nssCoreService_hk;
    NssCoreService _nssCoreService_us;

    NssStatus _nssStatus = NssStatus.Unauthenticated;

    // channel
//    static const BasicMessageChannel _eventMessageChannel =
//      const BasicMessageChannel("CoreEventCode", StandardMessageCodec());

    // TODO: Getter
    public NssCoreService getNssCoreServiceHK() {
        return this._nssCoreService_hk;
    }

    public NssCoreService getNssCoreServiceUS() {
        return this._nssCoreService_us;
    }

    public Observable getEventBus_hk() {
        return this._eventBus_hk;
    }

    public Observable getEventBus_us() {
        return this._eventBus_us;
    }

    public AppContext getAppContextUS() {
        return this._appContextUS;
    }

    public AppContext getAppContextHK() {
        return this._appContextHK;
    }

    public NssStatus getNssStatus() {
        return this._nssStatus;
    }


    // TODO: Constructor
    public void dispose() {
        // _nssCoreService.nssCore.destroy();
        // _nssCoreService_hk.destroy();
        _nssCoreService_us.destroy();
    }

//    void _sendCoreEventCode(Map arguments) async {
//        Map reply = await _eventMessageChannel.send(arguments);
//    }

    public NssMain() {
        Logger rootLogger = LogManager.getLogManager().getLogger("Main");
        rootLogger.setLevel(Level.ALL);

        rootLogger.setFilter(new Filter() {
            @Override
            public boolean isLoggable(LogRecord record) {
                final int len = record.getLoggerName().length();
                final int sublen = len > 20 ? 20 : len;
                final String name = StringUtils.leftPad(record.getLoggerName().substring(0, sublen), 20, " ");
                System.out.print(
                        "dataCoreInitRecord Level Name${record.level.name}: ${record.time}: [${name}] ${record" +
                                ".message}");
                return true;
            }
        });


        log.info("create nss core and app widget");

        // TODO:
        _appContextUS = new AppContext();
        _appContextHK = new AppContext();
        _eventBus_hk = new Observable() {
            @Override
            protected void subscribeActual(@NonNull Observer observer) {

            }
        };
        _eventBus_us = new Observable() {
            @Override
            protected void subscribeActual(@NonNull Observer observer) {

            }
        };
        //  TODO: hk
         _nssCoreService_hk = new NssCoreService(StockMarket.HK, false ,_appContextHK, _eventBus_hk);
        // TODO: us
        _nssCoreService_us =
                new NssCoreService(StockMarket.US, true, _appContextUS, _eventBus_us);

         _nssCoreService_hk.init();
//        _nssCoreService_us.init();
         _nssCoreService_hk.initCoreConfig();
         _nssCoreService_hk.createConnection();


//     subscription_hk.add(_eventBus_hk.on<UserEvent>().listen((UserEvent userEvent) {
//       if (userEvent.event() == UserEvent.HttpLoginSuccess) {
//         log.info("http login success");
//         _nssCoreService_hk.initCoreConfig(null);
//         _nssCoreService_hk.createConnection();
// //        systemNotifier.updateNssCoreStatus(updateNssCoreStatus: NssCoreStatus.alreadyLogin);
//         _nssStatus = NssStatus.Authenticated;
// //        searchStockController..text = codeList[codeList.length-1];
//       } else if (userEvent.event() == UserEvent.HttpLoginFailed) {
//         log.info("http login failed");
// //        systemNotifier.updateNssCoreStatus(updateNssCoreStatus: NssCoreStatus.loginFail);
// //        print(systemNotifier.getNssCoreStatus.toString());
//       } else if (userEvent.event() == UserEvent.NssLoginSucess) {
//         log.info("nss login success");
//       } else if (userEvent.event() == UserEvent.NssLoginFailed) {
//         log.info("nss login failed");
//       } else if (userEvent.event() == UserEvent.NssLoginDuplicated) {
//         log.info("nss duplicated login");
//       } else if (userEvent.event() == UserEvent.NssLoginUnknown) {
//         log.info("unknown login issues");
//       }
//     }));

//     subscription_hk.add(_eventBus_hk.on<UserEvent>().listen((UserEvent userEvent) {
//       if (userEvent.event() == UserEvent.HttpLoginSuccess) {
//         log.info("http login success");
//         _nssCoreService_hk.initCoreConfig(null);
//         _nssCoreService_hk.createConnection();
// //        systemNotifier.updateNssCoreStatus(updateNssCoreStatus: NssCoreStatus.alreadyLogin);
//         _nssStatus = NssStatus.Authenticated;
// //        searchStockController..text = codeList[codeList.length-1];
//       } else if (userEvent.event() == UserEvent.HttpLoginFailed) {
//         log.info("http login failed");
// //        systemNotifier.updateNssCoreStatus(updateNssCoreStatus: NssCoreStatus.loginFail);
// //        print(systemNotifier.getNssCoreStatus.toString());
//       } else if (userEvent.event() == UserEvent.NssLoginSucess) {
//         log.info("nss login success");
//       } else if (userEvent.event() == UserEvent.NssLoginFailed) {
//         log.info("nss login failed");
//       } else if (userEvent.event() == UserEvent.NssLoginDuplicated) {
//         log.info("nss duplicated login");
//       } else if (userEvent.event() == UserEvent.NssLoginUnknown) {
//         log.info("unknown login issues");
//       }
//     }));

        // subscription_hk.add(_eventBus_hk.on<NssEvent>().listen((NssEvent nssEvent) {
        //   if (nssEvent.event() == NssEvent.NssConnect) {
        //     log.info("nss connected");
        //   } else if (nssEvent.event() == NssEvent.NssAsaProgress) {
        //     log.info("nss asa progress");
        //   } else if (nssEvent.event() == NssEvent.NssAsaLoad) {
        //     log.info("nss asa load");
        //     _appContextHK.asaLoadCompleted.sink.add(true);
        //     print("All ASA Loading is finished ... Kick Start Quote-Subscriber");
        //     quoteSubscriberControllerHK = new QuoteSubscriberController.init(
        //         quoteSubscriberName: "QuoteSubscriberData",
        //         nssCoreService: _nssCoreService_hk,
        //         appContext: _appContextHK,
        //         subscriberStockCodeList: sCodeList,
        //         subscriberStockFieldList: sFieldIDList
        //     );
        //   } else if (nssEvent.event() == NssEvent.NssDisconnect) {
        //     _appContextHK.asaLoadCompleted.sink.add(false);
        //   } else {
        //     log.info("nss event");
        //   }
        // }));

        Disposable subscribe1 = _eventBus_us.subscribe(
                new Consumer<UserEvent>() {
                    @Override
                    public void accept(@NonNull UserEvent userEvent) throws Exception {
                        if (userEvent.event() == UserEvent.HttpLoginSuccess) {
                            log.info("http login success");
//                            _sendCoreEventCode({200: "http login success"});
                            _nssCoreService_us.initCoreConfig();
                            _nssCoreService_us.createConnection();
//        systemNotifier.updateNssCoreStatus(updateNssCoreStatus: NssCoreStatus.alreadyLogin);
                            _nssStatus = NssStatus.Authenticated;

//        searchStockController..text = codeList[codeList.length-1];
                        } else if (userEvent.event() == UserEvent.HttpLoginFailed) {
                            log.info("http login failed");
//                            _sendCoreEventCode({403: "http login failed"});
//        systemNotifier.updateNssCoreStatus(updateNssCoreStatus: NssCoreStatus.loginFail);
//        print(systemNotifier.getNssCoreStatus.toString());
                        } else if (userEvent.event() == UserEvent.NssLoginSucess) {
                            log.info("nss login success");
                        } else if (userEvent.event() == UserEvent.NssLoginFailed) {
                            log.info("nss login failed");
                        } else if (userEvent.event() == UserEvent.NssLoginDuplicated) {
                            log.info("nss duplicated login");
                        } else if (userEvent.event() == UserEvent.NssLoginUnknown) {
                            log.info("unknown login issues");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //对应onError()
                    }
                }, new Action() {
                    @Override
                    public void run() throws Throwable {
                        //对应onComplete()

                    }
                }
        );


        Disposable subscribe2 = _eventBus_us.subscribe(
                new Consumer<NssEvent>() {
                    @Override
                    public void accept(@NonNull NssEvent nssEvent) throws Exception {
                        if (nssEvent.event() == NssEvent.NssConnect) {
                            log.info("nss connected");
                        } else if (nssEvent.event() == NssEvent.NssAsaProgress) {
                            log.info("nss asa progress");
                        } else if (nssEvent.event() == NssEvent.NssAsaLoad) {
                            log.info("nss asa load");
                            _appContextUS.asaLoadCompleted.onNext(true);
                            System.out.print("All ASA Loading is finished ... Kick Start Quote-Subscriber");
                            Util.quoteSubscriberControllerUS.QuoteSubscriberControllerInit(
                                    "QuoteSubscriberData",
                                    _nssCoreService_us,
                                    _appContextUS,
                                    Util.sCodeList,
                                    Util.sFieldIDList);
                        } else if (nssEvent.event() == NssEvent.NssDisconnect) {
                            _appContextUS.asaLoadCompleted.onNext(false);
                        } else {
                            log.info("nss event");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //对应onError()
                    }
                }, new Action() {
                    @Override
                    public void run() throws Throwable {
                        //对应onComplete()

                    }
                }
        );

        subscription_us.add(subscribe1);
        subscription_us.add(subscribe2);

//        ProcessSignal.sigint.watch().listen((ProcessSignal signal) {
//            // subscription_hk.forEach((event) {
//            //   event.cancel();
//            // });
//            log.info(" ProcessSignal");
//            subscription_us.forEach((event) {
//                    log.info("before coreservice cancel");
//            event.cancel();
//      });
//            log.info("before coreservice destroy");
//            // _nssCoreService_hk.nssCore.destroy();
//            _nssCoreService_us.nssCore.destroy();
//            exit(0);
//        });
    }
}
