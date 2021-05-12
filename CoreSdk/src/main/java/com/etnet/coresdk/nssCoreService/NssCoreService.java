package com.etnet.coresdk.nssCoreService;

import android.os.StrictMode;

import com.etnet.coresdk.NssCore;
import com.etnet.coresdk.config.hk.HkAsaConfig;
import com.etnet.coresdk.config.hk.HkConfig;
import com.etnet.coresdk.config.hk.HkStressTestConfig;
import com.etnet.coresdk.config.us.UsAsaConfig;
import com.etnet.coresdk.config.us.UsConfig;
import com.etnet.coresdk.config.us.UsConfigStressTestConfig;
import com.etnet.coresdk.coreConfig.DefaultAsaConfig;
import com.etnet.coresdk.coreConfig.DefaultDecoderConfig;
import com.etnet.coresdk.coreConfig.DefaultProcessorConfig;
import com.etnet.coresdk.coreConfigInterface.CoreConfig;
import com.etnet.coresdk.coreController.Controller;
import com.etnet.coresdk.coreInterfaceLogin.LoginResponse;
import com.etnet.coresdk.coreModel.User;
import com.etnet.coresdk.coreNetwork.Ajax;
import com.etnet.coresdk.events.AppEvent;
import com.etnet.coresdk.events.NssEvent;
import com.etnet.coresdk.events.NssTime;
import com.etnet.coresdk.events.UserEvent;
import com.etnet.coresdk.marketConfig.StockMarket;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInput;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.etnet.coresdk.nssCoreService.Environment.*;

public class NssCoreService {
    final Logger log = Logger.getLogger("NssCoreService");

    NssCore _nssCore;
    boolean _isNssSessionEnd = true;

    AppContext _context;
    //    EventBus _event;
    Observable _event;
    StockMarket _stockMarket;
    boolean _isStressTest;

    CompositeDisposable _subscription_hk = new CompositeDisposable();
    CompositeDisposable _subscription_us = new CompositeDisposable();
    //    List<StreamSubscription<AppEvent>> _subscription_hk = new List();
//    List<StreamSubscription<AppEvent>> _subscription_us = new List();
    boolean _isShareConnection = false;
    // TODO: Getter

    public StockMarket getgetStockMarket() {
        return this._stockMarket;
    }

    public NssCoreService(StockMarket stockMarket, boolean isStessTest, AppContext context,
                          Observable event) {
        this._context = context;
        this._event = event;
        this._stockMarket = stockMarket;
        this._isStressTest = isStessTest;
    }

    public NssCore getNssCore() {
        return this._nssCore;
    }

    public void init() {
        _nssCore = new NssCore();
        getNssCore().setConfig(_getNssConfig());
        initEventAdapters();
    }

    public CoreConfig _getNssConfig() {
        // return HkConfig;
        // ignore: unused_local_variable
        CoreConfig refCoreConfig = null;
        // _stockMarket == StockMarket.US
        //     ? refCoreConfig = UsConfig
        //     : (_isStressTest ? refCoreConfig = UsConfigStressTestConfig : refCoreConfig = HkConfig);

        if (_stockMarket == StockMarket.US) {
            if (_isStressTest) {
                refCoreConfig = UsConfigStressTestConfig.UsConfigStressTestConfig;
            } else {
                refCoreConfig = UsConfig.UsConfig;
            }
        }
        if (_stockMarket == StockMarket.HK) {
            if (_isStressTest) {
                refCoreConfig = HkStressTestConfig.HkStressTestConfig;
            } else {
                refCoreConfig = HkConfig.HkConfig;
            }
        }
        return refCoreConfig;
        // return DefaultConfig;
    }

    //    void initCoreConfig(dynamic authService) {   // todo : no use of authService, why pass it as parameter?
    public void initCoreConfig() {
        // _nssCore.addAsaConfig(HkAsaConfig);
        // _nssCore.addAsaConfig(ShAsaConfig);
        // _nssCore.addAsaConfig(SzAsaConfig);
        if (_stockMarket == StockMarket.US) {
            _nssCore.addAsaConfig(UsAsaConfig.UsAsaConfig);
        } else {
            _nssCore.addAsaConfig(HkAsaConfig.HkAsaConfig);
        }

        if (_stockMarket != StockMarket.US) {
            _nssCore.addAsaConfig(DefaultAsaConfig.DefaultAsaConfig);
        }
        _nssCore.addDecoderConfig(DefaultDecoderConfig.DefaultDecoderConfig);
        _nssCore.addProcessorConfig(DefaultProcessorConfig.DefaultProcessorConfig);
    }

    public void initEventAdapters() {
        // this._subscription_hk.add(_nssCore
        //     .getEventService()
        //     .on<UserEvent>()
        //     .listen((UserEvent userEvent) {
        //   if (userEvent.event() == UserEvent.HttpLoginSuccess) {
        //     _event.fire(
        //         new UserEvent(UserEvent.HttpLoginSuccess, userEvent.data()));
        //   } else if (userEvent.event() == UserEvent.HttpLoginFailed) {
        //     _event.fire(
        //         new UserEvent(UserEvent.HttpLoginFailed, userEvent.data()));
        //   } else if (userEvent.event() == UserEvent.NssLoginSucess) {
        //     _isNssSessionEnd = false;
        //     _event.fire(
        //         new UserEvent(UserEvent.NssLoginSucess, userEvent.data()));
        //   } else if (userEvent.event() == UserEvent.NssLoginFailed) {
        //     _isNssSessionEnd = true;
        //     _event.fire(
        //         new UserEvent(UserEvent.NssLoginFailed, userEvent.data()));
        //   } else if (userEvent.event() == UserEvent.NssLoginDuplicated) {
        //     _isNssSessionEnd = true;
        //     _event.fire(
        //         new UserEvent(UserEvent.NssLoginDuplicated, userEvent.data()));
        //   } else if (userEvent.event() == UserEvent.NssLoginUnknown) {
        //     _isNssSessionEnd = true;
        //     _event.fire(
        //         new UserEvent(UserEvent.NssLoginUnknown, userEvent.data()));
        //   }
        // }));
        //
        // this._subscription_hk.add(_nssCore
        //     .getEventService()
        //     .on<NssEvent>()
        //     .listen((NssEvent nssEvent) {
        //   if (nssEvent.event() == NssEvent.NssConnect) {
        //     _isNssSessionEnd = false;
        //     _event.fire(new NssEvent(NssEvent.NssConnect, nssEvent.data()));
        //   } else if (nssEvent.event() == NssEvent.NssDisconnect) {
        //     _isNssSessionEnd = true;
        //     _event.fire(new NssEvent(NssEvent.NssDisconnect, nssEvent.data()));
        //   } else if (nssEvent.event() == NssEvent.NssAsaProgress) {
        //     _event.fire(new NssEvent(NssEvent.NssAsaProgress, nssEvent.data()));
        //   } else if (nssEvent.event() == NssEvent.NssAsaLoad) {
        //     _event.fire(new NssEvent(NssEvent.NssAsaLoad, nssEvent.data()));
        //   } else if (nssEvent.event() == NssEvent.NssNewsLoad) {
        //     _event.fire(new NssEvent(NssEvent.NssNewsLoad, nssEvent.data()));
        //   } else if (nssEvent.event() == NssEvent.NssMaintenance) {
        //     _event.fire(new NssEvent(NssEvent.NssMaintenance, nssEvent.data()));
        //   } else if (nssEvent.event() == NssEvent.NssTime) {
        //     _event.fire(new NssEvent(NssEvent.NssTime, nssEvent.data()));
        //   } else if (nssEvent.event() == NssEvent.NssHeartbeat) {
        //     _event.fire(new NssEvent(NssEvent.NssHeartbeat, nssEvent.data()));
        //   } else if (nssEvent.event() == NssEvent.NssHeartbeatTimeout) {
        //     _event.fire(
        //         new NssEvent(NssEvent.NssHeartbeatTimeout, nssEvent.data()));
        //   } else if (nssEvent.event() == NssEvent.NssData) {
        //     _event.fire(new NssEvent(NssEvent.NssData, nssEvent.data()));
        //   }
        // }));
        Disposable subscribe1 = _nssCore.getObservableService().subscribe(
                new Consumer<UserEvent>() {
                    @Override
                    public void accept(@NonNull UserEvent userEvent) throws Exception {
                        if (userEvent.event() == UserEvent.HttpLoginSuccess) {
                            _event.create(new ObservableOnSubscribe<UserEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<UserEvent> e) throws Exception {
                                    e.onNext(new UserEvent(UserEvent.HttpLoginSuccess, (Integer) userEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (userEvent.event() == UserEvent.HttpLoginFailed) {
                            _event.create(new ObservableOnSubscribe<UserEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<UserEvent> e) throws Exception {
                                    e.onNext(new UserEvent(UserEvent.HttpLoginFailed, (Integer) userEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (userEvent.event() == UserEvent.NssLoginSucess) {
                            _isNssSessionEnd = false;
                            _event.create(new ObservableOnSubscribe<UserEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<UserEvent> e) throws Exception {
                                    e.onNext(new UserEvent(UserEvent.NssLoginSucess, (Integer) userEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (userEvent.event() == UserEvent.NssLoginFailed) {
                            _isNssSessionEnd = true;
                            _event.create(new ObservableOnSubscribe<UserEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<UserEvent> e) throws Exception {
                                    e.onNext(new UserEvent(UserEvent.NssLoginFailed, (Integer) userEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (userEvent.event() == UserEvent.NssLoginDuplicated) {
                            _isNssSessionEnd = true;
                            _event.create(new ObservableOnSubscribe<UserEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<UserEvent> e) throws Exception {
                                    e.onNext(new UserEvent(UserEvent.NssLoginDuplicated, (Integer) userEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (userEvent.event() == UserEvent.NssLoginUnknown) {
                            _isNssSessionEnd = true;
                            _event.create(new ObservableOnSubscribe<UserEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<UserEvent> e) throws Exception {
                                    e.onNext(new UserEvent(UserEvent.NssLoginUnknown, (Integer) userEvent.data()));
                                    e.onComplete();
                                }
                            });
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

        Disposable subscribe2 = _nssCore.getObservableService().subscribe(
                new Consumer<NssEvent>() {
                    @Override
                    public void accept(@NonNull NssEvent nssEvent) throws Exception {
                        if (nssEvent.event() == NssEvent.NssConnect) {
                            _isNssSessionEnd = false;
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssConnect, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssDisconnect) {
                            _isNssSessionEnd = true;
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssDisconnect, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssAsaProgress) {
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssAsaProgress, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssAsaLoad) {
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssAsaLoad, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssNewsLoad) {
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssNewsLoad, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssMaintenance) {
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssMaintenance, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssTime) {
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssTime, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssHeartbeat) {
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssHeartbeat, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssHeartbeatTimeout) {
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssHeartbeatTimeout, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssData) {
                            _event.create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssData, nssEvent.data()));
                                    e.onComplete();
                                }
                            });
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

        _subscription_us.add(subscribe1);
        _subscription_us.add(subscribe2);
    }

    public void deatchEventAdapters() {
        // _subscription_hk.forEach((StreamSubscription<AppEvent> subscription) {
        //   subscription.cancel();
        // });
        // _subscription_hk.clear();
        _subscription_us.clear();
        _subscription_us.dispose();
    }

    public boolean isShareConnection() {
        return _isShareConnection;
    }

    public void login(String username, String password, boolean encrypted) {
        if (_isNssSessionEnd) {
            getNssCore().login(username, password, encrypted);
        }
    }

    public void loginIntegrated(String username, String token, Object result) {
        // nssCore.loginIntegrated(username, token, result);
    }

    public void createConnection() {
        if (_nssCore.getContext().getUser() != null && _nssCore.getContext().getUser().isSessionEnd()) {
            log.info("no sesssion for nss");
        }
        String url =
                String.valueOf(Environment.environment.get("apiUrl")) + String.valueOf(Environment.environment.get(
                        "ipService"));
        log.info("get ip address");

        ApiResponse result = new Controller().getNetworkController().sendHttpGetRequest(url);
        if (result != null){
                log.info("API Status : " + result.getStatus());
                if (result.getStatus().equals("success")) {
                    IPGeoInfo ipGeoInfo = result.getData();
                    if (ipGeoInfo.getIsCn()) {
                        _nssCore.enableCensor(true);
                    }
                    log.info("get ip success is_cn = " + ipGeoInfo.getIsCn());
                } else {
                    log.info("get ip recv incorrect status code");
                }
        }
    }

    public void destroy() {
        _nssCore.destroy();
    }
}



