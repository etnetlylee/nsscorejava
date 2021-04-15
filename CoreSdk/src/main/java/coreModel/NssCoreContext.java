package coreModel;

import java.util.Map;

import config.AsaConfig;
import coreStorage.model.AsaStorage;

public class NssCoreContext {
    AsaConfig _asaConfig;
    ProcessorConfig _processorConfig;
    CoreConfig _config;

    Controller _controller;
    Storage _storage;
    User _user;
    EventBus _events;
    StorageDecodeDispatcher _storageDecodeDispatcher;
    AsaStorage _asaStorage;
    DecoderConfig _decoderConfig;

    public NssCoreContext() {
        _controller = Controller();
        _controller.setContext(this);
        _events = EventBus();
        _asaConfig = new AsaConfig();
        _processorConfig = ProcessorConfig();
        _decoderConfig = DecoderConfig();
        _storage = Storage();
        _storage.setContext(this);
        _storageDecodeDispatcher = StorageDecodeDispatcher();
        _storageDecodeDispatcher.setContext(this);
        _asaStorage = AsaStorage();
    }

    public void setConfig(CoreConfig config) {
        _config = config;
        Environment.mIsDebug = _config.debug || false;
    }

    public CoreConfig getConfig() {
        return _config;
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
        return _config.useRandomWSIP;
    }

    public void enableCensored(String region) {
        _config.region = region;
    }

    public String getMarket() {
        return _config.market;
    }

    public String getUMSUrl() {
        return _config.server.ums;
    }

    public String getProductName() {
        return _config.productName;
    }

    public User getUser() {
        return _user;
    }

    public void setUser(User user) {
        _user = user;
    }

    EventBus get events => _events;

    public StorageDecodeDispatcher getStorageDecoderDispatcher() {
        return _storageDecodeDispatcher;
    }

    public void addProcessorConfig(Map<String, ProcessorInfo> processorConfig) {
        _processorConfig.setConfig(processorConfig);
    }

    public Map<Object, Object> getProcessorConfig() {
        return _processorConfig.getConfig();
    }

    public DecoderConfig getDecoderConfig() {
        return _decoderConfig;
    }

    public void addDecoderConfig(Map<String, DecoderInfo> decoderConfig) {
        _decoderConfig.setConfig(decoderConfig);
    }
}
