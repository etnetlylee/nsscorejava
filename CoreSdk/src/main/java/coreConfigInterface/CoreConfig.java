package coreConfigInterface;

import java.util.Map;

public class CoreConfig {
    String _productName;
    String _lang;
    ServerUrls _server;
    InitiatorConfig _initiator;
    String _region;
    boolean _enableCensor;
    Map<String, String> _apiEndpoints;
    boolean _useRandomWSIP;
    String _market;
    String _timezone;
    String _corpName;
    boolean _debug;

    CoreConfig(
            String productName,
            String lang,
            ServerUrls server,
            InitiatorConfig initiator,
            String region,
            boolean enableCensor,
            Map<String, String> apiEndpoints,
            boolean useRandomWSIP,
            String market,
            String timezone,
            String corpName,
            boolean debug) {
        _productName = productName;
        _lang = lang;
        _server = server;
        _initiator = initiator;
        _region = region;
        _enableCensor = enableCensor;
        _apiEndpoints = apiEndpoints;
        _useRandomWSIP = useRandomWSIP;
        _market = market;
        _timezone = timezone;
        _corpName = corpName;
        _debug = debug;
    }

    String get productName => _productName;
    String get lang => _lang;
    ServerUrls get server => _server;
    InitiatorConfig get initiator => _initiator;

    String get region => _region;
    set region(String region) => _region = region;

    boolean get enableCensor => _enableCensor;
    set enableCensor(boolean enableCensor) => _enableCensor = enableCensor;

    Map<String, String> get apiEndpoints => _apiEndpoints;
    boolean get useRandomWSIP => _useRandomWSIP;
    String get market => _market;
    String get timezone => _timezone;
    String get corpName => _corpName;
    boolean get debug => _debug;
}
