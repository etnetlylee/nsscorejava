//package com.etnet.coresdk.nssCoreService;
//
//import com.etnet.coresdk.NssCore;
//import com.etnet.coresdk.coreConfigInterface.CoreConfig;
//import com.etnet.coresdk.events.AppEvent;
//import com.etnet.coresdk.marketConfig.StockMarket;
//
//import java.util.logging.Logger;
//
//public class NssCoreService {
//    final Logger log = Logger.getLogger("NssCoreService");
//
//    NssCore _nssCore;
//    boolean _isNssSessionEnd = true;
//
//    AppContext _context;
//    EventBus _event;
//    StockMarket _stockMarket;
//    boolean _isStressTest;
//
//    List<StreamSubscription<AppEvent>> _subscription_hk = new List();
//    List<StreamSubscription<AppEvent>> _subscription_us = new List();
//    boolean _isShareConnection = false;
//    // TODO: Getter
//
//    public StockMarket getgetStockMarket(){
//        return this._stockMarket;
//    }
//
//    public NssCoreService(StockMarket stockMarket, boolean isStessTest, AppContext context,
//                   EventBus event) {
//        this._context = context;
//        this._event = event;
//        this._stockMarket = stockMarket;
//        this._isStressTest = isStessTest;
//    }
//
//   public NssCore getNssCore() {
//        return this._nssCore;
//    }
//
//    public void init() {
//        _nssCore = new NssCore();
//        getNssCore().setConfig(_getNssConfig());
//        initEventAdapters();
//    }
//
//    public CoreConfig _getNssConfig() {
//        // return HkConfig;
//        // ignore: unused_local_variable
//        CoreConfig refCoreConfig;
//        // _stockMarket == StockMarket.US
//        //     ? refCoreConfig = UsConfig
//        //     : (_isStressTest ? refCoreConfig = UsConfigStressTestConfig : refCoreConfig = HkConfig);
//
//        if (_stockMarket == StockMarket.US) {
//            if (_isStressTest) {
//                refCoreConfig = UsConfigStressTestConfig;
//            } else {
//                refCoreConfig = UsConfig;
//            }
//        }
//        if (_stockMarket == StockMarket.HK) {
//            if (_isStressTest) {
//                refCoreConfig = HkStressTestConfig;
//            } else {
//                refCoreConfig = HkConfig;
//            }
//        }
//        return refCoreConfig;
//        // return DefaultConfig;
//    }
//
//    void initCoreConfig(dynamic authService) {
//        // _nssCore.addAsaConfig(HkAsaConfig);
//        // _nssCore.addAsaConfig(ShAsaConfig);
//        // _nssCore.addAsaConfig(SzAsaConfig);
//        _stockMarket == StockMarket.US
//                ? _nssCore.addAsaConfig(UsAsaConfig)
//                : _nssCore.addAsaConfig(HkAsaConfig);
//
//        _stockMarket == StockMarket.US
//                ? null
//                : _nssCore.addAsaConfig(DefaultAsaConfig);
//        _nssCore.addDecoderConfig(DefaultDecoderConfig);
//        _nssCore.addProcessorConfig(DefaultProcessorConfig);
//    }
//
//    public void initEventAdapters() {
//        // this._subscription_hk.add(_nssCore
//        //     .getEventService()
//        //     .on<UserEvent>()
//        //     .listen((UserEvent userEvent) {
//        //   if (userEvent.event() == UserEvent.HttpLoginSuccess) {
//        //     _event.fire(
//        //         new UserEvent(UserEvent.HttpLoginSuccess, userEvent.data()));
//        //   } else if (userEvent.event() == UserEvent.HttpLoginFailed) {
//        //     _event.fire(
//        //         new UserEvent(UserEvent.HttpLoginFailed, userEvent.data()));
//        //   } else if (userEvent.event() == UserEvent.NssLoginSucess) {
//        //     _isNssSessionEnd = false;
//        //     _event.fire(
//        //         new UserEvent(UserEvent.NssLoginSucess, userEvent.data()));
//        //   } else if (userEvent.event() == UserEvent.NssLoginFailed) {
//        //     _isNssSessionEnd = true;
//        //     _event.fire(
//        //         new UserEvent(UserEvent.NssLoginFailed, userEvent.data()));
//        //   } else if (userEvent.event() == UserEvent.NssLoginDuplicated) {
//        //     _isNssSessionEnd = true;
//        //     _event.fire(
//        //         new UserEvent(UserEvent.NssLoginDuplicated, userEvent.data()));
//        //   } else if (userEvent.event() == UserEvent.NssLoginUnknown) {
//        //     _isNssSessionEnd = true;
//        //     _event.fire(
//        //         new UserEvent(UserEvent.NssLoginUnknown, userEvent.data()));
//        //   }
//        // }));
//        //
//        // this._subscription_hk.add(_nssCore
//        //     .getEventService()
//        //     .on<NssEvent>()
//        //     .listen((NssEvent nssEvent) {
//        //   if (nssEvent.event() == NssEvent.NssConnect) {
//        //     _isNssSessionEnd = false;
//        //     _event.fire(new NssEvent(NssEvent.NssConnect, nssEvent.data()));
//        //   } else if (nssEvent.event() == NssEvent.NssDisconnect) {
//        //     _isNssSessionEnd = true;
//        //     _event.fire(new NssEvent(NssEvent.NssDisconnect, nssEvent.data()));
//        //   } else if (nssEvent.event() == NssEvent.NssAsaProgress) {
//        //     _event.fire(new NssEvent(NssEvent.NssAsaProgress, nssEvent.data()));
//        //   } else if (nssEvent.event() == NssEvent.NssAsaLoad) {
//        //     _event.fire(new NssEvent(NssEvent.NssAsaLoad, nssEvent.data()));
//        //   } else if (nssEvent.event() == NssEvent.NssNewsLoad) {
//        //     _event.fire(new NssEvent(NssEvent.NssNewsLoad, nssEvent.data()));
//        //   } else if (nssEvent.event() == NssEvent.NssMaintenance) {
//        //     _event.fire(new NssEvent(NssEvent.NssMaintenance, nssEvent.data()));
//        //   } else if (nssEvent.event() == NssEvent.NssTime) {
//        //     _event.fire(new NssEvent(NssEvent.NssTime, nssEvent.data()));
//        //   } else if (nssEvent.event() == NssEvent.NssHeartbeat) {
//        //     _event.fire(new NssEvent(NssEvent.NssHeartbeat, nssEvent.data()));
//        //   } else if (nssEvent.event() == NssEvent.NssHeartbeatTimeout) {
//        //     _event.fire(
//        //         new NssEvent(NssEvent.NssHeartbeatTimeout, nssEvent.data()));
//        //   } else if (nssEvent.event() == NssEvent.NssData) {
//        //     _event.fire(new NssEvent(NssEvent.NssData, nssEvent.data()));
//        //   }
//        // }));
//
//        this._subscription_us.add(_nssCore
//                .getEventService()
//                .on < UserEvent > ()
//                .listen((UserEvent userEvent) {
//            if (userEvent.event() == UserEvent.HttpLoginSuccess) {
//                _event.fire(
//                        new UserEvent(UserEvent.HttpLoginSuccess, userEvent.data()));
//            } else if (userEvent.event() == UserEvent.HttpLoginFailed) {
//                _event.fire(
//                        new UserEvent(UserEvent.HttpLoginFailed, userEvent.data()));
//            } else if (userEvent.event() == UserEvent.NssLoginSucess) {
//                _isNssSessionEnd = false;
//                _event.fire(
//                        new UserEvent(UserEvent.NssLoginSucess, userEvent.data()));
//            } else if (userEvent.event() == UserEvent.NssLoginFailed) {
//                _isNssSessionEnd = true;
//                _event.fire(
//                        new UserEvent(UserEvent.NssLoginFailed, userEvent.data()));
//            } else if (userEvent.event() == UserEvent.NssLoginDuplicated) {
//                _isNssSessionEnd = true;
//                _event.fire(
//                        new UserEvent(UserEvent.NssLoginDuplicated, userEvent.data()));
//            } else if (userEvent.event() == UserEvent.NssLoginUnknown) {
//                _isNssSessionEnd = true;
//                _event.fire(
//                        new UserEvent(UserEvent.NssLoginUnknown, userEvent.data()));
//            }
//        }));
//
//        this._subscription_us.add(_nssCore
//                .getEventService()
//                .on < NssEvent > ()
//                .listen((NssEvent nssEvent) {
//            if (nssEvent.event() == NssEvent.NssConnect) {
//                _isNssSessionEnd = false;
//                _event.fire(new NssEvent(NssEvent.NssConnect, nssEvent.data()));
//            } else if (nssEvent.event() == NssEvent.NssDisconnect) {
//                _isNssSessionEnd = true;
//                _event.fire(new NssEvent(NssEvent.NssDisconnect, nssEvent.data()));
//            } else if (nssEvent.event() == NssEvent.NssAsaProgress) {
//                _event.fire(new NssEvent(NssEvent.NssAsaProgress, nssEvent.data()));
//            } else if (nssEvent.event() == NssEvent.NssAsaLoad) {
//                _event.fire(new NssEvent(NssEvent.NssAsaLoad, nssEvent.data()));
//            } else if (nssEvent.event() == NssEvent.NssNewsLoad) {
//                _event.fire(new NssEvent(NssEvent.NssNewsLoad, nssEvent.data()));
//            } else if (nssEvent.event() == NssEvent.NssMaintenance) {
//                _event.fire(new NssEvent(NssEvent.NssMaintenance, nssEvent.data()));
//            } else if (nssEvent.event() == NssEvent.NssTime) {
//                _event.fire(new NssEvent(NssEvent.NssTime, nssEvent.data()));
//            } else if (nssEvent.event() == NssEvent.NssHeartbeat) {
//                _event.fire(new NssEvent(NssEvent.NssHeartbeat, nssEvent.data()));
//            } else if (nssEvent.event() == NssEvent.NssHeartbeatTimeout) {
//                _event.fire(
//                        new NssEvent(NssEvent.NssHeartbeatTimeout, nssEvent.data()));
//            } else if (nssEvent.event() == NssEvent.NssData) {
//                _event.fire(new NssEvent(NssEvent.NssData, nssEvent.data()));
//            }
//        }));
//    }
//
//    void deatchEventAdapters() {
//        // _subscription_hk.forEach((StreamSubscription<AppEvent> subscription) {
//        //   subscription.cancel();
//        // });
//
//        _subscription_us.forEach((StreamSubscription < AppEvent > subscription) {
//            subscription.cancel();
//        });
//        // _subscription_hk.clear();
//        _subscription_us.clear();
//    }
//
//    boolean isShareConnection() {
//        return _isShareConnection;
//    }
//
//    void login(String username, String password, boolean encrypted) {
//        if (_isNssSessionEnd) {
//            nssCore.login(username, password, encrypted);
//        }
//    }
//
//    void loginIntegrated(String username, String token, dynamic result) {
//        // nssCore.loginIntegrated(username, token, result);
//    }
//
//    void createConnection() {
//        if (_nssCore.getContext().getUser().isSessionEnd()) {
//            log.info('no sesssion for nss');
//        }
//
//        log.info('get ip address');
//        http
//                .get(environment['apiUrl'] + environment['ipService'])
//                .then((http.Response result) {
//            Map<String, dynamic> jsonResult = json.decode(result.body);
//            ApiResponse apiResponse = ApiResponse.fromJson(jsonResult);
//            log.info('API Status' + apiResponse.status);
//            if (apiResponse.status == 'success') {
//                IPGeoInfo ipGeoInfo = apiResponse.data as IPGeoInfo;
//                if (ipGeoInfo.is_cn) {
//                    _nssCore.enableCensor(true);
//                }
//                log.info('get ip success is_cn=' + ipGeoInfo.is_cn.toString());
//            } else {
//                log.info('get ip recv incorrect status code');
//            }
//            _nssCore.connect();
//        }).catchError((Object error) {
//            log.info('get ip failed');
//
//
//            _nssCore.connect();
//        });
//    }
//
//    destroy() {
//        _nssCore.destroy();
//    }
//}
//
//
//class ApiResponse {
//    String _message;
//    String _status;
//    dynamic _data;
//
//    ApiResponse(this._message, this._status, this._data);
//
//    factory ApiResponse.
//
//    fromJson(Map<String, dynamic> data) {
//        return new ApiResponse(
//                data['message'],
//                data['status'],
//                IPGeoInfo.fromJson(data['data']),
//                );
//    }
//
//    String get
//    message =>_message;
//    String get
//    status =>_status;
//    dynamic get
//    data =>_data;
//}
//
//
//public class IPGeoInfo {
//    boolean _is_cn;
//    boolean _is_loopback;
//    String _type;
//    boolean _is_local;
//    String _ip;
//
//    IPGeoInfo(
//      this._is_cn, this._is_local, this._type, this._is_loopback, this._ip);
//
//    factory IPGeoInfo.
//
//    fromJson(Map<String, dynamic> data) {
//        return new IPGeoInfo(data['is_cn'], data['is_local'], data['type'],
//                data['is_loopback'], data['ip']);
//    }
//
//    boolean get
//    is_cn =>_is_cn;
//    boolean get
//    is_loopback =>_is_loopback;
//    String get
//    type =>_type;
//    boolean get
//    is_local =>_is_local;
//    String get
//    ip =>_ip;
//}
//
