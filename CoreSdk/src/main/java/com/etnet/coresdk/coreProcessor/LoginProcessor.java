package com.etnet.coresdk.coreProcessor;

import java.util.Map;
import java.util.logging.Logger;

import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreModel.NssPacket;
import com.etnet.coresdk.coreModel.Processor;
import com.etnet.coresdk.events.UserEvent;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class LoginProcessor extends Processor {
    public static final String id = "login";
    public static final String LOGIN_SUCESS = "0";
    public static final String LOGIN_FAIL = "1";
    public static final String LOGIN_DUPLICATE = "2";
    final Logger log = Logger.getLogger("LoginProcessor");

    NssCoreContext _context;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    @Override
    public Object process(NssPacket nssPacket) {
        final Map<Integer, Map<Integer, String>> _temp = (Map<Integer, Map<Integer, String>>) nssPacket.getParsedBody();
        final Map<Integer, String> content = _temp.get(0);
        UserEvent userEvent;
        if (content.size() == 1) {
            log.info("status code " + content.get(0));

            if (content.get(0) == LoginProcessor.LOGIN_SUCESS) {
                log.info("NssLoginSucess");
                userEvent = new UserEvent(UserEvent.NssLoginSucess, content.get(0));
            } else if (content.get(0) == LoginProcessor.LOGIN_FAIL) {
                log.info("NssLoginFailed");
                userEvent = new UserEvent(UserEvent.NssLoginFailed, content.get(0));
            } else if (content.get(0) == LoginProcessor.LOGIN_DUPLICATE) {
                log.info("NssLoginDuplicated");
                userEvent = new UserEvent(UserEvent.NssLoginDuplicated, content.get(0));
            } else {
                log.info("NssLoginUnknown");
                userEvent = new UserEvent(UserEvent.NssLoginUnknown, content.get(0));
            }
        } else {
            log.info("NssLoginUnknown");
            userEvent = new UserEvent(UserEvent.NssLoginUnknown, content.get(0));
        }
        return userEvent;
    }

    @Override
    public void notify(NssPacket nssPacket, Object data) {
        UserEvent userEvent = (UserEvent) data;
        log.info("login state updated: " + userEvent.data().toString());
        if (userEvent.data() != LoginProcessor.LOGIN_SUCESS) {
            log.info("login state changed to not success, session end");
            _context.getUser().endSession();
            _context.getController().getNetworkController().detachConnection();
            _context.getController().getNetworkController().onDisConnected(1000, "duplicated login");
        }
        this._context.getObservable().create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                e.onNext(data);
                e.onComplete();
            }
        });
//        _context.getEvents().getDefault().register(data);
    }
}
