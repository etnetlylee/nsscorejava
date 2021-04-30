package coreModel;

//import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import config.AsaConfig;
import config.ProcessorConfig;
import coreConfig.DecoderConfig;
import coreConfigInterface.AsaConfigInfo;
import coreConfigInterface.AsaDecodersConfig;
import coreConfigInterface.CoreConfig;
import coreController.Controller;
import coreEnvironment.Environment;
import coreStorage.Storage;
import coreStorage.StorageDecodeDispatcher;
import coreStorage.model.AsaStorage;
import events.AppEvent;
import events.NssEvent;
import events.UserEvent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;

public class NssCoreContext {
    AsaConfig _asaConfig;
    ProcessorConfig _processorConfig;
    CoreConfig _config;

    Controller _controller;
    Storage _storage;
    User _user;
    //    EventBus _events;
    StorageDecodeDispatcher _storageDecodeDispatcher;
    AsaStorage _asaStorage;
    DecoderConfig _decoderConfig;
    Observable _mObservable;

    public NssCoreContext() {
        _controller = new Controller();
        _controller.setContext(this);
//        _events = new EventBus();
        _asaConfig = new AsaConfig();
        _processorConfig = new ProcessorConfig();
        _decoderConfig = new DecoderConfig();
        _storage = new Storage();
        _storage.setContext(this);
        _storageDecodeDispatcher = new StorageDecodeDispatcher();
        _storageDecodeDispatcher.setContext(this);
        _asaStorage = new AsaStorage();
        _mObservable = new Observable() {
            @Override
            protected void subscribeActual(@NonNull Observer observer) {

            }
        };

    }

    public void setConfig(CoreConfig config) {
        _config = config;
        Environment.mIsDebug = _config._debug || false;
    }

    public CoreConfig getConfig() {
        return this._config;
    }

    public void addAsaConfig(AsaDecodersConfig asa) {
        _asaConfig.addConfig(asa);
    }

    public AsaConfigInfo getAsaConfig() {
        return _asaConfig.getConfig();
    }

    public Controller getController() {
        return _controller;
    }

    public Storage getStorage() {
        return _storage;
    }

    public AsaStorage getAsaStorage() {
        return _asaStorage;
    }

    public boolean getRandWSIP() {
        return _config.getUseRandomWSIP();
    }

    public void enableCensored(String region) {
        _config.setRegion(region);
    }

    public String getMarket() {
        return _config.getMarket();
    }

    public String getUMSUrl() {
        return _config.getServer().getUms();
    }

    public String getProductName() {
        return _config.getProductName();
    }

    public User getUser() {
        return _user;
    }

    public void setUser(User user) {
        _user = user;
    }

//    public EventBus getEvents(){
//      return  this._events;
//    }
    public Observable getObservable(){
      return  this._mObservable;
    }

    public StorageDecodeDispatcher getStorageDecoderDispatcher() {
        return _storageDecodeDispatcher;
    }

    public void addProcessorConfig(Map<String, ProcessorInfo> processorConfig) {
        _processorConfig.setConfig(processorConfig);
    }

    public Map<String, ProcessorInfo> getProcessorConfig() {
        return _processorConfig.getConfig();
    }

    public DecoderConfig getDecoderConfig() {
        return _decoderConfig;
    }

    public void addDecoderConfig(Map<String, DecoderInfo> decoderConfig) {
        _decoderConfig.setConfig(decoderConfig);
    }
}
