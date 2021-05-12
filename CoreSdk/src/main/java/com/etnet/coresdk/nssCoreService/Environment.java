package com.etnet.coresdk.nssCoreService;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    public static final Map<String, Object> environment = new HashMap<String, Object>() {{
        put("production", false);
        put("version", "0.1");
        put("apiUrl", "https://iq6.etnet.com.hk");
//        put("apiUrl", "https://iq6pilot.etnet.com.hk");
        put("ipService", "/HttpServer/webIQ/jsp/ip-service.jsp?ip=me");
    }};
}
