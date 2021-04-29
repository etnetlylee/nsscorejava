import java.util.ArrayList;
import java.util.logging.Logger;

import coreInitiator.AsaInitiator;
import coreModel.NssCoreContext;
import events.AppEvent;

public class NssCore {
    final Logger log = Logger.getLogger("Core");

    NssCoreContext _context;
    AsaInitiator _asaInitiator;
    // NewsInitiator _newsInitiator;
    List<StreamSubscription<AppEvent>> _event = new ArrayList<Object>();
    // SubscriberFactory subscriberFactory;
    // Helper helper;
    int _asaReloadCount = 0;

    public NssCore() {
        _context = new NssCoreContext();
    }

    public void _init() {
        log.info("init, attach events");
        _event.add(_context.events.on<UserEvent>().listen((UserEvent userEvent) {
            if (userEvent.event() == UserEvent.HttpLoginSuccess) {
                LoginResponse response = userEvent.data() as LoginResponse;
                String username = response.getUserId();
                String webToken = response.getToken();
                String nssToken = response.getToken();
                User user = new User(username, webToken, nssToken);
                user.setAuth(response.isValid());
                user.resetSession(); // should use new nss session.
                _context.setUser(user);
            } else if (userEvent.event() == UserEvent.NssLoginSucess) {
                log.info("user login success from nss: " + userEvent.data().toString());
                _context.events.fire(NssEvent(NssEvent.NssReconnect, null));
            } else if (userEvent.event() == UserEvent.NssLoginDuplicated) {
                this.destroy();
            }
        }));

        _event.add(_context.events.on<NssEvent>().listen((NssEvent nssEvent) {
            if (nssEvent.event() == NssEvent.NssAsaLoad) {
                // init news
                _asaReloadCount++;
                log.info(" ---------- ASA load complete -------------" +
                        _asaReloadCount.toString());

                _context.events.fire(new NssEvent(
                        NssEvent.NssNewsLoad, DateTime.now().millisecondsSinceEpoch));
            } else if (nssEvent.event() == NssEvent.NssReconnect) {
                log.info("Reconnect");
                log.info("start watch dog");
                _context.getController().getHeartbeatController().startWatchDog();
                log.info("broadcast cleanup event");
                _context.events.fire(new NssEvent(NssEvent.NssCleanup, null));

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
                        getConfig().initiator.useASA == true) {
                    _asaInitiator.active();
                } else {
                    // Invoke asa_load event
                    // const progrsss = { code: "", finishedTasks: 1, percent: 1 };
                    _context.events.fire(new NssEvent(NssEvent.NssAsaProgress, {}));
                    _context.events
                            .fire(new NssEvent(NssEvent.NssAsaLoad, DateTime.now()));
                }
            } else if (nssEvent.event() == NssEvent.NssDisconnect) {
                // if (getConfig().initiator.useNews == true && _newsInitiator  != null) {
                //   _newsInitiator.inactive();
                // }
                if (getConfig().initiator.useASA == true && _asaInitiator != null) {
                    _asaInitiator.inactive();
                }

                log.info("clear storage");
                _context.getAsaStorage().clear();
                _context.getStorage().clear();

                log.info("clear watch dog");
                _context.getController().getHeartbeatController().stopWatchDog();
            } else if (nssEvent.event() == NssEvent.NssCleanup) {}
        }));

        // _helper = new Helper(_context);
        // _subscriberFactory = new SubscriberFactory(_context);

        _setupTimezone();
    }

    resetState() {
        _context.getAsaStorage().clear();
        _context.getStorage().clear();
        _init();
    }

    Future<Null> _setupTimezone() async {
        // await initializeTimeZone();
        // final kHongKong = getLocation("Asia/Hong_Kong");
        // setLocalLocation(kHongKong);
        // TZDateTime.local(2018);

        // log.info(Location);

        // // TZDateTime
        // final TimeZone timeZone =
        //     kHongKong.timeZone(DateTime.now().millisecondsSinceEpoch);
        // log.info(timeZone.toString());
        // log.info(new TZDateTime.from(DateTime.now()))
    }

    NssCoreContext getContext() {
        return _context;
    }

    EventBus getEventService() {
        return _context.events;
    }

    CoreConfig getConfig() {
        return _context.getConfig();
    }

    void setConfig(CoreConfig config) {
        _context.setConfig(config);
    }

    enableCensor(bool enable) {
        _context.getConfig().enableCensor = enable;
    }

    addAsaConfig(AsaDecodersConfig config) {
        _context.addAsaConfig(config);
    }

    addDecoderConfig(Map<String, DecoderInfo> decoderConfig) {
        _context.addDecoderConfig(decoderConfig);
    }

    addProcessorConfig(Map<String, ProcessorInfo> processorConfig) {
        _context.addProcessorConfig(processorConfig);
    }

    connect() {
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
            String nssUrl = _context.getConfig().server.wss;
            _context
                    .getController()
                    .getNetworkController()
                    .getConnection()
                    .retryCount(9999)
                    .connect(ConnectOptions(_context.getUser(), "US", nssUrl, false)); // TODO: hard coded
        } else {
            log.info("user object is null");
        }
    }

    disconnect() {
        if (_context.getController().getNetworkController().isConnectionExists()) {
            _context
                    .getController()
                    .getNetworkController()
                    .getConnection()
                    .disconnect(1000, "close user session");
        }
    }

    void login(String username, String password, [bool encrypted]) {
        resetState(); // connection and event may be destroyed, we should reset to a fresh state
        if (username != null && password != null) {
            _context
                    .getController()
                    .getCommandController()
                    .sendHttpLoginCommand(username, password, encrypted);
        }
    }

    void loginIntegrated(String username, String token, dynamic result) {
        if (username != null && token != null) {
            // _context
            //   .getController()
            //   .getCommandController()
            //   .sendIntegratedLoginCommand(username, token, result);
        }
    }

    destroy() {
        log.info("destroy session");
        disconnect();

        log.info("unsubscribe events");
        _event.forEach((subscrption) {
                subscrption.cancel();
    });
        _event.clear();
    }

    void resumeConnection() {
        _context.getController().getNetworkController().resumeConnection();
    }

    void pauseConnection() {
        _context.getController().getNetworkController().pauseConnection();
    }

    bool isConnectionPaused() {
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

    verbose() {
        // Map<String, Map<String, QuoteData>>
        log.info(" =========[ Verbose ]=================================");
        _context
                .getStorage()
                .quoteStore
                .forEach((String code, Map<String, QuoteData> quoteDataMap) {
            quoteDataMap.forEach((String fieldId, QuoteData quoteData) {
                quoteData.getSubscription().forEach((Subscription s) {
                    log.info(" code=" +
                            code +
                            " fieldId=" +
                            fieldId +
                            " subscription=" +
                            s.getSubsciber().viewId);
                });
            });
        });
        log.info(" =====================================================");
    }
}
