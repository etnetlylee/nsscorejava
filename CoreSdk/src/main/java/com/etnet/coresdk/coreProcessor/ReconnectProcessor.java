package com.etnet.coresdk.coreProcessor;

import java.util.List;
import java.util.logging.Logger;

import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreModel.NssPacket;
import com.etnet.coresdk.coreModel.Processor;
import com.etnet.coresdk.events.NssEvent;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class ReconnectProcessor extends Processor {
    public static final String id = "reconnect";
    final Logger log = Logger.getLogger("ReconnectProcessor");

    NssCoreContext _context;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    @Override
    public Object process(NssPacket nssPacket) {
        log.info("reconnect-processor executed");
        final List<List<String>> _temp = (List<List<String>>) nssPacket.getParsedBody();
        final List<String> csvData = (List<String>) _temp.get(0);
        final String arrays = csvData.get(1);
        if (arrays.charAt(0) == '0') {
            return new NssEvent(NssEvent.NssMaintenance, csvData);
        }
        return null;
    }

    @Override
    public void notify(NssPacket nssPacket, Object data) {
        boolean _tempData = (boolean) data;
        if (_tempData) {
            this._context.getObservable().create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> e) throws Exception {
                    e.onNext(data);
                    e.onComplete();
                }
            });
//            _context.getEvents().getDefault().register(data);
        }
    }
}
