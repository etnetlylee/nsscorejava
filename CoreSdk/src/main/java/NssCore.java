import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import coreConfigInterface.AsaDecodersConfig;
import coreConfigInterface.CoreConfig;
import coreInitiator.AsaInitiator;
import coreInterfaceLogin.LoginResponse;
import coreModel.DecoderInfo;
import coreModel.NssCoreContext;
import coreModel.ProcessorInfo;
import coreModel.QuoteData;
import coreModel.User;
import events.AppEvent;
import events.UserEvent;
import events.NssEvent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import network.ConnectOptions;

import static android.service.controls.ControlsProviderService.TAG;

public class NssCore {
    final Logger log = Logger.getLogger("Core");

    NssCoreContext _context;
    AsaInitiator _asaInitiator;
    // NewsInitiator _newsInitiator;
//    List<StreamSubscription<AppEvent>> _event = new ArrayList<Object>();
//    List<Observable> _event = new ArrayList<Observable>();
    CompositeDisposable _event = new CompositeDisposable();
    // SubscriberFactory subscriberFactory;
    // Helper helper;
    int _asaReloadCount = 0;

    public NssCore() {
        _context = new NssCoreContext();
    }

    public void _init() {
        log.info("init, attach events");
        Disposable subscribe1 = _context.getObservable().subscribe(
                new Consumer<UserEvent>() {
                    @Override
                    public void accept(@NonNull UserEvent userEvent) throws Exception {
                        if (userEvent.event() == UserEvent.HttpLoginSuccess) {
                            LoginResponse response = (LoginResponse) userEvent.data();
                            String username = response.getUserId();
                            String webToken = response.getToken();
                            String nssToken = response.getToken();
                            User user = new User(username, webToken, nssToken);
                            user.setAuth(response.isValid());
                            user.resetSession(); // should use new nss session.
                            _context.setUser(user);
                        } else if (userEvent.event() == UserEvent.NssLoginSucess) {
                            log.info("user login success from nss: " + userEvent.data().toString());
                            _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssReconnect, null));
                                    e.onComplete();
                                }
                            });
//                            _context.events.fire(new NssEvent(NssEvent.NssReconnect, null));
                        } else if (userEvent.event() == UserEvent.NssLoginDuplicated) {
                            destroy();
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
                });

        Disposable subscribe2 = _context.getObservable().subscribe(
                new Consumer<NssEvent>() {
                    @Override
                    public void accept(@NonNull NssEvent nssEvent) throws Exception {
                        if (nssEvent.event() == NssEvent.NssAsaLoad) {
                            // init news
                            _asaReloadCount++;
                            log.info(" ---------- ASA load complete -------------" +
                                    _asaReloadCount);

                            _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(
                                            NssEvent.NssNewsLoad, (int) Calendar.getInstance().getTimeInMillis()));
                                    e.onComplete();
                                }
                            });
                        } else if (nssEvent.event() == NssEvent.NssReconnect) {
                            log.info("Reconnect");
                            log.info("start watch dog");
                            _context.getController().getHeartbeatController().startWatchDog();
                            log.info("broadcast cleanup event");
                            _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(NssEvent.NssCleanup, null));
                                    e.onComplete();
                                }
                            });
                            // news
                            // if (!_newsInitiator) {
                            //   _newsInitiator = new NewsInitiator();
                            //   _newsInitiator.setContext(_context);
                            //   _newsInitiator.init();
                            // }
                            // this.newsInitiator.cleanup();

                            // asa
                            if (_asaInitiator == null) {
                                // only create initiator when it is not exists
                                _asaInitiator = new AsaInitiator();
                                _asaInitiator.setContext(_context);
                                _asaInitiator.init();
                            }
                            _asaInitiator.cleanUp();

                            if (_asaInitiator.anyAsaData() &&
                                    getConfig().getInitiator().getUseASA() == true) {
                                _asaInitiator.active();
                            } else {
                                // Invoke asa_load event
                                // const progrsss = { code: "", finishedTasks: 1, percent: 1 };
                                _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                        e.onNext(new NssEvent(NssEvent.NssAsaProgress, new HashMap<>()));
                                        e.onComplete();
                                    }
                                });
                                _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                        e.onNext(new NssEvent(NssEvent.NssAsaLoad, Calendar.getInstance().getTime()));
                                        e.onComplete();
                                    }
                                });
                            }
                        } else if (nssEvent.event() == NssEvent.NssDisconnect) {
                            // if (getConfig().initiator.useNews == true && _newsInitiator  != null) {
                            //   _newsInitiator.inactive();
                            // }
                            if (getConfig().getInitiator().getUseASA() == true && _asaInitiator != null) {
                                _asaInitiator.inactive();
                            }

                            log.info("clear storage");
                            _context.getAsaStorage().clear();
                            _context.getStorage().clear();

                            log.info("clear watch dog");
                            _context.getController().getHeartbeatController().stopWatchDog();
                        } else if (nssEvent.event() == NssEvent.NssCleanup) {
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
                });

        _event.add(subscribe1);
        _event.add(subscribe2);
        // _helper = new Helper(_context);
        // _subscriberFactory = new SubscriberFactory(_context);

//        _setupTimezone();
    }

    public void resetState() {
        _context.getAsaStorage().clear();
        _context.getStorage().clear();
        _init();
    }

//    Future<Null> _setupTimezone() async {
//        // await initializeTimeZone();
//        // final kHongKong = getLocation("Asia/Hong_Kong");
//        // setLocalLocation(kHongKong);
//        // TZDateTime.local(2018);
//
//        // log.info(Location);
//
//        // // TZDateTime
//        // final TimeZone timeZone =
//        //     kHongKong.timeZone(DateTime.now().millisecondsSinceEpoch);
//        // log.info(timeZone.toString());
//        // log.info(new TZDateTime.from(DateTime.now()))
//    }

    public NssCoreContext getContext() {
        return _context;
    }

//    EventBus getEventService() {
//        return _context.events;
//    }

    public CoreConfig getConfig() {
        return _context.getConfig();
    }

    public void setConfig(CoreConfig config) {
        _context.setConfig(config);
    }

    public void enableCensor(boolean enable) {
        _context.getConfig().setEnableCensor(enable);
    }

    public void addAsaConfig(AsaDecodersConfig config) {
        _context.addAsaConfig(config);
    }

    public void addDecoderConfig(Map<String, DecoderInfo> decoderConfig) {
        _context.addDecoderConfig(decoderConfig);
    }

    public void addProcessorConfig(Map<String, ProcessorInfo> processorConfig) {
        _context.addProcessorConfig(processorConfig);
    }

    public void connect() {
        // create connection based on user permissions
        if (!_context.getController().getNetworkController().isConnectionExists()) {
            log.info("init connection");
            _context.getController().getNetworkController().initConnection();
        }

        if (_context.getUser() != null) {
            log.info("user object exists");
            // if (_nssCore.getContext().getUser().isSessionEnd()) {
            //   log.info("checked, user session end, create new connection");
            // }
            // If login http success, then connect to nss
            String nssUrl = _context.getConfig().getServer().getWss();
            _context
                    .getController()
                    .getNetworkController()
                    .getConnection()
                    .retryCount(9999)
                    .connect(new ConnectOptions(_context.getUser(), "US", nssUrl, false)); // TODO: hard coded
        } else {
            log.info("user object is null");
        }
    }

    public void disconnect() {
        if (_context.getController().getNetworkController().isConnectionExists()) {
            _context
                    .getController()
                    .getNetworkController()
                    .getConnection()
                    .disconnect(1000, "close user session");
        }
    }

    public void login(String username, String password, boolean encrypted) {
        resetState(); // connection and event may be destroyed, we should reset to a fresh state
        if (username != null && password != null) {
            _context
                    .getController()
                    .getCommandController()
                    .sendHttpLoginCommand(username, password, encrypted);
        }
    }

    public void loginIntegrated(String username, String token, Object result) {
        if (username != null && token != null) {
            // _context
            //   .getController()
            //   .getCommandController()
            //   .sendIntegratedLoginCommand(username, token, result);
        }
    }

    public void destroy() {
        log.info("destroy session");
        disconnect();

        log.info("unsubscribe events");
        _event.clear();
    }

    public void resumeConnection() {
        _context.getController().getNetworkController().resumeConnection();
    }

    public void pauseConnection() {
        _context.getController().getNetworkController().pauseConnection();
    }

    public boolean isConnectionPaused() {
        return _context.getController().getNetworkController().isConnectionPaused();
    }

    // getNewsContent(String type, String lang,String newsId, NewsSubscriber subscriber) {
    //   _context
    //     .getController()
    //     .getCommandController()
    //     .sendGetNewsContentCommand(type, lang, newsId, subscriber);
    // }

    // Helper getHelper() {
    //   return _helper;
    // }

    // SubscriberFactory getSubscriberFactory() {
    //   return _subscriberFactory;
    // }

    public void verbose() {
        // Map<String, Map<String, QuoteData>>
        log.info(" =========[ Verbose ]=================================");
        Map<String, Map<String, QuoteData>> _map = _context
                .getStorage()
                .getQuoteStore();


        for (Map.Entry<String, Map<String, QuoteData>> entry : _map.entrySet()) {
            String code = entry.getKey();
            Map<String, QuoteData> quoteDataMap = entry.getValue();
            for (Map.Entry<String, QuoteData> entry1 : quoteDataMap.entrySet()) {
                String fieldId = entry1.getKey();
                QuoteData quoteData = entry1.getValue();
                List<coreModel.Subscription> subscription = quoteData.getSubscription();
                for (coreModel.Subscription s : subscription) {
                    log.info(" code=" +
                            code +
                            " fieldId=" +
                            fieldId +
                            " subscription=" +
                            s.getSubsciber().getViewId());
                }

            }
        }
        log.info(" =====================================================");
    }
}
