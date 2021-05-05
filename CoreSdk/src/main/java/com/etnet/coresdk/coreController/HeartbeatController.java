package com.etnet.coresdk.coreController;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.etnet.coresdk.api.ContextProvider;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.events.NssEvent;
import com.etnet.coresdk.events.NssTime;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class HeartbeatController extends ContextProvider {
    final Logger log = Logger.getLogger("HeartbeatController");

    NssCoreContext _context;
    int _timeDiff = 0;
    int _localDate;
    Timer _mWatchDog;
    boolean _fed = false;
    int _beforeDate;
    Integer _lastFedTime;
    Timer _localDateUpdater;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    public void feedTheWatchDog(int serverTime) {
        Calendar t = Calendar.getInstance();
        final int currentLocalTime = (int) t.getTimeInMillis();
        _timeDiff = serverTime - currentLocalTime;
        _fed = true;
        _waitHungry();
        this._context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
            @Override
            public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                e.onNext(new NssEvent(NssEvent.NssHeartbeat, serverTime));
                e.onComplete();
            }
        });
//        _context.getEvents().getDefault().register(new NssEvent(NssEvent.NssHeartbeat, serverTime));
    }

    public void _waitHungry() {
        if (_mWatchDog != null) {
            _mWatchDog.cancel();
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (_context.getController().getNetworkController().isSessionEnd()) {
                    if (!_fed) {
                        if (_lastFedTime == null) {
                            log.info("this is not your dog... forgot to bring a watch dog?");
                            return;
                        }
                        Calendar t = Calendar.getInstance();
                        Date da = t.getTime();
                        final int currentTime = (int) t.getTimeInMillis() - _lastFedTime;
                        log.info("The dog is not being fed, hungry for " +
                                currentTime +
                                " ms");
                        _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                            @Override
                            public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                e.onNext(new NssEvent(NssEvent.NssHeartbeatTimeout, null));
                                e.onComplete();
                            }
                        });
//                        _context.getEvents().getDefault().register(new NssEvent(NssEvent.NssHeartbeatTimeout, null));
                    }
                    _fed = false;
                }
                _waitHungry();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 15);
    }

    public void startWatchDog() {
        if (_mWatchDog != null) {
            log.info("watchdog is already running");
        } else {
            Calendar t = Calendar.getInstance();
            _beforeDate = (int) t.getTimeInMillis();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Calendar t = Calendar.getInstance();
                    _localDate = (int) Calendar.getInstance().getTimeInMillis();
                    final int elapsed = _localDate - _beforeDate;
                    if (elapsed > 1500) {
                        log.info(" Timer drop time segment, elapsed = " + elapsed);
                    } else {
                        final int fixedTimestamp = _localDate + _timeDiff;

                        if (_context.getController().getNetworkController().isSessionEnd()) {
                            _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                                @Override
                                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                                    e.onNext(new NssEvent(
                                            NssEvent.NssTime, new NssTime(fixedTimestamp, _timeDiff)));
                                    e.onComplete();
                                }
                            });
//                            _context.getEvents().getDefault().register(new NssEvent(
//                                    NssEvent.NssTime, new NssTime(fixedTimestamp, _timeDiff)));
                        }
                    }

                    _beforeDate = _localDate;
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 1);


            _lastFedTime = _beforeDate;
            _waitHungry();
        }
    }

    public void stopWatchDog() {
        if (_localDateUpdater != null) {
            _localDateUpdater.cancel();
        }
        _localDateUpdater = null;

        if (_mWatchDog != null) {
            _mWatchDog.cancel();
        }
        _mWatchDog = null;
    }
}
