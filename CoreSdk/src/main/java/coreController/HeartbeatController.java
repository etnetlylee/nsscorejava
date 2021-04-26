package coreController;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import api.ContextProvider;
import coreModel.NssCoreContext;
import events.NssEvent;

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
        // todo : need to discuss EventBus
//        _context.events.fire(new NssEvent(NssEvent.NssHeartbeat, serverTime));
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
                        // todo : need to discuss EventBus
//                    _context.events.fire(new NssEvent(NssEvent.NssHeartbeatTimeout, null));
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
      const oneSec = const Duration(seconds:1);
            _localDateUpdater = new Timer.periodic(oneSec, (Timer t) {
                _localDate =DateTime.now().millisecondsSinceEpoch;
                final elapsed =_localDate -_beforeDate;
        if(elapsed >1500)

                {
                    log.info(" Timer drop time segment, elapsed = ", elapsed);
                } else

                {
                    final int fixedTimestamp = _localDate + _timeDiff;

                    if (_context.getController().getNetworkController().isSessionEnd())
                        _context.events.fire(new NssEvent(
                                NssEvent.NssTime, new NssTime(fixedTimestamp, _timeDiff)));
                }

                _beforeDate =_localDate;
            });
            _lastFedTime = _beforeDate;
            _waitHungry();
        }
    }

    stopWatchDog() {
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
