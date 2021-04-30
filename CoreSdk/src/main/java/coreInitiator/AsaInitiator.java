package coreInitiator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import api.ContextProvider;
import constants.EndPoints;
import coreConfigInterface.AsaConfigInfo;
import coreEnvironment.Environment;
import coreModel.AsaDecoder;
import coreModel.Decoder;
import coreModel.NssCoreContext;
import coreModel.QuoteData;
import coreModel.RawData;
import coreSubscriber.SubscriberJava;
import coreSubscriber.listener.UpdateListener;
import coreSubscriber.request.HttpRequest;
import coreSubscriber.request.QuoteRequest;
import events.AsaProgressStatus;
import events.NssEvent;
import events.NssTime;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

import static constants.AsaConstant.RESOURCE_DISCLAIMERS;
import static constants.AsaConstant.RESOURCE_INSTRUMENT_NAME;
import static constants.AsaConstant.RESOURCE_SEARCH_LIST;

public class AsaInitiator extends ContextProvider implements UpdateListener {
    final Logger log = Logger.getLogger("AsaInitiator");
    NssCoreContext _context;
    SubscriberJava _subscriberJava;
    AsaConfigInfo _asaConfig;
    Map<String, Decoder> _decoderPool = new HashMap<String, Decoder>();
    Map<String, List<String>> _codeList = new HashMap<String, List<String>>(); // name => array
    List<Object> _streamCodeList = new ArrayList<Object>();
    List<String> _httpCodeList = Arrays.asList(RESOURCE_INSTRUMENT_NAME,
            RESOURCE_SEARCH_LIST,
            RESOURCE_DISCLAIMERS);
    Map<String, String> _httpCodeEndpoints = new HashMap<String, String>();
    boolean _freshLoad = true;
    int _dataLoadedCount = 0;
    int _totalDataCount = 0;
    int _lastLoadTime = 0;

    public AsaInitiator() {
        _httpCodeEndpoints.put(RESOURCE_INSTRUMENT_NAME, EndPoints.ENDPOINT_ASSETSJSON);
        _httpCodeEndpoints.put(RESOURCE_SEARCH_LIST, EndPoints.ENDPOINT_SCREENSJSON);
        _httpCodeEndpoints.put(RESOURCE_DISCLAIMERS, EndPoints.ENDPOINT_DISCLAIMERS);
    }

    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    public void init() {
        _asaConfig = _context.getAsaConfig();
        if (Environment.isDebug()) {
            log.info("asaConfig: ${_asaConfig}");
        }


        for (Map.Entry<String, Map<String, AsaDecoder>> entry : _asaConfig.getConfigs().entrySet()) {
            _codeList.put(entry.getKey(), (List<String>) entry.getValue().keySet());
        }

        _streamCodeList = Collections.singletonList(_asaConfig.getStreamList());
        if (Environment.isDebug()) {
            log.info("initDecoders: ${_codeList}");
        }
        _subscriberJava = new SubscriberJava("asa-initiator", this);
    }

    public boolean anyAsaData() {
        boolean neeedSubscribe = false;
        for (List<String> list : _codeList.values()){
            if (list.size() > 0) {
                neeedSubscribe = true;
                break;
            }
        }
        return neeedSubscribe;
    }

    public void active() {
        log.info("start initiator");
        _dataLoadedCount = 0;

        if (!_freshLoad) {
            Calendar t = Calendar.getInstance();
            int currTime = (int) t.getTimeInMillis();
            // 1hour cache for snapshot asa data
            if ((_lastLoadTime - currTime) > 3600000) {
                // cache valid for one hour
                log.info("cache expired, reload all asa");
                _freshLoad = true;
            } else {
                log.info("snapshot quote load from cached");
            }
        }

        // start subscribe by asa field and codes
        _totalDataCount = 0;
        int _cachedDataCount = 0;

        for (Map.Entry<String, List<String>> entry : _codeList.entrySet()) {
            String field = entry.getKey();
            List<String> list = entry.getValue();
            for (String code : list){
                log.info("load code" + code);
                if (_httpCodeList.indexOf(code) != -1) {
                    log.info("load from http");
                    String endPoint = _httpCodeEndpoints.get(code);
                    HttpRequest jsonHR = _context
                            .getController()
                            .getRequestController()
                            .createHttpRequest(endPoint, code, field, new HashMap<String, String>());
                    _totalDataCount++;
                    _subscriberJava.subscribe(jsonHR);
                } else {
                    if (_streamCodeList.contains(code)) {
                        log.info("load from nss stream");
                        QuoteRequest streamQR = _context
                                .getController()
                                .getRequestController()
                                .createStreamQuoteRequest(Arrays.asList(code), Arrays.asList(field));
                        _totalDataCount++;
                        _subscriberJava.subscribe(streamQR);
                    } else {
                        if (_freshLoad == true) {
                            log.info("load from nss snapshot");
                            QuoteRequest snapQR = _context
                                    .getController()
                                    .getRequestController()
                                    .createSnapshotQuoteRequest(Arrays.asList(code), Arrays.asList(field));
                            _totalDataCount++;
                            _subscriberJava.subscribe(snapQR);
                        } else {
                            // keep using cache
                            _totalDataCount++;
                            _cachedDataCount++;
                            log.info("already exists in cache");
                        }
                    }
                }
            }
        }

        if (_cachedDataCount == _totalDataCount) {
            log.info("no asa will be subscribed again, asa loaded");
            _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                @Override
                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                    e.onNext(new NssEvent(NssEvent.NssAsaLoad, _lastLoadTime));
                    e.onComplete();
                }
            });
//            _context.getEvents().getDefault().register(new NssEvent(NssEvent.NssAsaLoad, _lastLoadTime));
        }
    }

    @Override
    public void onDataUpdated(Object data) {
        List<QuoteData> bundle = (List<QuoteData>) data;
        // List<String> codes = _codeList[bundle[0].getNssData().getFieldID()];
        int totalDataTask = _totalDataCount;
        for (QuoteData data1 : bundle){
            int benchStartTime = (int) Calendar.getInstance().getTimeInMillis();
            String code = data1.getCode();
            String fieldID = data1.getNssData().getFieldID();
            Decoder decoder = getDecoder(code, fieldID);
            RawData rawData = new RawData(code);
            rawData.setFieldID(fieldID);
            rawData.setServerFieldId(fieldID);
            rawData.setData(data1.getNssData().getData());
            rawData.setSnapshot(false);

            decoder.decodeStream(code, rawData);

            _dataLoadedCount++;
            int finishedTasks = _dataLoadedCount;
            int percent = Math.round(finishedTasks / totalDataTask * 100);
            // set timeout
            // Timer(
            //     Duration(seconds: 0),
            //     () => {
            final AsaProgressStatus progress =
            new AsaProgressStatus(code, finishedTasks, totalDataTask, percent);
            _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                @Override
                public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                    e.onNext(new NssEvent(NssEvent.NssAsaProgress, progress));
                    e.onComplete();
                }
            });
//            _context.getEvents().getDefault().register(new NssEvent(NssEvent.NssAsaProgress, progress));
            int benchEndTime = (int) Calendar.getInstance().getTimeInMillis();
            int timeUsed = benchEndTime - benchStartTime;
            log.info("progress code:" +
                    progress.getCode() +
                    " " +
                    progress.getFinishedTasks() +
                    "/" +
                    progress.getTotalDataTasks() +
                    "(" +
                    progress.getPercent() +
                    ")" +
                    " time used=" +
                    timeUsed +
                    "ms");
            if (_dataLoadedCount >= totalDataTask) {
                log.info("initiator completed");
                _freshLoad =
                        false; // will not load snap quote anymore within this session
                _lastLoadTime = (int) Calendar.getInstance().getTimeInMillis();
                _context.getObservable().create(new ObservableOnSubscribe<NssEvent>() {
                    @Override
                    public void subscribe(ObservableEmitter<NssEvent> e) throws Exception {
                        e.onNext(new NssEvent(NssEvent.NssAsaLoad, _lastLoadTime));
                        e.onComplete();
                    }
                });
//                _context.getEvents().getDefault().register(new NssEvent(NssEvent.NssAsaLoad, _lastLoadTime));
            }
        }
    }

    public Decoder getDecoder(String code, String fieldId) {
        Decoder decoder = new Decoder();
        if (_asaConfig.getConfigs().containsKey(fieldId)) {
            Map<String, AsaDecoder> field = _asaConfig.getConfigs().get(fieldId);
            if (field.containsKey(code)) {
                AsaDecoder decoderInfo = field.get(code);
                if (_decoderPool.containsKey(decoderInfo.getUniqueId())) {
                    decoder = _decoderPool.get(decoderInfo.getUniqueId());
                } else {
                    decoder = decoderInfo.ifAbsent();
                    decoder.setContext(_context);
                    _decoderPool.put(decoderInfo.getUniqueId(), decoder);
                }
            }
        }
        return decoder;
    }

    public void cleanUp() {
        if (_freshLoad) {
            _dataLoadedCount = 0;
            _context.getAsaStorage().clear();
        }
    }

    public void inactive() {
        log.info("initiator inactive");
        if (_context.getController().getNetworkController().isConnectionPaused()) {
            log.info("connection being paused, asa data will preserve for a period.");
        } else {
            _freshLoad = true; // hard reset state
        }

        for (Map.Entry<String, List<String>> entry : _codeList.entrySet()) {
             String field = entry.getKey();
            List<String> list = entry.getValue();
            for (String code : list){
                if (_streamCodeList.contains(code)) {
                    QuoteRequest streamQR = _context
                            .getController()
                            .getRequestController()
                            .createRemoveQuoteRequest(Arrays.asList(code), Arrays.asList(field));
                    _subscriberJava.unsubscribe(streamQR);
                }
            }
        }
    }
}

